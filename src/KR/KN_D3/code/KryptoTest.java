package KR.KN_D3.code;

import com.sun.net.httpserver.HttpServer;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class KryptoTest {
    public static void main(String[] args) throws Exception {
        testValidierungUndDelegation();
        testKonsole();
        testApi();
        System.out.println("KN-D3: Alle Tests erfolgreich.");
    }

    private static void testValidierungUndDelegation() throws Exception {
        List<List<Coin>> aufrufe = new ArrayList<>();
        List<Marktdaten> erwartet = List.of(beispiel());
        KryptoLogik logik = new KryptoLogik(new EingabeValidator(), coins -> {
            aufrufe.add(coins);
            return erwartet;
        });
        pruefe(logik.abfragen(" btc ") == erwartet, "Service-Ergebnis muss zurueckgegeben werden");
        logik.abfragen("sol");
        logik.abfragen("ETH");
        logik.abfragen("alle");
        pruefe(aufrufe.equals(List.of(List.of(Coin.BTC), List.of(Coin.SOL), List.of(Coin.ETH),
                List.of(Coin.BTC, Coin.SOL, Coin.ETH))), "Validierte Coins werden an Service uebergeben");
        for (String falsch : new String[] {null, "", "   ", "DOGE", "1", "BTC,ETH"}) {
            try {
                logik.abfragen(falsch);
                throw new AssertionError("Eigene Exception erwartet: " + falsch);
            } catch (UngueltigeEingabeException erwartetFehler) {
                pruefe(aufrufe.size() == 4, "Ungueltiger Input darf keinen Service aufrufen");
            }
        }
    }

    private static void testKonsole() {
        AtomicInteger aufrufe = new AtomicInteger();
        KryptoLogik logik = new KryptoLogik(new EingabeValidator(), coins -> {
            if (aufrufe.incrementAndGet() == 1) {
                throw new MarktdatenException("Test-Netzwerkfehler");
            }
            return List.of(beispiel());
        });
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        Konsole konsole = new Konsole(new Scanner("DOGE\nBTC\nBTC\n0\nETH\n"),
                new PrintStream(buffer, true, StandardCharsets.UTF_8));
        new KryptoApp(konsole, logik).start();
        String text = buffer.toString(StandardCharsets.UTF_8);
        pruefe(text.contains("Unbekannter Coin") && text.contains("Test-Netzwerkfehler")
                && text.contains("Bitcoin (BTC)") && text.contains("Auf Wiedersehen!"),
                "App muss nach Eingabe- und API-Fehlern weiterlaufen");
        pruefe(aufrufe.get() == 2, "Nach 0 darf kein weiterer Aufruf erfolgen");
        // Dateiende beendet ebenfalls sauber, auch ohne eingegebene 0.
        new KryptoApp(new Konsole(new Scanner(""), new PrintStream(buffer)), logik).start();
        pruefe(aufrufe.get() == 2, "Dateiende ruft keinen Service auf");
    }

    private static void testApi() throws Exception {
        AtomicInteger status = new AtomicInteger(200);
        AtomicReference<String> body = new AtomicReference<>();
        AtomicReference<String> query = new AtomicReference<>();
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/ticker", exchange -> {
            query.set(exchange.getRequestURI().getRawQuery());
            byte[] bytes = body.get().getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(status.get(), bytes.length);
            try (var output = exchange.getResponseBody()) { output.write(bytes); }
        });
        server.start();
        try {
            KrakenService service = new KrakenService(URI.create("http://127.0.0.1:"
                    + server.getAddress().getPort() + "/ticker"));
            // Bewusst verschiedene Werte fuer heute [0] und letzte 24 Stunden [1].
            String ticker = "{\"c\":[\"123.45\",\"1\"],\"h\":[\"125\",\"130\"],"
                    + "\"l\":[\"120\",\"110\"],\"v\":[\"10\",\"20.5\"]}";
            String korrekt = "{\"error\":[],\"result\":{\"XXBTZUSD\":" + ticker
                    + ",\"SOLUSD\":" + ticker + ",\"XETHZUSD\":" + ticker + "}}";
            body.set(korrekt);
            List<Marktdaten> daten = service.lade(List.of(Coin.values()));
            pruefe(query.get().equals("pair=XBTUSD,SOLUSD,ETHUSD"), "Handelspaare im HTTP-Request");
            pruefe(daten.size() == 3 && daten.get(1).coin() == Coin.SOL
                    && daten.get(2).coin() == Coin.ETH, "Alle Antwortschluessel werden zugeordnet");
            Marktdaten btc = daten.get(0);
            pruefe(btc.preis().equals(new BigDecimal("123.45"))
                    && btc.hoch24h().equals(new BigDecimal("130"))
                    && btc.tief24h().equals(new BigDecimal("110"))
                    && btc.volumen24h().equals(new BigDecimal("20.5")), "Korrekte 24h-Felder und Einheiten");
            for (String falsch : List.of("kein JSON", "null", "{}", "{\"error\":[],\"result\":{}}",
                    "{\"error\":[\"EGeneral:Test\"]}", korrekt.replace("123.45", "NaN"),
                    korrekt.replace("123.45", "-1"), korrekt.replace("\"130\"", "null"))) {
                body.set(falsch);
                erwarteApiFehler(service);
            }
            body.set(korrekt);
            for (int code : new int[] {429, 500, 403}) {
                status.set(code);
                erwarteApiFehler(service);
            }
        } finally {
            server.stop(0);
        }
    }

    private static void erwarteApiFehler(KrakenService service) throws Exception {
        try {
            service.lade(List.of(Coin.BTC));
            throw new AssertionError("MarktdatenException erwartet");
        } catch (MarktdatenException erwartet) {
            pruefe(!erwartet.getMessage().isBlank(), "Verstaendliche Fehlermeldung");
        }
    }

    private static Marktdaten beispiel() {
        return new Marktdaten(Coin.BTC, new BigDecimal("100"), new BigDecimal("110"),
                new BigDecimal("90"), new BigDecimal("20"), Instant.parse("2026-01-01T12:00:00Z"));
    }

    private static void pruefe(boolean bedingung, String meldung) {
        if (!bedingung) { throw new AssertionError(meldung); }
    }
}
