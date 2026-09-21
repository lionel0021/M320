package KR.KN_V2.code;

/**
 * Definiert die gemeinsamen Eigenschaften und den Reservierungszustand eines Lieferfahrzeugs.
 *
 * <p>Die Unterklassen bestimmen Kosten und Liefereignung, damit {@link Fuhrpark}
 * unterschiedliche Fahrzeugtypen einheitlich verwalten kann. Neue Fahrzeuge
 * sind zunaechst verfuegbar und koennen reserviert und wieder freigegeben werden.</p>
 */
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

    /**
     * Berechnet die Kosten unabhaengig davon, ob das Fahrzeug die Strecke liefern kann.
     * Fuer die Einsatzplanung muss zusaetzlich {@link #kannLiefern(double)} geprueft werden.
     *
     * @param distanzKm geplante Strecke in Kilometern
     * @return fahrzeugabhaengige Kosten in CHF
     */
    public abstract double berechneKosten(double distanzKm);

    /**
     * Prueft Verfuegbarkeit und fahrzeugspezifische Grenzen, ohne das Fahrzeug zu reservieren.
     *
     * @param distanzKm geplante Strecke in Kilometern
     * @return {@code true}, wenn das Fahrzeug frei ist und seine Lieferbedingungen erfuellt
     */
    public abstract boolean kannLiefern(double distanzKm);

    /**
     * Reserviert das Fahrzeug ohne Pruefung einer konkreten Lieferstrecke.
     *
     * @throws IllegalStateException wenn das Fahrzeug bereits reserviert ist
     */
    public void reservieren() {
        if (!verfuegbar) {
            throw new IllegalStateException("Fahrzeug " + id + " ist bereits reserviert.");
        }
        verfuegbar = false;
    }

    /**
     * Gibt eine bestehende Reservierung frei; eine doppelte Freigabe gilt als Bedienfehler.
     *
     * @throws IllegalStateException wenn das Fahrzeug bereits frei ist
     */
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
