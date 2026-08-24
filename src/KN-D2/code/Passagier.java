package kn.d2;

// Eigenstaendiges Objekt. Ein Passagier existiert unabhaengig von einem Flug
// und kann sogar auf mehreren Fluegen stehen. -> Teil einer AGGREGATION.
public class Passagier {
    private final String name;
    private final String passNummer;

    public Passagier(String name, String passNummer) {
        this.name = name;
        this.passNummer = passNummer;
    }

    public String getName() {
        return name;
    }

    public String getPassNummer() {
        return passNummer;
    }

    @Override
    public String toString() {
        return name + " (" + passNummer + ")";
    }
}
