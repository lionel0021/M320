package KR.KN_V3.code;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BoerseNewYork implements StockExchange {
    private final Map<String, Aktie> aktien = new LinkedHashMap<>();
    private final Map<String, Double> kurse = new LinkedHashMap<>();

    public BoerseNewYork() {
        erfasse(new Aktie("AAPL", "Apple"), 180.00);
        erfasse(new Aktie("MSFT", "Microsoft"), 415.30);
        erfasse(new Aktie("TSLA", "Tesla"), 245.60);
        erfasse(new Aktie("AMZN", "Amazon"), 178.90);
    }

    private void erfasse(Aktie aktie, double kurs) {
        aktien.put(aktie.getSymbol(), aktie);
        kurse.put(aktie.getSymbol(), kurs);
    }

    @Override
    public String getName() {
        return "Boerse New York (USD)";
    }

    @Override
    public boolean istKotiert(String symbol) {
        return kurse.containsKey(symbol);
    }

    @Override
    public double getKurs(String symbol) {
        if (!istKotiert(symbol)) {
            throw new IllegalArgumentException(symbol + " ist an " + getName() + " nicht kotiert.");
        }
        return kurse.get(symbol);
    }

    @Override
    public List<Aktie> getAktien() {
        return new ArrayList<>(aktien.values());
    }
}
