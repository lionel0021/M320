package KR.KN_D3.code;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.Strictness;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KrakenService implements MarktdatenService {
    private final HttpClient client;
    private final URI endpoint;
    private final Gson gson = new GsonBuilder().setStrictness(Strictness.STRICT).create();

    public KrakenService() {
        this(URI.create("https://api.kraken.com/0/public/Ticker"));
    }

    // Austauschbarer Endpoint fuer Tests mit einem lokalen HTTP-Server.
    KrakenService(URI endpoint) {
        this.endpoint = endpoint;
        this.client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
    }

    @Override
    public List<Marktdaten> lade(List<Coin> coins) throws MarktdatenException {
        if (coins.isEmpty()) {
            throw new MarktdatenException("Es wurde kein Coin ausgewaehlt.");
        }
        String paare = coins.stream().map(Coin::getHandelspaar).collect(Collectors.joining(","));
        HttpRequest request = HttpRequest.newBuilder(URI.create(endpoint + "?pair=" + paare))
                .timeout(Duration.ofSeconds(15)).header("Accept", "application/json").GET().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 429) {
                throw new MarktdatenException("Zu viele Anfragen. Bitte spaeter erneut versuchen.");
            }
            if (response.statusCode() != 200) {
                throw new MarktdatenException("Kraken ist nicht verfuegbar (HTTP " + response.statusCode() + ").");
            }
            return leseAntwort(response.body(), coins);
        } catch (IOException e) {
            throw new MarktdatenException("Marktdaten nicht erreichbar. Internetverbindung pruefen.", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new MarktdatenException("Die Abfrage wurde unterbrochen.", e);
        }
    }

    private List<Marktdaten> leseAntwort(String json, List<Coin> coins) throws MarktdatenException {
        try {
            JsonObject root = gson.fromJson(json, JsonObject.class);
            JsonArray fehler = root.getAsJsonArray("error");
            if (fehler == null) {
                throw new IllegalArgumentException("Fehlerstatus fehlt");
            }
            if (!fehler.isEmpty()) {
                throw new MarktdatenException("Kraken meldet einen API-Fehler. Bitte spaeter erneut versuchen.");
            }
            JsonObject result = root.getAsJsonObject("result");
            List<Marktdaten> daten = new ArrayList<>();
            Instant zeitpunkt = Instant.now();
            for (Coin coin : coins) {
                JsonObject ticker = result.getAsJsonObject(coin.getAntwortSchluessel());
                // c[0]: letzter Handel; h/l/v[1]: letzte 24 Stunden (Index 0 waere heute seit 00:00 UTC).
                daten.add(new Marktdaten(coin, zahl(ticker, "c", 0), zahl(ticker, "h", 1),
                        zahl(ticker, "l", 1), zahl(ticker, "v", 1), zeitpunkt));
            }
            return List.copyOf(daten);
        } catch (RuntimeException e) {
            throw new MarktdatenException("Kraken hat unvollstaendige oder ungueltige Marktdaten geliefert.", e);
        }
    }

    private BigDecimal zahl(JsonObject ticker, String feld, int index) {
        JsonElement wert = ticker.getAsJsonArray(feld).get(index);
        BigDecimal zahl = wert.getAsBigDecimal();
        if (zahl.signum() < 0) {
            throw new IllegalArgumentException("Negativer Marktwert");
        }
        return zahl;
    }
}
