package KR.KN_D1.implementation_1;

public class KontoTest {
    public static void main(String[] args) {
        testEinzahlenUndAbheben();
        testUeberweisungVeraendertBeideKonten();
        testUeberziehungWirdVerhindert();
        System.out.println("Implementation 1: Alle Tests erfolgreich.");
    }

    private static void testEinzahlenUndAbheben() {
        Konto konto = new Konto("CH-TEST-1", "Test", 10_000);
        konto.einzahlen(2_500);
        konto.abheben(1_000);
        pruefe(konto.getSaldoInRappen() == 11_500, "Einzahlen/Abheben ist falsch");
    }

    private static void testUeberweisungVeraendertBeideKonten() {
        Konto quelle = new Konto("CH-TEST-2", "Quelle", 10_000);
        Konto ziel = new Konto("CH-TEST-3", "Ziel", 5_000);
        quelle.ueberweisenAn(ziel, 2_000);
        pruefe(quelle.getSaldoInRappen() == 8_000, "Quellkonto ist falsch");
        pruefe(ziel.getSaldoInRappen() == 7_000, "Zielkonto ist falsch");
    }

    private static void testUeberziehungWirdVerhindert() {
        Konto konto = new Konto("CH-TEST-4", "Test", 1_000);
        boolean fehlerErhalten = false;
        try {
            konto.abheben(1_001);
        } catch (IllegalStateException exception) {
            fehlerErhalten = true;
        }
        pruefe(fehlerErhalten, "Eine Überziehung muss abgelehnt werden");
        pruefe(konto.getSaldoInRappen() == 1_000, "Saldo darf sich nicht verändern");
    }

    private static void pruefe(boolean bedingung, String meldung) {
        if (!bedingung) {
            throw new AssertionError(meldung);
        }
    }
}
