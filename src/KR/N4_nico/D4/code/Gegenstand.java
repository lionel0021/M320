package KR.N4_nico.D4.code;

public class Gegenstand extends Spielobjekt {
    public Gegenstand(String name, String beschreibung) {
        super(name, beschreibung);
    }

    public String untersuchen() {
        return getBeschreibung();
    }
}
