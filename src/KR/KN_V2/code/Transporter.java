package KR.KN_V2.code;

/**
 * Beschreibt einen Transporter mit begrenzter Nutzlast und beladungsabhaengigen Kosten.
 *
 * <p>Eine Lieferung ist nur moeglich, wenn das Fahrzeug verfuegbar ist und die
 * aktuelle Ladung die maximale Nutzlast nicht ueberschreitet. Eine Begrenzung
 * der Fahrstrecke wird nicht modelliert.</p>
 */
public class Transporter extends Lieferfahrzeug {
    private final double maxNutzlastKg;
    private final double aktuelleLadungKg;

    public Transporter(String id, String kennzeichen, double basisKostenProKm, double aktuellerKilometerstand,
                       double maxNutzlastKg, double aktuelleLadungKg) {
        super(id, kennzeichen, basisKostenProKm, aktuellerKilometerstand);
        this.maxNutzlastKg = maxNutzlastKg;
        this.aktuelleLadungKg = aktuelleLadungKg;
    }

    /**
     * Beruecksichtigt die Beladung durch einen linear steigenden Kostenzuschlag:
     * leer gelten die Basiskosten, bei voller Nutzlast liegen sie 50 Prozent hoeher.
     * Die Berechnung setzt eine positive maximale Nutzlast voraus.
     *
     * @param distanzKm geplante Strecke in Kilometern
     * @return beladungsabhaengige Kosten in CHF, auch bei Ueberladung
     */
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
