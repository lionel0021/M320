package KR.KN_D1.implementation_1;

/**
 * Zweites Beispiel (ohne Benutzereingabe): ein Lohn-Szenario.
 *
 * Zeigt die Objektkommunikation aus KN-D1: ein Arbeitgeber-Konto ueberweist
 * Lohn an zwei Mitarbeiter-Konten, danach spart ein Mitarbeiter einen Teil auf
 * ein eigenes Sparkonto. Jede Ueberweisung ist ein Aufruf von einem
 * Konto-Objekt an ein anderes (privatkonto.ueberweisenAn(zielkonto, betrag)).
 */
public class Beispiel2 {

    public static void main(String[] args) {
        // Betraege werden in Rappen gefuehrt (100'000 Rappen = 1'000.00 CHF).
        Konto arbeitgeber = new Konto("CH-2000", "Firma AG", 1_000_000); // 10'000.00 CHF
        Konto lohnAnna = new Konto("CH-2001", "Anna", 0);
        Konto lohnBen = new Konto("CH-2002", "Ben", 0);
        Konto sparAnna = new Konto("CH-2003", "Anna", 0);

        System.out.println("=== Beispiel 2: Lohnzahlung ===");
        System.out.println("Vorher:");
        zeigeKonten(arbeitgeber, lohnAnna, lohnBen, sparAnna);

        // Arbeitgeber zahlt Lohn -> Objekt ruft Methode am Zielobjekt auf.
        arbeitgeber.ueberweisenAn(lohnAnna, 450_000); // 4'500.00 CHF
        arbeitgeber.ueberweisenAn(lohnBen, 380_000);  // 3'800.00 CHF

        // Anna legt einen Teil auf ihr Sparkonto.
        lohnAnna.ueberweisenAn(sparAnna, 100_000);    // 1'000.00 CHF

        System.out.println("\nNach Lohnzahlung und Sparen:");
        zeigeKonten(arbeitgeber, lohnAnna, lohnBen, sparAnna);

        // Zeigt die Kapselung: ein zu hoher Betrag wird abgelehnt.
        System.out.println("\nVersuch, mehr abzuheben als vorhanden:");
        try {
            lohnBen.abheben(999_999_999);
        } catch (IllegalStateException e) {
            System.out.println("Abgelehnt: " + e.getMessage());
        }
    }

    private static void zeigeKonten(Konto... konten) {
        for (Konto konto : konten) {
            System.out.printf("%s (%s): %s%n",
                    konto.getKontonummer(), konto.getInhaber(), konto.saldoFormatiert());
        }
    }
}
