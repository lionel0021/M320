package kn.d1.impl1;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Einfaches Bankkonto. Geld wird als Rappen gespeichert, damit keine
 * Rundungsfehler durch Gleitkommazahlen entstehen.
 */
public class Konto {
    private final String kontonummer;
    private final String inhaber;
    private long saldoInRappen;

    public Konto(String kontonummer, String inhaber, long startSaldoInRappen) {
        if (kontonummer == null || kontonummer.isBlank()) {
            throw new IllegalArgumentException("Die Kontonummer darf nicht leer sein.");
        }
        if (inhaber == null || inhaber.isBlank()) {
            throw new IllegalArgumentException("Der Inhaber darf nicht leer sein.");
        }
        if (startSaldoInRappen < 0) {
            throw new IllegalArgumentException("Der Startsaldo darf nicht negativ sein.");
        }

        this.kontonummer = kontonummer;
        this.inhaber = inhaber;
        this.saldoInRappen = startSaldoInRappen;
    }

    public String getKontonummer() {
        return kontonummer;
    }

    public String getInhaber() {
        return inhaber;
    }

    public long getSaldoInRappen() {
        return saldoInRappen;
    }

    public void einzahlen(long betragInRappen) {
        pruefePositivenBetrag(betragInRappen);
        saldoInRappen = Math.addExact(saldoInRappen, betragInRappen);
    }

    public void abheben(long betragInRappen) {
        pruefePositivenBetrag(betragInRappen);
        if (betragInRappen > saldoInRappen) {
            throw new IllegalStateException("Der Kontostand reicht nicht aus.");
        }
        saldoInRappen -= betragInRappen;
    }

    public void ueberweisenAn(Konto zielkonto, long betragInRappen) {
        Objects.requireNonNull(zielkonto, "Das Zielkonto darf nicht null sein.");
        if (zielkonto == this) {
            throw new IllegalArgumentException("Quell- und Zielkonto müssen verschieden sein.");
        }

        pruefePositivenBetrag(betragInRappen);
        if (betragInRappen > saldoInRappen) {
            throw new IllegalStateException("Der Kontostand reicht nicht aus.");
        }

        // Beide neuen Salden werden vor der ersten Zustandsänderung geprüft.
        // Dadurch bleibt die Überweisung auch bei einem Zahlenüberlauf atomar.
        long neuerZielsaldo = Math.addExact(zielkonto.saldoInRappen, betragInRappen);
        saldoInRappen -= betragInRappen;
        zielkonto.saldoInRappen = neuerZielsaldo;
    }

    public String saldoFormatiert() {
        return "CHF " + BigDecimal.valueOf(saldoInRappen, 2).toPlainString();
    }

    private static void pruefePositivenBetrag(long betragInRappen) {
        if (betragInRappen <= 0) {
            throw new IllegalArgumentException("Der Betrag muss grösser als null sein.");
        }
    }
}
