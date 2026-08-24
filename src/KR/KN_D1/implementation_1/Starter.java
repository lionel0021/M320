package KR.KN_D1.implementation_1;

public class Starter {
    public static void main(String[] args) {
        Konto privatkonto = new Konto("CH-1001", "Nico", 150_000);
        Konto sparkonto = new Konto("CH-1002", "Nico", 50_000);

        System.out.println("Vor der Überweisung:");
        zeigeKonten(privatkonto, sparkonto);

        privatkonto.einzahlen(20_000);
        privatkonto.abheben(5_000);
        privatkonto.ueberweisenAn(sparkonto, 30_000);

        System.out.println("\nNach Einzahlung, Abhebung und Überweisung:");
        zeigeKonten(privatkonto, sparkonto);
    }

    private static void zeigeKonten(Konto... konten) {
        for (Konto konto : konten) {
            System.out.printf("%s (%s): %s%n",
                    konto.getKontonummer(), konto.getInhaber(), konto.saldoFormatiert());
        }
    }
}
