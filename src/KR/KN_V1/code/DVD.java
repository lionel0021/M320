package KR.KN_V1.code;

import java.util.Objects;

public class DVD extends Medium {
    private final int laufzeitInMinuten;
    private final String regisseur;

    public DVD(String titel, int erscheinungsjahr, String inventarnummer,
               int laufzeitInMinuten, String regisseur) {
        super(titel, erscheinungsjahr, inventarnummer);
        if (laufzeitInMinuten <= 0) {
            throw new IllegalArgumentException("laufzeit muss positiv sein");
        }
        this.laufzeitInMinuten = laufzeitInMinuten;
        this.regisseur = Objects.requireNonNull(regisseur, "regisseur darf nicht null sein");
    }

    public int getLaufzeitInMinuten() {
        return laufzeitInMinuten;
    }

    public String getRegisseur() {
        return regisseur;
    }
}
