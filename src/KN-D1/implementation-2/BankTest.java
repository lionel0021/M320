public class BankTest {
    public static void main(String[] args) {
        testBankVerwaltetKontenUndTransfer();
        testUnbekanntesKontoWirdAbgelehnt();
        testKontolisteIstNichtVeraenderbar();
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

    private static void pruefe(boolean bedingung, String meldung) {
        if (!bedingung) {
            throw new AssertionError(meldung);
        }
    }
}
