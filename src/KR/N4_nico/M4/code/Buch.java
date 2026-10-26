package KR.N4_nico.M4.code;

public class Buch extends Medium {
    private final String autor;
    private final int seiten;

    public Buch(String titel, int jahr, String autor, int seiten) {
        super(titel, jahr);
        this.autor = autor;
        this.seiten = seiten;
    }

    public String art() {
        return "Buch";
    }

    public String beschreibung() {
        return "von " + autor + ", " + seiten + " Seiten";
    }
}
