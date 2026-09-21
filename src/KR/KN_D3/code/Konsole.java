package KR.KN_D3.code;

import java.io.PrintStream;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Konsole {
    private final Scanner scanner;
    private final PrintStream ausgabe;
    private final DateTimeFormatter zeitformat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss z")
            .withZone(ZoneId.of("Europe/Zurich"));

    public Konsole(Scanner scanner, PrintStream ausgabe) {
        this.scanner = scanner;
        this.ausgabe = ausgabe;
    }

    public void begruessen() {
        ausgabe.println("Krypto-Marktdaten: Bitcoin, Solana und Ethereum");
        ausgabe.println("Quelle: Kraken | Preise in USD | Hoch/Tief/Volumen: letzte 24 Stunden");
    }

    public String liesAuswahl() {
        ausgabe.print("\nBTC, SOL, ETH, ALLE oder 0 zum Beenden: ");
        return scanner.hasNextLine() ? scanner.nextLine() : null;
    }

    public void zeige(List<Marktdaten> daten) {
        for (Marktdaten d : daten) {
            ausgabe.printf(Locale.GERMANY,
                    "%n%s (%s)%nLetzter Kurs: %,.2f USD%n24h-Hoch:     %,.2f USD%n"
                    + "24h-Tief:     %,.2f USD%n24h-Volumen:  %,.4f %s%nAbgerufen: %s%n",
                    d.coin().getName(), d.coin(), d.preis(), d.hoch24h(), d.tief24h(),
                    d.volumen24h(), d.coin(), zeitformat.format(d.abgerufenAm()));
        }
    }

    public void fehler(String meldung) { ausgabe.println("Fehler: " + meldung); }
    public void verabschieden() { ausgabe.println("Auf Wiedersehen!"); }
}
