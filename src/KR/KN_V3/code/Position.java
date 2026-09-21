package KR.KN_V3.code;

public class Position {
    private final Aktie aktie;
    private final int anzahl;

    public Position(Aktie aktie, int anzahl) {
        this.aktie = aktie;
        this.anzahl = anzahl;
    }

    public Aktie getAktie() {
        return aktie;
    }

    public int getAnzahl() {
        return anzahl;
    }
}
