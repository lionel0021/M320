package KR.KN_V2.code;

public class Transporter extends Lieferfahrzeug {
    private final double maxNutzlastKg;
    private final double aktuelleLadungKg;

    public Transporter(String id, String kennzeichen, double basisKostenProKm, double aktuellerKilometerstand,
                       double maxNutzlastKg, double aktuelleLadungKg) {
        super(id, kennzeichen, basisKostenProKm, aktuellerKilometerstand);
        this.maxNutzlastKg = maxNutzlastKg;
        this.aktuelleLadungKg = aktuelleLadungKg;
    }

    @Override
    public double berechneKosten(double distanzKm) {
        double ladungsfaktor = 1.0 + (aktuelleLadungKg / maxNutzlastKg) * 0.5;
        return getBasisKostenProKm() * distanzKm * ladungsfaktor;
    }

    @Override
    public boolean kannLiefern(double distanzKm) {
        return istVerfuegbar() && aktuelleLadungKg <= maxNutzlastKg;
    }

    @Override
    public String printOut() {
        return super.printOut() + String.format(" | Ladung %.0f/%.0f kg", aktuelleLadungKg, maxNutzlastKg);
    }
}
