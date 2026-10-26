package KR.N4_nico.M4.code;

public class DVD extends Medium {
    private final String regisseur;
    private final int minuten;

    public DVD(String titel, int jahr, String regisseur, int minuten) {
        super(titel, jahr);
        this.regisseur = regisseur;
        this.minuten = minuten;
    }

    public String art() {
        return "DVD";
    }

    public String beschreibung() {
        return "Regie " + regisseur + ", " + minuten + " Min";
    }
}
