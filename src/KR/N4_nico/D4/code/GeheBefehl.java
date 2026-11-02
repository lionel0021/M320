package KR.N4_nico.D4.code;

public class GeheBefehl implements Befehl {
    public String schluesselwort() {
        return "gehe";
    }

    public String ausfuehren(Spielwelt welt, String argument) throws SpielException {
        if (argument.isBlank()) {
            throw new FehlendesArgumentException("Wohin? Zum Beispiel: gehe norden");
        }
        Spieler spieler = welt.getSpieler();
        Raum aktuell = spieler.getRaum();
        Raum ziel = aktuell.getAusgang(argument);
        if (ziel == null) {
            return "Dort geht es nicht weiter.";
        }
        String noetig = aktuell.benoetigterSchluessel(argument);
        if (noetig != null && !spieler.hat(noetig)) {
            return "Die Tuer ist verschlossen. Du brauchst: " + noetig;
        }
        spieler.setRaum(ziel);
        return ziel.untersuchen();
    }
}
