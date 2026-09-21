package KR.KN_V2.code;

public class ElektroVan extends Lieferfahrzeug {
    private final double akkuProzent;
    private final double reichweiteKm;
    private static final double STROM_FAKTOR = 0.6;

    public ElektroVan(String id, String kennzeichen, double basisKostenProKm, double aktuellerKilometerstand,
                      double akkuProzent, double reichweiteKm) {
        super(id, kennzeichen, basisKostenProKm, aktuellerKilometerstand);
        this.akkuProzent = akkuProzent;
        this.reichweiteKm = reichweiteKm;
    }

    @Override
    public double berechneKosten(double distanzKm) {
        return getBasisKostenProKm() * distanzKm * STROM_FAKTOR;
    }

    @Override
    public boolean kannLiefern(double distanzKm) {
        return istVerfuegbar() && vorhandeneReichweite() >= distanzKm;
    }

    private double vorhandeneReichweite() {
        return reichweiteKm * (akkuProzent / 100.0);
    }

    @Override
    public String printOut() {
        return super.printOut() + String.format(" | Akku %.0f%%, Reichweite %.0f km",
                akkuProzent, vorhandeneReichweite());
    }
}
