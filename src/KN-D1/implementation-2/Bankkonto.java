public class Bankkonto {
    private final String kontonummer;
    private final Kunde inhaber;
    private long saldoInRappen;

    public Bankkonto(String kontonummer, Kunde inhaber) {
        if (kontonummer == null || kontonummer.isBlank()) {
            throw new IllegalArgumentException("Die Kontonummer darf nicht leer sein.");
        }
        if (inhaber == null) {
            throw new IllegalArgumentException("Der Inhaber darf nicht null sein.");
        }
        this.kontonummer = kontonummer;
        this.inhaber = inhaber;
    }

    public String getKontonummer() {
        return kontonummer;
    }

    public Kunde getInhaber() {
        return inhaber;
    }

    public long getSaldoInRappen() {
        return saldoInRappen;
    }

    void gutschreiben(long betragInRappen) {
        pruefePositivenBetrag(betragInRappen);
        saldoInRappen = Math.addExact(saldoInRappen, betragInRappen);
    }

    void belasten(long betragInRappen) {
        pruefePositivenBetrag(betragInRappen);
        if (betragInRappen > saldoInRappen) {
            throw new IllegalStateException("Der Kontostand reicht nicht aus.");
        }
        saldoInRappen -= betragInRappen;
    }

    public String beschreibung() {
        return String.format("%s – %s – CHF %.2f",
                kontonummer, inhaber.getName(), saldoInRappen / 100.0);
    }

    private static void pruefePositivenBetrag(long betragInRappen) {
        if (betragInRappen <= 0) {
            throw new IllegalArgumentException("Der Betrag muss grösser als null sein.");
        }
    }
}
