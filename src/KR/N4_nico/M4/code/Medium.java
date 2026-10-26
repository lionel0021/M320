package KR.N4_nico.M4.code;

public abstract class Medium implements Ausleihbar {
    private final String titel;
    private final int jahr;
    private String ausgeliehenAn;

    protected Medium(String titel, int jahr) {
        this.titel = titel;
        this.jahr = jahr;
        this.ausgeliehenAn = null;
    }

    public String getTitel() {
        return titel;
    }

    public int getJahr() {
        return jahr;
    }

    public abstract String art();

    public abstract String beschreibung();

    public boolean istVerfuegbar() {
        return ausgeliehenAn == null;
    }

    public void ausleihen(String name) {
        if (!istVerfuegbar()) {
            throw new IllegalStateException(titel + " ist bereits ausgeliehen an " + ausgeliehenAn);
        }
        ausgeliehenAn = name;
    }

    public void zurueckgeben() {
        ausgeliehenAn = null;
    }

    public String status() {
        return istVerfuegbar() ? "verfuegbar" : "ausgeliehen an " + ausgeliehenAn;
    }

    public String info() {
        return art() + ": " + titel + " (" + jahr + ") - " + beschreibung() + " [" + status() + "]";
    }
}
