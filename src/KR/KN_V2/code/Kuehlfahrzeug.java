package KR.KN_V2.code;

public class Kuehlfahrzeug extends Lieferfahrzeug {
    private final double temperatur;
    private final boolean kuehlungAktiv;
    private static final double ZUSCHLAG_PRO_KM = 0.30;
    private static final double MAX_TEMPERATUR = 7.0;

    public Kuehlfahrzeug(String id, String kennzeichen, double basisKostenProKm, double aktuellerKilometerstand,
                         double temperatur, boolean kuehlungAktiv) {
        super(id, kennzeichen, basisKostenProKm, aktuellerKilometerstand);
        this.temperatur = temperatur;
        this.kuehlungAktiv = kuehlungAktiv;
    }

    @Override
    public double berechneKosten(double distanzKm) {
        double kosten = getBasisKostenProKm() * distanzKm;
        if (kuehlungAktiv) {
            kosten += ZUSCHLAG_PRO_KM * distanzKm;
        }
        return kosten;
    }

    @Override
    public boolean kannLiefern(double distanzKm) {
        return istVerfuegbar() && kuehlungAktiv && temperatur <= MAX_TEMPERATUR;
    }

    @Override
    public String printOut() {
        return super.printOut() + String.format(" | %.1f Grad, Kuehlung %s",
                temperatur, kuehlungAktiv ? "an" : "aus");
    }
}
