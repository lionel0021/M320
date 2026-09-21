package KR.KN_V2.code;

import java.util.List;
import java.util.Scanner;

/**
 * Stellt das Konsolenmenue fuer die Fuhrparkverwaltung bereit.
 *
 * <p>Laedt beim Start Beispielfahrzeuge und verarbeitet Benutzereingaben zur
 * Verwaltung, Reservierung und zum Kostenvergleich. Die Fahrzeugverwaltung
 * wird an {@link Fuhrpark} delegiert.</p>
 */
public class Starter {
    private final Fuhrpark fuhrpark = new Fuhrpark();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new Starter().start();
    }

    private void start() {
        beispieldatenLaden();
        System.out.println("=== Fuhrpark-Verwaltung (Lieferfahrzeuge) ===");
        boolean laeuft = true;
        while (laeuft) {
            zeigeMenue();
            switch (scanner.nextLine().trim()) {
                case "1" -> fahrzeugHinzufuegen();
                case "2" -> fuhrpark.printAlle();
                case "3" -> fahrzeugSuchen();
                case "4" -> geeigneteAnzeigen();
                case "5" -> lieferkostenBerechnen();
                case "6" -> reservierenFreigeben();
                case "7" -> gesamtkostenVergleichen();
                case "8" -> fahrzeugLoeschen();
                case "9" -> laeuft = false;
                default -> System.out.println("Ungueltige Auswahl.");
            }
            System.out.println();
        }
        System.out.println("Programm beendet.");
    }

    private void zeigeMenue() {
        System.out.println("1 = Fahrzeug hinzufuegen");
        System.out.println("2 = Alle Fahrzeuge anzeigen");
        System.out.println("3 = Fahrzeug suchen");
        System.out.println("4 = Geeignete Fahrzeuge fuer eine Distanz anzeigen");
        System.out.println("5 = Lieferkosten fuer ein Fahrzeug berechnen");
        System.out.println("6 = Fahrzeug reservieren / freigeben");
        System.out.println("7 = Gesamtkosten aller geeigneten Fahrzeuge vergleichen");
        System.out.println("8 = Fahrzeug loeschen");
        System.out.println("9 = Beenden");
        System.out.print("Auswahl: ");
    }

    /**
     * Laedt auch absichtlich ungeeignete Fahrzeuge, damit die Eignungspruefung
     * bei Ueberladung, zu hoher Temperatur und begrenzter Reichweite sichtbar wird.
     */
    private void beispieldatenLaden() {
        fuhrpark.add(new Transporter("T1", "ZH-1001", 1.20, 50000, 1500, 900));
        fuhrpark.add(new Transporter("T2", "ZH-1002", 1.10, 80000, 1000, 1200));
        fuhrpark.add(new Kuehlfahrzeug("K1", "ZH-2001", 1.50, 30000, 4.0, true));
        fuhrpark.add(new Kuehlfahrzeug("K2", "ZH-2002", 1.50, 42000, 9.0, true));
        fuhrpark.add(new ElektroVan("E1", "ZH-3001", 1.00, 12000, 80, 200));
        fuhrpark.add(new ElektroVan("E2", "ZH-3002", 1.00, 9000, 20, 150));
        fuhrpark.add(new Lastenrad("L1", "ZH-4001", 0.40, 500, 15));
    }

    private void fahrzeugHinzufuegen() {
        System.out.println("Typ: T = Transporter, K = Kuehlfahrzeug, E = ElektroVan, L = Lastenrad");
        String typ = frageText("Typ: ").toUpperCase();
        String id = frageText("Id: ");
        String kennzeichen = frageText("Kennzeichen: ");
        double basis = frageZahl("Basiskosten pro km: ");
        double kmStand = frageZahl("Kilometerstand: ");
        try {
            switch (typ) {
                case "T" -> fuhrpark.add(new Transporter(id, kennzeichen, basis, kmStand,
                        frageZahl("max Nutzlast kg: "), frageZahl("aktuelle Ladung kg: ")));
                case "K" -> fuhrpark.add(new Kuehlfahrzeug(id, kennzeichen, basis, kmStand,
                        frageZahl("Temperatur: "), frageJaNein("Kuehlung aktiv? (j/n): ")));
                case "E" -> fuhrpark.add(new ElektroVan(id, kennzeichen, basis, kmStand,
                        frageZahl("Akku Prozent: "), frageZahl("Reichweite km: ")));
                case "L" -> fuhrpark.add(new Lastenrad(id, kennzeichen, basis, kmStand,
                        frageZahl("max Distanz km: ")));
                default -> System.out.println("Unbekannter Typ.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }

    private void fahrzeugSuchen() {
        Lieferfahrzeug f = fuhrpark.findById(frageText("Id: "));
        System.out.println(f == null ? "Nicht gefunden." : f.printOut());
    }

    private void geeigneteAnzeigen() {
        double distanz = frageZahl("Distanz km: ");
        List<Lieferfahrzeug> geeignet = fuhrpark.findeGeeigneteFahrzeuge(distanz);
        if (geeignet.isEmpty()) {
            System.out.println("Kein Fahrzeug geeignet.");
            return;
        }
        for (Lieferfahrzeug f : geeignet) {
            System.out.printf("%s | Kosten %.2f CHF%n", f.info(), f.berechneKosten(distanz));
        }
    }

    private void lieferkostenBerechnen() {
        Lieferfahrzeug f = fuhrpark.findById(frageText("Id: "));
        if (f == null) {
            System.out.println("Nicht gefunden.");
            return;
        }
        double distanz = frageZahl("Distanz km: ");
        System.out.printf("Kosten: %.2f CHF%n", f.berechneKosten(distanz));
        System.out.println("Kann liefern: " + (f.kannLiefern(distanz) ? "ja" : "nein"));
    }

    private void reservierenFreigeben() {
        Lieferfahrzeug f = fuhrpark.findById(frageText("Id: "));
        if (f == null) {
            System.out.println("Nicht gefunden.");
            return;
        }
        try {
            if (f.istVerfuegbar()) {
                f.reservieren();
                System.out.println("Reserviert.");
            } else {
                f.freigeben();
                System.out.println("Freigegeben.");
            }
        } catch (IllegalStateException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }

    /**
     * Vergleicht nur einsatzfaehige Fahrzeuge. Bei gleichen Kosten bleibt das zuerst
     * aufgenommene Fahrzeug die guenstigste Wahl, da nur kleinere Kosten es ersetzen.
     */
    private void gesamtkostenVergleichen() {
        double distanz = frageZahl("Distanz km: ");
        List<Lieferfahrzeug> geeignet = fuhrpark.findeGeeigneteFahrzeuge(distanz);
        if (geeignet.isEmpty()) {
            System.out.println("Kein Fahrzeug geeignet.");
            return;
        }
        Lieferfahrzeug guenstigstes = geeignet.get(0);
        for (Lieferfahrzeug f : geeignet) {
            System.out.printf("%s | %.2f CHF%n", f.info(), f.berechneKosten(distanz));
            if (f.berechneKosten(distanz) < guenstigstes.berechneKosten(distanz)) {
                guenstigstes = f;
            }
        }
        System.out.printf("Guenstigstes: %s (%.2f CHF)%n",
                guenstigstes.info(), guenstigstes.berechneKosten(distanz));
        System.out.printf("Gesamtkosten aller geeigneten: %.2f CHF%n",
                fuhrpark.berechneGesamtkosten(distanz));
    }

    private void fahrzeugLoeschen() {
        System.out.println(fuhrpark.removeById(frageText("Id: ")) ? "Geloescht." : "Nicht gefunden.");
    }

    private String frageText(String frage) {
        System.out.print(frage);
        return scanner.nextLine().trim();
    }

    /**
     * Akzeptiert Dezimalkomma und Dezimalpunkt und wiederholt die Abfrage bei Parsefehlern.
     * Fachliche Grenzen wie positive Distanzen werden hier nicht geprueft.
     *
     * @param frage Eingabeaufforderung fuer die Konsole
     * @return eingelesene Zahl
     */
    private double frageZahl(String frage) {
        while (true) {
            System.out.print(frage);
            try {
                return Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Bitte eine Zahl eingeben.");
            }
        }
    }

    private boolean frageJaNein(String frage) {
        return frageText(frage).equalsIgnoreCase("j");
    }
}
