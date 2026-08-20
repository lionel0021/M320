public class Kunde {
    private final int kundennummer;
    private final String name;

    public Kunde(int kundennummer, String name) {
        if (kundennummer <= 0) {
            throw new IllegalArgumentException("Die Kundennummer muss positiv sein.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Der Name darf nicht leer sein.");
        }
        this.kundennummer = kundennummer;
        this.name = name;
    }

    public int getKundennummer() {
        return kundennummer;
    }

    public String getName() {
        return name;
    }
}
