package KR.KN_V1.code;

public class Zeitschrift extends Medium {
    private final int ausgabe;
    private final int monat;

    public Zeitschrift(String titel, int erscheinungsjahr, String inventarnummer,
                       int ausgabe, int monat) {
        super(titel, erscheinungsjahr, inventarnummer);
        if (ausgabe <= 0) {
            throw new IllegalArgumentException("ausgabe muss positiv sein");
        }
        if (monat < 1 || monat > 12) {
            throw new IllegalArgumentException("monat muss zwischen 1 und 12 liegen");
        }
        this.ausgabe = ausgabe;
        this.monat = monat;
    }

    public int getAusgabe() {
        return ausgabe;
    }

    public int getMonat() {
        return monat;
    }
}
