package KR.N4_nico.D4.code;

public abstract class Spielobjekt {
    private final String name;
    private final String beschreibung;

    protected Spielobjekt(String name, String beschreibung) {
        this.name = name;
        this.beschreibung = beschreibung;
    }

    public String getName() {
        return name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public abstract String untersuchen();
}
