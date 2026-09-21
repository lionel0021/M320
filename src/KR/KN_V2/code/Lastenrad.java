package KR.KN_V2.code;

/**
 * Beschreibt ein Lastenrad fuer Lieferungen innerhalb einer maximalen Fahrstrecke.
 *
 * <p>Die Kosten entsprechen den Basiskosten pro Kilometer ohne Zuschlag.
 * Fuer eine Lieferung muss das Lastenrad verfuegbar sein und die geplante
 * Strecke innerhalb seiner Distanzgrenze liegen.</p>
 */
public class Lastenrad extends Lieferfahrzeug {
    private final double maxDistanzKm;

    public Lastenrad(String id, String kennzeichen, double basisKostenProKm, double aktuellerKilometerstand,
                     double maxDistanzKm) {
        super(id, kennzeichen, basisKostenProKm, aktuellerKilometerstand);
        this.maxDistanzKm = maxDistanzKm;
    }

    @Override
    public double berechneKosten(double distanzKm) {
        return getBasisKostenProKm() * distanzKm;
    }

    @Override
    public boolean kannLiefern(double distanzKm) {
        return istVerfuegbar() && distanzKm <= maxDistanzKm;
    }

    @Override
    public String printOut() {
        return super.printOut() + String.format(" | max %.0f km", maxDistanzKm);
    }
}
