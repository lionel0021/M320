package KR.KN_D1.garage;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

/**
 * Zweites Beispiel fuer KN-D1: eine Garagen-Simulation mit Benutzereingabe.
 *
 * Der Benutzer kann Fahrzeuge zur Reparatur registrieren (mit Kosten), sie als
 * repariert markieren und abfragen, welche Fahrzeuge schon repariert sind und
 * wie hoch die Kosten sind.
 */
public class GarageStarter {

    public static void main(String[] args) {
        Garage garage = new Garage();
        // Startbestand, damit man sofort etwas sieht.
        garage.registriere("ZH-1000", 45_000); // 450.00 CHF
        garage.registriere("ZH-2000", 120_000); // 1'200.00 CHF

        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Garagen-Simulation ===");

        boolean laeuft = true;
        while (laeuft) {
            zeigeMenue();
            String wahl = scanner.nextLine().trim();

            try {
                switch (wahl) {
                    case "1" -> {
                        System.out.print("Kennzeichen: ");
                        String kennzeichen = scanner.nextLine().trim();
                        long kosten = frageBetrag(scanner, "Reparaturkosten in CHF: ");
                        garage.registriere(kennzeichen, kosten);
                        System.out.println("Registriert: " + kennzeichen);
                    }
                    case "2" -> {
                        System.out.print("Kennzeichen (repariert): ");
                        String kennzeichen = scanner.nextLine().trim();
                        if (garage.markiereRepariert(kennzeichen)) {
                            System.out.println(kennzeichen + " ist jetzt repariert.");
                        } else {
                            System.out.println("Kein Fahrzeug mit diesem Kennzeichen.");
                        }
                    }
                    case "3" -> zeigeListe("Reparierte Fahrzeuge:", garage.reparierteFahrzeuge());
                    case "4" -> zeigeListe("Offene Fahrzeuge:", garage.offeneFahrzeuge());
                    case "5" -> System.out.println("Gesamte Reparaturkosten: "
                            + rappenAlsChf(garage.gesamtkostenInRappen()));
                    case "0" -> laeuft = false;
                    default -> System.out.println("Ungueltige Auswahl. Bitte 0-5 eingeben.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Fehler: " + e.getMessage());
            }
            System.out.println();
        }

        System.out.println("Programm beendet.");
    }

    private static void zeigeMenue() {
        System.out.println("1 = Fahrzeug zur Reparatur registrieren");
        System.out.println("2 = Fahrzeug als repariert markieren");
        System.out.println("3 = Reparierte Fahrzeuge anzeigen");
        System.out.println("4 = Offene Fahrzeuge anzeigen");
        System.out.println("5 = Gesamtkosten anzeigen");
        System.out.println("0 = Beenden");
        System.out.print("Auswahl: ");
    }

    private static void zeigeListe(String titel, List<Fahrzeug> liste) {
        System.out.println(titel);
        if (liste.isEmpty()) {
            System.out.println("  (keine)");
            return;
        }
        long summe = 0;
        for (Fahrzeug fahrzeug : liste) {
            System.out.println("  " + fahrzeug);
            summe += fahrzeug.getKostenInRappen();
        }
        System.out.println("  Kosten zusammen: " + rappenAlsChf(summe));
    }

    /** Liest einen CHF-Betrag ein und gibt ihn in Rappen zurueck. */
    private static long frageBetrag(Scanner scanner, String frage) {
        while (true) {
            System.out.print(frage);
            String eingabe = scanner.nextLine().trim().replace(",", ".");
            try {
                double chf = Double.parseDouble(eingabe);
                if (chf < 0) {
                    System.out.println("Betrag darf nicht negativ sein.");
                    continue;
                }
                return Math.round(chf * 100);
            } catch (NumberFormatException e) {
                System.out.println("Bitte eine Zahl eingeben (z.B. 450 oder 450.50).");
            }
        }
    }

    private static String rappenAlsChf(long rappen) {
        return "CHF " + BigDecimal.valueOf(rappen, 2).toPlainString();
    }
}
