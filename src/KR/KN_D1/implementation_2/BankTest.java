package KR.KN_D1.implementation_2;

public class BankTest {
    public static void main(String[] args) {
        testBankVerwaltetKontenUndTransfer();
        testUnbekanntesKontoWirdAbgelehnt();
        testKontolisteIstNichtVeraenderbar();
        testUeberweisungIstAtomar();
        testUeberweisungsverlaufIstMomentaufnahme();
        testBetragWirdExaktUmgewandelt();
        System.out.println("Implementation 2: Alle Tests erfolgreich.");
    }

    private static void testBankVerwaltetKontenUndTransfer() {
        Bank bank = new Bank("Testbank");
        Bankkonto quelle = bank.kontoEroeffnen("A", new Kunde(1, "Anna"));
        Bankkonto ziel = bank.kontoEroeffnen("B", new Kunde(2, "Ben"));
        bank.einzahlen("A", 10_000);
        bank.ueberweisen("A", "B", 2_500);

        pruefe(quelle.getSaldoInRappen() == 7_500, "Quellsaldo ist falsch");
        pruefe(ziel.getSaldoInRappen() == 2_500, "Zielsaldo ist falsch");
        pruefe(bank.getUeberweisungen().size() == 1, "Überweisung fehlt im Verlauf");
    }

    private static void testUnbekanntesKontoWirdAbgelehnt() {
        Bank bank = new Bank("Testbank");
        boolean fehlerErhalten = false;
        try {
            bank.einzahlen("UNBEKANNT", 100);
        } catch (IllegalArgumentException exception) {
            fehlerErhalten = true;
        }
        pruefe(fehlerErhalten, "Ein unbekanntes Konto muss abgelehnt werden");
    }

    private static void testKontolisteIstNichtVeraenderbar() {
        Bank bank = new Bank("Testbank");
        bank.kontoEroeffnen("A", new Kunde(1, "Anna"));
        boolean fehlerErhalten = false;
        try {
            bank.getKonten().clear();
        } catch (UnsupportedOperationException exception) {
            fehlerErhalten = true;
        }
        pruefe(fehlerErhalten, "Die Kontoliste darf von aussen nicht veränderbar sein");
    }

    private static void testUeberweisungIstAtomar() {
        Bank bank = new Bank("Testbank");
        Bankkonto quelle = bank.kontoEroeffnen("A", new Kunde(1, "Anna"));
        Bankkonto ziel = bank.kontoEroeffnen("B", new Kunde(2, "Ben"));
        bank.einzahlen("A", 1_000);
        bank.einzahlen("B", Long.MAX_VALUE);

        erwarteException(ArithmeticException.class, () -> bank.ueberweisen("A", "B", 1));

        pruefe(quelle.getSaldoInRappen() == 1_000, "Quellsaldo darf sich nicht verändern");
        pruefe(ziel.getSaldoInRappen() == Long.MAX_VALUE, "Zielsaldo darf sich nicht verändern");
        pruefe(bank.getUeberweisungen().isEmpty(), "Fehlgeschlagener Transfer darf nicht protokolliert werden");
    }

    private static void testUeberweisungsverlaufIstMomentaufnahme() {
        Bank bank = new Bank("Testbank");
        bank.kontoEroeffnen("A", new Kunde(1, "Anna"));
        bank.kontoEroeffnen("B", new Kunde(2, "Ben"));
        bank.einzahlen("A", 1_000);

        var vorherigerVerlauf = bank.getUeberweisungen();
        bank.ueberweisen("A", "B", 100);

        pruefe(vorherigerVerlauf.isEmpty(), "Eine zurückgegebene Liste darf sich nachträglich nicht ändern");
        pruefe(bank.getUeberweisungen().size() == 1, "Aktueller Verlauf ist falsch");
    }

    private static void testBetragWirdExaktUmgewandelt() {
        pruefe(BankSimulation.betragInRappenUmwandeln("12.34") == 1_234,
                "Punkt als Dezimaltrennzeichen wurde falsch umgewandelt");
        pruefe(BankSimulation.betragInRappenUmwandeln("12,34") == 1_234,
                "Komma als Dezimaltrennzeichen wurde falsch umgewandelt");
        erwarteException(IllegalArgumentException.class,
                () -> BankSimulation.betragInRappenUmwandeln("12.345"));
    }

    private static void erwarteException(Class<? extends Throwable> typ, Runnable aktion) {
        try {
            aktion.run();
        } catch (Throwable exception) {
            if (typ.isInstance(exception)) {
                return;
            }
            throw new AssertionError("Falscher Exception-Typ", exception);
        }
        throw new AssertionError("Erwartete Exception wurde nicht ausgelöst: " + typ.getSimpleName());
    }

    private static void pruefe(boolean bedingung, String meldung) {
        if (!bedingung) {
            throw new AssertionError(meldung);
        }
    }
}
