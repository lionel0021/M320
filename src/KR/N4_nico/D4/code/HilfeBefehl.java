package KR.N4_nico.D4.code;

public class HilfeBefehl implements Befehl {
    public String schluesselwort() {
        return "hilfe";
    }

    public String ausfuehren(Spielwelt welt, String argument) {
        return "Befehle: " + String.join(", ", welt.befehlsWoerter()) + ", ende";
    }
}
