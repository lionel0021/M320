package KR.KN_D1.implementation_1;

import java.util.Scanner;

/**
 * Interaktive Variante: der Benutzer steuert die zwei Konten selbst ueber
 * ein einfaches Konsolenmenue. Betraege werden in CHF eingegeben (z.B. 100.50)
 * und intern in Rappen umgerechnet.
 */
public class Starter {

    public static void main(String[] args) {
        Konto privatkonto = new Konto("CH-1001", "Nico", 150_000); // 1'500.00 CHF
        Konto sparkonto = new Konto("CH-1002", "Nico", 50_000);    //   500.00 CHF
        Konto[] konten = {privatkonto, sparkonto};

        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Banksimulation (Implementation 1) ===");

        boolean laeuft = true;
        while (laeuft) {
            zeigeMenue();
            String wahl = scanner.nextLine().trim();

            try {
                switch (wahl) {
                    case "1" -> zeigeKonten(konten);
                    case "2" -> {
                        Konto k = waehleKonto(scanner, konten, "Auf welches Konto einzahlen?");
                        k.einzahlen(frageBetrag(scanner, "Einzahlungsbetrag in CHF: "));
                        System.out.println("Neuer Saldo: " + k.saldoFormatiert());
                    }
                    case "3" -> {
                        Konto k = waehleKonto(scanner, konten, "Von welchem Konto abheben?");
                        k.abheben(frageBetrag(scanner, "Abhebungsbetrag in CHF: "));
                        System.out.println("Neuer Saldo: " + k.saldoFormatiert());
                    }
                    case "4" -> {
                        Konto von = waehleKonto(scanner, konten, "Von welchem Konto ueberweisen?");
                        Konto auf = waehleKonto(scanner, konten, "Auf welches Konto ueberweisen?");
                        von.ueberweisenAn(auf, frageBetrag(scanner, "Ueberweisungsbetrag in CHF: "));
                        System.out.println("Ueberweisung ausgefuehrt.");
                        zeigeKonten(konten);
                    }
                    case "0" -> laeuft = false;
                    default -> System.out.println("Ungueltige Auswahl. Bitte 0-4 eingeben.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                // Fachliche Fehler (z.B. zu wenig Guthaben) abfangen, Programm laeuft weiter.
                System.out.println("Fehler: " + e.getMessage());
            }
            System.out.println();
        }

        System.out.println("Programm beendet. Auf Wiedersehen.");
    }

    private static void zeigeMenue() {
        System.out.println("1 = Konten anzeigen");
        System.out.println("2 = Einzahlen");
        System.out.println("3 = Abheben");
        System.out.println("4 = Ueberweisen");
        System.out.println("0 = Beenden");
        System.out.print("Auswahl: ");
    }

    private static Konto waehleKonto(Scanner scanner, Konto[] konten, String frage) {
        while (true) {
            System.out.println(frage);
            for (int i = 0; i < konten.length; i++) {
                System.out.printf("  %d = %s (%s)%n",
                        i + 1, konten[i].getKontonummer(), konten[i].getInhaber());
            }
            System.out.print("Konto-Nr: ");
            String eingabe = scanner.nextLine().trim();
            try {
                int index = Integer.parseInt(eingabe) - 1;
                if (index >= 0 && index < konten.length) {
                    return konten[index];
                }
            } catch (NumberFormatException ignoriert) {
                // faellt unten in die Fehlermeldung
            }
            System.out.println("Bitte eine gueltige Konto-Nr eingeben.");
        }
    }

    /** Liest einen CHF-Betrag ein und gibt ihn in Rappen zurueck. */
    private static long frageBetrag(Scanner scanner, String frage) {
        while (true) {
            System.out.print(frage);
            String eingabe = scanner.nextLine().trim().replace(",", ".");
            try {
                double chf = Double.parseDouble(eingabe);
                return Math.round(chf * 100);
            } catch (NumberFormatException e) {
                System.out.println("Bitte eine Zahl eingeben (z.B. 100 oder 100.50).");
            }
        }
    }

    private static void zeigeKonten(Konto... konten) {
        for (Konto konto : konten) {
            System.out.printf("%s (%s): %s%n",
                    konto.getKontonummer(), konto.getInhaber(), konto.saldoFormatiert());
        }
    }
}
