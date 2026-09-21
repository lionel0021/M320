package KR.KN_V3.code;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BoerseZuerich implements StockExchange {
    private final Map<String, Aktie> aktien = new LinkedHashMap<>();
    private final Map<String, Double> kurse = new LinkedHashMap<>();

    public BoerseZuerich() {
        erfasse(new Aktie("NESN", "Nestle"), 92.50);
        erfasse(new Aktie("ABBN", "ABB"), 51.20);
        erfasse(new Aktie("SREN", "Swiss Re"), 118.75);
        erfasse(new Aktie("NOVN", "Novartis"), 96.10);
    }

    private void erfasse(Aktie aktie, double kurs) {
        aktien.put(aktie.getSymbol(), aktie);
        kurse.put(aktie.getSymbol(), kurs);
    }

    @Override
    public String getName() {
        return "Boerse Zuerich (CHF)";
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
