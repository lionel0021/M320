package kn.d2;

// Kleines Wert-Objekt fuer die Abflugzeit (Stunde:Minute).
// Es wird vom Flug selbst erzeugt und gehoert nur ihm -> Teil einer KOMPOSITION.
public class Abflugzeit {
    private final int stunde;
    private final int minute;

    public Abflugzeit(int stunde, int minute) {
        if (stunde < 0 || stunde > 23 || minute < 0 || minute > 59) {
            throw new IllegalArgumentException("Ungueltige Uhrzeit: " + stunde + ":" + minute);
        }
        this.stunde = stunde;
        this.minute = minute;
    }

    // fuer die Suche im Zeitplan: Minuten seit Mitternacht
    public int alsMinuten() {
        return stunde * 60 + minute;
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d", stunde, minute);
    }
}
