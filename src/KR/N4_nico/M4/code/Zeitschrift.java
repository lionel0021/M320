package KR.N4_nico.M4.code;

public class Zeitschrift extends Medium {
    private final int ausgabe;

    public Zeitschrift(String titel, int jahr, int ausgabe) {
        super(titel, jahr);
        this.ausgabe = ausgabe;
    }

    public String art() {
        return "Zeitschrift";
    }

    public String beschreibung() {
        return "Ausgabe Nr. " + ausgabe;
    }
}
