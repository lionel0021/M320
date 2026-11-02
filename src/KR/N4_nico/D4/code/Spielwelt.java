package KR.N4_nico.D4.code;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Spielwelt {
    private final Spieler spieler;
    private final Map<String, Befehl> befehle = new LinkedHashMap<>();

    public Spielwelt(Spieler spieler) {
        this.spieler = spieler;
    }

    public Spieler getSpieler() {
        return spieler;
    }

    public void registriere(Befehl befehl) {
        befehle.put(befehl.schluesselwort(), befehl);
    }

    public List<String> befehlsWoerter() {
        return new ArrayList<>(befehle.keySet());
    }

    public String aktuellerRaumText() {
        return spieler.getRaum().untersuchen();
    }

    public boolean istGewonnen() {
        return spieler.hat("gold");
    }

    public String verarbeite(String eingabe) throws SpielException {
        if (eingabe == null || eingabe.isBlank()) {
            throw new UngueltigeEingabeException("Bitte gib einen Befehl ein.");
        }
        String[] teile = eingabe.trim().toLowerCase().split("\\s+", 2);
        Befehl befehl = befehle.get(teile[0]);
        if (befehl == null) {
            throw new UnbekannterBefehlException(teile[0]);
        }
        String argument = teile.length > 1 ? teile[1] : "";
        return befehl.ausfuehren(this, argument);
    }
}
