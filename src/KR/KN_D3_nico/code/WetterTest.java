package KR.KN_D3_nico.code;

import java.util.List;

public class WetterTest {
    public static void main(String[] args) throws Exception {
        testValidierungGueltig();
        testValidierungUngueltig();
        testDelegationLiefertDaten();
        testServiceFehlerWirdWeitergereicht();
        testKeinServiceAufrufBeiUngueltigerEingabe();
        System.out.println("KN-D3 (nico): Alle Tests erfolgreich.");
    }

    private static void testValidierungGueltig() throws Exception {
        EingabeValidator validator = new EingabeValidator();
        pruefe(validator.validiereAuswahl("zuerich").equals(List.of(Stadt.ZUERICH)), "zuerich muss ZUERICH liefern");
        pruefe(validator.validiereAuswahl("be").equals(List.of(Stadt.BERN)), "be muss BERN liefern");
        pruefe(validator.validiereAuswahl("ALLE").size() == 3, "ALLE muss 3 Staedte liefern");
    }

    private static void testValidierungUngueltig() {
        EingabeValidator validator = new EingabeValidator();
        pruefeWirft(() -> validator.validiereAuswahl("mond"), "unbekannte Stadt muss werfen");
        pruefeWirft(() -> validator.validiereAuswahl("  "), "leere Eingabe muss werfen");
    }

    private static void testDelegationLiefertDaten() throws Exception {
        WetterLogik logik = new WetterLogik(new EingabeValidator(), new LokalerWetterService());
        List<Wetter> daten = logik.abfragen("bern");
        pruefe(daten.size() == 1, "bern muss ein Ergebnis liefern");
        pruefe(Math.abs(daten.get(0).getTemperaturC() - 16.0) < 0.001, "Temperatur fuer Bern falsch");
        pruefe(daten.get(0).getZustand().equals("Regen"), "Zustand fuer Bern falsch");
    }

    private static void testServiceFehlerWirdWeitergereicht() {
        WetterService kaputt = staedte -> {
            throw new WetterException("Service nicht erreichbar");
        };
        WetterLogik logik = new WetterLogik(new EingabeValidator(), kaputt);
        pruefeWirft(() -> logik.abfragen("genf"), "Service-Fehler muss als WetterException ankommen");
    }

    private static void testKeinServiceAufrufBeiUngueltigerEingabe() {
        boolean[] aufgerufen = {false};
        WetterService wachhund = staedte -> {
            aufgerufen[0] = true;
            return List.of();
        };
        WetterLogik logik = new WetterLogik(new EingabeValidator(), wachhund);
        pruefeWirft(() -> logik.abfragen("xxx"), "ungueltige Eingabe muss werfen");
        pruefe(!aufgerufen[0], "bei ungueltiger Eingabe darf der Service nicht aufgerufen werden");
    }

    private interface Codeblock {
        void fuehreAus() throws Exception;
    }

    private static void pruefeWirft(Codeblock block, String meldung) {
        try {
            block.fuehreAus();
            throw new AssertionError("FEHLGESCHLAGEN (keine Exception): " + meldung);
        } catch (UngueltigeEingabeException | WetterException erwartet) {
        } catch (Exception andere) {
            throw new AssertionError("FEHLGESCHLAGEN (falsche Exception): " + meldung);
        }
    }

    private static void pruefe(boolean bedingung, String meldung) {
        if (!bedingung) {
            throw new AssertionError("FEHLGESCHLAGEN: " + meldung);
        }
    }
}
