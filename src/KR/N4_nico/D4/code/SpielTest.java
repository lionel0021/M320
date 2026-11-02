package KR.N4_nico.D4.code;

public class SpielTest {
    public static void main(String[] args) throws Exception {
        testGenericInventar();
        testDelegationBewegung();
        testUngueltigeEingabeWirft();
        testUnbekannterBefehlWirft();
        testFehlendesArgumentWirft();
        testNehmenUndBenutzen();
        testVerschlosseneTuer();
        testGewinn();
        System.out.println("KN-D4 (nico): Alle Tests erfolgreich.");
    }

    private static void testGenericInventar() {
        Inventar<Gegenstand> inv = new Inventar<>();
        inv.hinzufuegen(new Gegenstand("stein", "grau"));
        inv.hinzufuegen(new Lampe());
        pruefe(inv.groesse() == 2, "Inventar muss 2 enthalten");
        pruefe(inv.enthaelt("LAMPE"), "finde muss Gross-/Kleinschreibung ignorieren");
        Gegenstand weg = inv.entferne("stein");
        pruefe(weg != null && weg.getName().equals("stein"), "entferne muss den Stein liefern");
        pruefe(inv.groesse() == 1, "nach entferne nur noch 1");
    }

    private static void testDelegationBewegung() throws Exception {
        Spielwelt welt = Welt.bauen();
        welt.verarbeite("gehe norden");
        pruefe(welt.getSpieler().getRaum().getName().equals("Halle"), "gehe norden muss in die Halle fuehren");
    }

    private static void testUngueltigeEingabeWirft() {
        Spielwelt welt = Welt.bauen();
        pruefeWirft(() -> welt.verarbeite("   "), UngueltigeEingabeException.class, "leere Eingabe");
    }

    private static void testUnbekannterBefehlWirft() {
        Spielwelt welt = Welt.bauen();
        pruefeWirft(() -> welt.verarbeite("tanze"), UnbekannterBefehlException.class, "unbekannter Befehl");
    }

    private static void testFehlendesArgumentWirft() {
        Spielwelt welt = Welt.bauen();
        pruefeWirft(() -> welt.verarbeite("gehe"), FehlendesArgumentException.class, "gehe ohne Richtung");
    }

    private static void testNehmenUndBenutzen() throws Exception {
        Spielwelt welt = Welt.bauen();
        welt.verarbeite("nimm lampe");
        String antwort = welt.verarbeite("benutze lampe");
        pruefe(antwort.contains("leuchtet"), "benutze lampe muss die Lampe einschalten");
    }

    private static void testVerschlosseneTuer() throws Exception {
        Spielwelt welt = Welt.bauen();
        welt.verarbeite("gehe norden");
        String antwort = welt.verarbeite("gehe osten");
        pruefe(antwort.contains("verschlossen"), "Schatzkammer ohne Schluessel muss verschlossen sein");
        pruefe(welt.getSpieler().getRaum().getName().equals("Halle"), "Spieler muss in der Halle bleiben");
    }

    private static void testGewinn() throws Exception {
        Spielwelt welt = Welt.bauen();
        welt.verarbeite("gehe norden");
        welt.verarbeite("gehe westen");
        welt.verarbeite("nimm schluessel");
        welt.verarbeite("gehe osten");
        welt.verarbeite("gehe osten");
        welt.verarbeite("nimm gold");
        pruefe(welt.istGewonnen(), "mit Gold im Inventar muss das Spiel gewonnen sein");
    }

    private interface Codeblock {
        void fuehreAus() throws Exception;
    }

    private static void pruefeWirft(Codeblock block, Class<? extends Exception> erwartet, String meldung) {
        try {
            block.fuehreAus();
            throw new AssertionError("FEHLGESCHLAGEN (keine Exception): " + meldung);
        } catch (Exception e) {
            if (!erwartet.isInstance(e)) {
                throw new AssertionError("FEHLGESCHLAGEN (falsche Exception " + e.getClass().getSimpleName() + "): " + meldung);
            }
        }
    }

    private static void pruefe(boolean bedingung, String meldung) {
        if (!bedingung) {
            throw new AssertionError("FEHLGESCHLAGEN: " + meldung);
        }
    }
}
