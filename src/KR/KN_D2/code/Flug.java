package kn.d2;

import java.util.ArrayList;
import java.util.List;

// Ein Flug hat ZWEI verschiedene HAT-Beziehungen:
//
// 1) KOMPOSITION mit Abflugzeit:
//    Der Flug ERZEUGT das Abflugzeit-Objekt selbst im Konstruktor und gibt es
//    nie nach aussen. Wird der Flug weggeworfen, ist auch die Abflugzeit weg.
//    -> starke Abhaengigkeit ("besteht aus").
//
// 2) AGGREGATION mit Passagier:
//    Die Passagiere kommen von AUSSEN (addPassagier) und leben unabhaengig
//    weiter, wenn der Flug geloescht wird.
//    -> lose Koppelung ("hat").
public class Flug {
    private final String flugNummer;
    private final String ziel;
    private final Abflugzeit abflugzeit;              // Komposition (selbst erzeugt)
    private final List<Passagier> passagiere = new ArrayList<>(); // Aggregation

    public Flug(String flugNummer, String ziel, int stunde, int minute) {
        this.flugNummer = flugNummer;
        this.ziel = ziel;
        this.abflugzeit = new Abflugzeit(stunde, minute); // <-- hier entsteht der Teil
    }

    public void addPassagier(Passagier passagier) {
        passagiere.add(passagier);
    }

    public boolean removePassagier(Passagier passagier) {
        return passagiere.remove(passagier);
    }

    // DELEGATION: der Flug beantwortet die Frage nicht selbst,
    // sondern reicht sie an die Passagierliste weiter.
    public int anzahlPassagiere() {
        return passagiere.size();
    }

    public List<Passagier> getPassagiere() {
        return new ArrayList<>(passagiere); // Kopie -> Liste bleibt gekapselt
    }

    public String getFlugNummer() {
        return flugNummer;
    }

    public String getZiel() {
        return ziel;
    }

    public int abflugInMinuten() {
        return abflugzeit.alsMinuten();
    }

    @Override
    public String toString() {
        return flugNummer + " -> " + ziel + " um " + abflugzeit
                + " (" + anzahlPassagiere() + " Passagiere)";
    }
}
