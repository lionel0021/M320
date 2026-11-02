package KR.N4_nico.D4.code;

public class BenutzeBefehl implements Befehl {
    public String schluesselwort() {
        return "benutze";
    }

    public String ausfuehren(Spielwelt welt, String argument) throws SpielException {
        if (argument.isBlank()) {
            throw new FehlendesArgumentException("Was benutzen? Zum Beispiel: benutze lampe");
        }
        Spieler spieler = welt.getSpieler();
        Gegenstand g = spieler.ausInventar(argument);
        if (g == null) {
            return "Das hast du nicht: " + argument;
        }
        if (g instanceof Benutzbar benutzbar) {
            return benutzbar.benutze(spieler);
        }
        return "Das kannst du nicht benutzen: " + argument;
    }
}
