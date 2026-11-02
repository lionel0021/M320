package KR.N4_nico.D4.code;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Raum extends Spielobjekt {
    private final Map<String, Raum> ausgaenge = new LinkedHashMap<>();
    private final Map<String, String> schloesser = new HashMap<>();
    private final Inventar<Gegenstand> gegenstaende = new Inventar<>();

    public Raum(String name, String beschreibung) {
        super(name, beschreibung);
    }

    public void setAusgang(String richtung, Raum ziel) {
        ausgaenge.put(richtung, ziel);
    }

    public Raum getAusgang(String richtung) {
        return ausgaenge.get(richtung);
    }

    public void verschliesse(String richtung, String schluesselName) {
        schloesser.put(richtung, schluesselName);
    }

    public String benoetigterSchluessel(String richtung) {
        return schloesser.get(richtung);
    }

    public void addGegenstand(Gegenstand g) {
        gegenstaende.hinzufuegen(g);
    }

    public Gegenstand nimmGegenstand(String name) {
        return gegenstaende.entferne(name);
    }

    public String untersuchen() {
        StringBuilder sb = new StringBuilder(getBeschreibung());
        if (!gegenstaende.istLeer()) {
            sb.append("\nHier liegt: ").append(String.join(", ", gegenstaende.namen()));
        }
        if (!ausgaenge.isEmpty()) {
            sb.append("\nAusgaenge: ").append(String.join(", ", ausgaenge.keySet()));
        }
        return sb.toString();
    }
}
