package KR.KN_V2.code;

public abstract class Lieferfahrzeug {
    private final String id;
    private final String kennzeichen;
    private final double basisKostenProKm;
    private double aktuellerKilometerstand;
    private boolean verfuegbar;

    protected Lieferfahrzeug(String id, String kennzeichen, double basisKostenProKm, double aktuellerKilometerstand) {
        this.id = id;
        this.kennzeichen = kennzeichen;
        this.basisKostenProKm = basisKostenProKm;
        this.aktuellerKilometerstand = aktuellerKilometerstand;
        this.verfuegbar = true;
    }

    public abstract double berechneKosten(double distanzKm);

    public abstract boolean kannLiefern(double distanzKm);

    public void reservieren() {
        if (!verfuegbar) {
            throw new IllegalStateException("Fahrzeug " + id + " ist bereits reserviert.");
        }
        verfuegbar = false;
    }

    public void freigeben() {
        if (verfuegbar) {
            throw new IllegalStateException("Fahrzeug " + id + " ist bereits frei.");
        }
        verfuegbar = true;
    }

    public String info() {
        return getClass().getSimpleName() + " " + id + " (" + kennzeichen + ")";
    }

    public String printOut() {
        String status = verfuegbar ? "verfuegbar" : "reserviert";
        return String.format("%s | Basis %.2f CHF/km | %.0f km | %s",
                info(), basisKostenProKm, aktuellerKilometerstand, status);
    }

    public String getId() {
        return id;
    }

    public String getKennzeichen() {
        return kennzeichen;
    }

    public double getBasisKostenProKm() {
        return basisKostenProKm;
    }

    public double getAktuellerKilometerstand() {
        return aktuellerKilometerstand;
    }

    public boolean istVerfuegbar() {
        return verfuegbar;
    }
}
