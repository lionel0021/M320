package kn.d2;

import java.util.ArrayList;
import java.util.List;

// Der Zeitplan HAT mehrere Fluege (AGGREGATION): die Fluege werden von aussen
// hinzugefuegt und existieren auch ohne den Zeitplan weiter.
//
// Der Zeitplan delegiert Aufgaben an die einzelnen Fluege weiter.
public class Zeitplan {
    private final List<Flug> fluege = new ArrayList<>();

    public void addFlug(Flug flug) {
        fluege.add(flug);
    }

    // DELEGATION: Zeitplan sucht den Flug und laesst diesen selbst zaehlen.
    public int passagiereVon(String flugNummer) {
        Flug flug = sucheFlug(flugNummer);
        return flug == null ? 0 : flug.anzahlPassagiere();
    }

    // Alle Fluege, die ab einer bestimmten Uhrzeit starten.
    public List<Flug> fluegeAb(int stunde, int minute) {
        int grenze = stunde * 60 + minute;
        List<Flug> treffer = new ArrayList<>();
        for (Flug flug : fluege) {
            if (flug.abflugInMinuten() >= grenze) {
                treffer.add(flug);
            }
        }
        return treffer;
    }

    public Flug sucheFlug(String flugNummer) {
        for (Flug flug : fluege) {
            if (flug.getFlugNummer().equals(flugNummer)) {
                return flug;
            }
        }
        return null;
    }

    public int anzahlFluege() {
        return fluege.size();
    }
}
