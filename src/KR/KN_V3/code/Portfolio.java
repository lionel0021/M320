package KR.KN_V3.code;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {
    private final List<Position> positionen = new ArrayList<>();

    public void addPosition(Aktie aktie, int anzahl) {
        positionen.add(new Position(aktie, anzahl));
    }

    public double berechneWert(StockExchange boerse) {
        double summe = 0.0;
        for (Position p : positionen) {
            if (boerse.istKotiert(p.getAktie().getSymbol())) {
                summe += p.getAnzahl() * boerse.getKurs(p.getAktie().getSymbol());
            }
        }
        return summe;
    }

    public List<Position> getPositionen() {
        return new ArrayList<>(positionen);
    }
}
