package KR.KN_D1.garage;

import java.math.BigDecimal;

/**
 * Ein Fahrzeug in der Garage. Kennzeichen und Reparaturkosten sind gekapselt.
 * Der Reparatur-Status kann nur ueber markiereRepariert() gesetzt werden.
 */
public class Fahrzeug {
    private final String kennzeichen;
    private final long kostenInRappen;
    private boolean repariert;

    public Fahrzeug(String kennzeichen, long kostenInRappen) {
        if (kennzeichen == null || kennzeichen.isBlank()) {
            throw new IllegalArgumentException("Das Kennzeichen darf nicht leer sein.");
        }
        if (kostenInRappen < 0) {
            throw new IllegalArgumentException("Die Reparaturkosten duerfen nicht negativ sein.");
        }
        this.kennzeichen = kennzeichen;
        this.kostenInRappen = kostenInRappen;
        this.repariert = false;
    }

    public String getKennzeichen() {
        return kennzeichen;
    }

    public long getKostenInRappen() {
        return kostenInRappen;
    }

    public boolean istRepariert() {
        return repariert;
    }

    public void markiereRepariert() {
        this.repariert = true;
    }

    public String kostenFormatiert() {
        return "CHF " + BigDecimal.valueOf(kostenInRappen, 2).toPlainString();
    }

    @Override
    public String toString() {
        String status = repariert ? "repariert" : "offen";
        return kennzeichen + " - " + kostenFormatiert() + " (" + status + ")";
    }
}
