package KR.KN_V3.code;

public class PortfolioTest {
    public static void main(String[] args) {
        testWertHaengtVonBoerseAb();
        testNichtKotierteWerdenIgnoriert();
        testKursException();
        testIstKotiert();
        System.out.println("KN-V3: Alle Tests erfolgreich.");
    }

    private static void testWertHaengtVonBoerseAb() {
        Portfolio portfolio = new Portfolio();
        portfolio.addPosition(new Aktie("NESN", "Nestle"), 10);
        portfolio.addPosition(new Aktie("AAPL", "Apple"), 5);

        double zuerich = portfolio.berechneWert(new BoerseZuerich());
        double newYork = portfolio.berechneWert(new BoerseNewYork());

        pruefe(Math.abs(zuerich - 925.0) < 0.001, "Zuerich-Wert falsch (nur Nestle)");
        pruefe(Math.abs(newYork - 900.0) < 0.001, "New-York-Wert falsch (nur Apple)");
        pruefe(zuerich != newYork, "Wert muss je nach Boerse verschieden sein");
    }

    private static void testNichtKotierteWerdenIgnoriert() {
        Portfolio portfolio = new Portfolio();
        portfolio.addPosition(new Aktie("XXXX", "Unbekannt"), 100);
        pruefe(portfolio.berechneWert(new BoerseZuerich()) == 0.0, "unbekannte Aktie muss ignoriert werden");
    }

    private static void testKursException() {
        StockExchange boerse = new BoerseZuerich();
        try {
            boerse.getKurs("AAPL");
            throw new AssertionError("Apple ist in Zuerich nicht kotiert, haette werfen muessen");
        } catch (IllegalArgumentException erwartet) {
        }
    }

    private static void testIstKotiert() {
        StockExchange boerse = new BoerseNewYork();
        pruefe(boerse.istKotiert("AAPL"), "Apple muss in New York kotiert sein");
        pruefe(!boerse.istKotiert("NESN"), "Nestle darf in New York nicht kotiert sein");
    }

    private static void pruefe(boolean bedingung, String meldung) {
        if (!bedingung) {
            throw new AssertionError("FEHLGESCHLAGEN: " + meldung);
        }
    }
}
