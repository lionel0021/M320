package KR.N4_nico.D4.code;

public class NimmBefehl implements Befehl {
    public String schluesselwort() {
        return "nimm";
    }

    public String ausfuehren(Spielwelt welt, String argument) throws SpielException {
        if (argument.isBlank()) {
            throw new FehlendesArgumentException("Was nehmen? Zum Beispiel: nimm lampe");
        }
        Spieler spieler = welt.getSpieler();
        Gegenstand g = spieler.getRaum().nimmGegenstand(argument);
        if (g == null) {
            return "Das gibt es hier nicht: " + argument;
        }
        spieler.nimm(g);
        return "Du nimmst: " + g.getName();
    }
}
