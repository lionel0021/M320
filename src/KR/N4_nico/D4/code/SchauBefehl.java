package KR.N4_nico.D4.code;

public class SchauBefehl implements Befehl {
    public String schluesselwort() {
        return "schau";
    }

    public String ausfuehren(Spielwelt welt, String argument) {
        return welt.getSpieler().getRaum().untersuchen();
    }
}
