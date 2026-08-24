package kn.d1.impl2;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Bank {
    private final String name;
    private final Map<String, Bankkonto> konten = new LinkedHashMap<>();
    private final List<Ueberweisung> ueberweisungen = new ArrayList<>();

    public Bank(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Der Bankname darf nicht leer sein.");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Bankkonto kontoEroeffnen(String kontonummer, Kunde inhaber) {
        Bankkonto konto = new Bankkonto(kontonummer, inhaber);
        if (konten.containsKey(kontonummer)) {
            throw new IllegalArgumentException("Diese Kontonummer existiert bereits.");
        }
        konten.put(kontonummer, konto);
        return konto;
    }

    public Bankkonto findeKonto(String kontonummer) {
        Bankkonto konto = konten.get(kontonummer);
        if (konto == null) {
            throw new IllegalArgumentException("Konto nicht gefunden: " + kontonummer);
        }
        return konto;
    }

    public void einzahlen(String kontonummer, long betragInRappen) {
        findeKonto(kontonummer).gutschreiben(betragInRappen);
    }

    public void abheben(String kontonummer, long betragInRappen) {
        findeKonto(kontonummer).belasten(betragInRappen);
    }

    public void ueberweisen(String vonKontonummer, String zuKontonummer, long betragInRappen) {
        Bankkonto quelle = findeKonto(vonKontonummer);
        Bankkonto ziel = findeKonto(zuKontonummer);
        quelle.ueberweisenAn(ziel, betragInRappen);
        ueberweisungen.add(new Ueberweisung(
                quelle.getKontonummer(), ziel.getKontonummer(), betragInRappen));
    }

    public List<Bankkonto> getKonten() {
        return List.copyOf(konten.values());
    }

    public List<Ueberweisung> getUeberweisungen() {
        return List.copyOf(ueberweisungen);
    }
}
