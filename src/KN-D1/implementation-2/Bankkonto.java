import java.math.BigDecimal;
import java.util.Objects;

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
        saldoInRappen = saldoNachGutschrift(betragInRappen);
    }

    void belasten(long betragInRappen) {
        saldoInRappen = saldoNachBelastung(betragInRappen);
    }

    void ueberweisenAn(Bankkonto zielkonto, long betragInRappen) {
        Objects.requireNonNull(zielkonto, "Das Zielkonto darf nicht null sein.");
        if (zielkonto == this) {
            throw new IllegalArgumentException("Quell- und Zielkonto müssen verschieden sein.");
        }

        long neuerQuellsaldo = saldoNachBelastung(betragInRappen);
        long neuerZielsaldo = zielkonto.saldoNachGutschrift(betragInRappen);
        saldoInRappen = neuerQuellsaldo;
        zielkonto.saldoInRappen = neuerZielsaldo;
    }

    private long saldoNachGutschrift(long betragInRappen) {
        pruefePositivenBetrag(betragInRappen);
        return Math.addExact(saldoInRappen, betragInRappen);
    }

    private long saldoNachBelastung(long betragInRappen) {
        pruefePositivenBetrag(betragInRappen);
        if (betragInRappen > saldoInRappen) {
            throw new IllegalStateException("Der Kontostand reicht nicht aus.");
        }
        return saldoInRappen - betragInRappen;
    }

    public String beschreibung() {
        return String.format("%s – %s – CHF %s",
                kontonummer, inhaber.getName(),
                BigDecimal.valueOf(saldoInRappen, 2).toPlainString());
    }

    private static void pruefePositivenBetrag(long betragInRappen) {
        if (betragInRappen <= 0) {
            throw new IllegalArgumentException("Der Betrag muss grösser als null sein.");
        }
    }
}
