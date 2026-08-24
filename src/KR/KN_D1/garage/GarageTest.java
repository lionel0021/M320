package KR.KN_D1.garage;

public class GarageTest {
    public static void main(String[] args) {
        testRegistrierenUndSuchen();
        testReparaturStatus();
        testGesamtkosten();
        testDoppeltesKennzeichen();
        System.out.println("Garage: Alle Tests erfolgreich.");
    }

    private static void testRegistrierenUndSuchen() {
        Garage garage = new Garage();
        garage.registriere("ZH-1", 10_000);
        pruefe(garage.anzahl() == 1, "Anzahl muss 1 sein");
        pruefe(garage.suche("ZH-1") != null, "Fahrzeug muss gefunden werden");
        pruefe(garage.suche("GIBTS-NICHT") == null, "unbekanntes Kennzeichen -> null");
    }

    private static void testReparaturStatus() {
        Garage garage = new Garage();
        garage.registriere("ZH-2", 20_000);
        pruefe(garage.reparierteFahrzeuge().isEmpty(), "am Anfang nichts repariert");
        pruefe(garage.markiereRepariert("ZH-2"), "markieren muss true liefern");
        pruefe(garage.reparierteFahrzeuge().size() == 1, "1 Fahrzeug repariert");
        pruefe(garage.offeneFahrzeuge().isEmpty(), "keine offenen mehr");
        pruefe(!garage.markiereRepariert("ZH-X"), "unbekannt -> false");
    }

    private static void testGesamtkosten() {
        Garage garage = new Garage();
        garage.registriere("ZH-3", 30_000);
        garage.registriere("ZH-4", 45_000);
        pruefe(garage.gesamtkostenInRappen() == 75_000, "Gesamtkosten muessen 75000 Rappen sein");
    }

    private static void testDoppeltesKennzeichen() {
        Garage garage = new Garage();
        garage.registriere("ZH-5", 5_000);
        try {
            garage.registriere("ZH-5", 9_000);
            throw new AssertionError("doppeltes Kennzeichen haette Fehler werfen muessen");
        } catch (IllegalArgumentException erwartet) {
            // ok
        }
    }

    private static void pruefe(boolean bedingung, String meldung) {
        if (!bedingung) {
            throw new AssertionError("FEHLGESCHLAGEN: " + meldung);
        }
    }
}
