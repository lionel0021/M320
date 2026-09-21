package KR.KN_V3.code;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Starter {
    private final Scanner scanner = new Scanner(System.in);
    private final List<StockExchange> boersen = new ArrayList<>();
    private final Portfolio portfolio = new Portfolio();
    private StockExchange aktuelleBoerse;

    public static void main(String[] args) {
        new Starter().start();
    }

    private void start() {
        boersen.add(new BoerseZuerich());
        boersen.add(new BoerseNewYork());
        aktuelleBoerse = boersen.get(0);

        portfolio.addPosition(new Aktie("NESN", "Nestle"), 10);
        portfolio.addPosition(new Aktie("ABBN", "ABB"), 20);
        portfolio.addPosition(new Aktie("AAPL", "Apple"), 15);
        portfolio.addPosition(new Aktie("TSLA", "Tesla"), 5);

        System.out.println("=== Aktien-Portfolio ===");
        boolean laeuft = true;
        while (laeuft) {
            zeigeMenue();
            switch (scanner.nextLine().trim()) {
                case "1" -> boerseWaehlen();
                case "2" -> portfoliowertAnzeigen();
                case "3" -> kursAbfragen();
                case "4" -> portfolioAnzeigen();
                case "5" -> laeuft = false;
                default -> System.out.println("Ungueltige Auswahl.");
            }
            System.out.println();
        }
        System.out.println("Programm beendet.");
    }

    private void zeigeMenue() {
        System.out.println("Aktuelle Boerse: " + aktuelleBoerse.getName());
        System.out.println("1 = Boerse waehlen");
        System.out.println("2 = Portfoliowert anzeigen");
        System.out.println("3 = Aktienkurs abfragen");
        System.out.println("4 = Portfolio anzeigen");
        System.out.println("5 = Beenden");
        System.out.print("Auswahl: ");
    }

    private void boerseWaehlen() {
        for (int i = 0; i < boersen.size(); i++) {
            System.out.println("  " + (i + 1) + " = " + boersen.get(i).getName());
        }
        System.out.print("Boerse-Nr: ");
        try {
            int index = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (index >= 0 && index < boersen.size()) {
                aktuelleBoerse = boersen.get(index);
                System.out.println("Gewaehlt: " + aktuelleBoerse.getName());
            } else {
                System.out.println("Ungueltige Nummer.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Bitte eine Zahl eingeben.");
        }
    }

    private void portfoliowertAnzeigen() {
        double wert = portfolio.berechneWert(aktuelleBoerse);
        System.out.printf("Portfoliowert an %s: %.2f%n", aktuelleBoerse.getName(), wert);
        System.out.println("(nur an dieser Boerse kotierte Aktien werden gezaehlt)");
    }

    private void kursAbfragen() {
        System.out.print("Symbol: ");
        String symbol = scanner.nextLine().trim().toUpperCase();
        if (aktuelleBoerse.istKotiert(symbol)) {
            System.out.printf("%s Kurs: %.2f%n", symbol, aktuelleBoerse.getKurs(symbol));
        } else {
            System.out.println(symbol + " ist an " + aktuelleBoerse.getName() + " nicht kotiert.");
        }
    }

    private void portfolioAnzeigen() {
        System.out.println("Positionen:");
        for (Position p : portfolio.getPositionen()) {
            boolean kotiert = aktuelleBoerse.istKotiert(p.getAktie().getSymbol());
            String kurs = kotiert
                    ? String.format("%.2f", aktuelleBoerse.getKurs(p.getAktie().getSymbol()))
                    : "nicht kotiert";
            System.out.printf("  %d x %s | Kurs: %s%n", p.getAnzahl(), p.getAktie(), kurs);
        }
    }
}
