package KR.KN_V1.code;

import java.util.Objects;

/**
 * Demonstriert die spaetere Anforderung aus Teil 9. Ein E-Book ist zwar ein
 * Medium, kann aber nicht physisch ausgeliehen werden. Die Overrides zeigen
 * bewusst, weshalb die urspruengliche Hierarchie fuer diese Anforderung nicht
 * mehr ideal ist (siehe README, Antworten 14-17).
 */
public class EBook extends Medium {
    private final double dateigroesseInMegabyte;
    private final String dateiformat;

    public EBook(String titel, int erscheinungsjahr, String inventarnummer,
                 double dateigroesseInMegabyte, String dateiformat) {
        super(titel, erscheinungsjahr, inventarnummer);
        if (dateigroesseInMegabyte <= 0) {
            throw new IllegalArgumentException("dateigroesse muss positiv sein");
        }
        this.dateigroesseInMegabyte = dateigroesseInMegabyte;
        this.dateiformat = Objects.requireNonNull(
                dateiformat, "dateiformat darf nicht null sein");
    }

    public double getDateigroesseInMegabyte() {
        return dateigroesseInMegabyte;
    }

    public String getDateiformat() {
        return dateiformat;
    }

    @Override
    public void ausleihen() {
        throw new UnsupportedOperationException("Ein E-Book wird nicht physisch ausgeliehen");
    }

    @Override
    public void zurueckgeben() {
        throw new UnsupportedOperationException("Ein E-Book wird nicht physisch zurueckgegeben");
    }
}
