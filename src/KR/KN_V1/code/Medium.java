package KR.KN_V1.code;

import java.util.Objects;

/** Gemeinsame Basisklasse aller Medien der Schulbibliothek. */
public class Medium {
    private final String titel;
    private final int erscheinungsjahr;
    private final String inventarnummer;
    private boolean ausgeliehen;

    public Medium(String titel, int erscheinungsjahr, String inventarnummer) {
        this.titel = Objects.requireNonNull(titel, "titel darf nicht null sein");
        this.inventarnummer = Objects.requireNonNull(
                inventarnummer, "inventarnummer darf nicht null sein");
        if (titel.isBlank()) {
            throw new IllegalArgumentException("titel darf nicht leer sein");
        }
        if (inventarnummer.isBlank()) {
            throw new IllegalArgumentException("inventarnummer darf nicht leer sein");
        }
        if (erscheinungsjahr < 0) {
            throw new IllegalArgumentException("erscheinungsjahr darf nicht negativ sein");
        }
        this.erscheinungsjahr = erscheinungsjahr;
    }

    public String getTitel() {
        return titel;
    }

    public int getErscheinungsjahr() {
        return erscheinungsjahr;
    }

    public String getInventarnummer() {
        return inventarnummer;
    }

    public boolean isAusgeliehen() {
        return ausgeliehen;
    }

    public void ausleihen() {
        if (ausgeliehen) {
            throw new IllegalStateException(titel + " ist bereits ausgeliehen");
        }
        ausgeliehen = true;
    }

    public void zurueckgeben() {
        if (!ausgeliehen) {
            throw new IllegalStateException(titel + " ist nicht ausgeliehen");
        }
        ausgeliehen = false;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + inventarnummer + ", " + titel
                + ", " + erscheinungsjahr + ", ausgeliehen=" + ausgeliehen + "}";
    }
}
