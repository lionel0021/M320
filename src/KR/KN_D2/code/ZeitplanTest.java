package kn.d2;

import java.util.List;

// Einfache Tests ohne externe Bibliothek (wie im Rest des Repos):
// per main-Methode ausfuehren.
public class ZeitplanTest {
    public static void main(String[] args) {
        testAggregationPassagierLebtWeiter();
        testDelegationPassagiereVon();
        testFluegeAbUhrzeit();
        testRemovePassagier();
        System.out.println("KN-D2: Alle Tests erfolgreich.");
    }

    private static void testAggregationPassagierLebtWeiter() {
        Passagier anna = new Passagier("Anna", "P1");
        Flug flug = new Flug("LX1", "Rom", 9, 0);
        flug.addPassagier(anna);
        flug.removePassagier(anna);
        // Aggregation: Passagier-Objekt existiert nach dem Entfernen weiter.
        pruefe(anna.getName().equals("Anna"), "Passagier darf nach remove nicht kaputt sein");
        pruefe(flug.anzahlPassagiere() == 0, "Flug muss leer sein");
    }

    private static void testDelegationPassagiereVon() {
        Zeitplan zp = new Zeitplan();
        Flug flug = new Flug("LX2", "Paris", 7, 15);
        flug.addPassagier(new Passagier("Ben", "P2"));
        flug.addPassagier(new Passagier("Cara", "P3"));
        zp.addFlug(flug);
        // Zeitplan delegiert an den Flug -> muss 2 liefern.
        pruefe(zp.passagiereVon("LX2") == 2, "Delegation muss 2 Passagiere melden");
        pruefe(zp.passagiereVon("GIBTS-NICHT") == 0, "unbekannter Flug -> 0");
    }

    private static void testFluegeAbUhrzeit() {
        Zeitplan zp = new Zeitplan();
        zp.addFlug(new Flug("LX3", "Wien", 6, 0));
        zp.addFlug(new Flug("LX4", "Oslo", 18, 30));
        List<Flug> abMittag = zp.fluegeAb(12, 0);
        pruefe(abMittag.size() == 1, "nur 1 Flug ab 12:00");
        pruefe(abMittag.get(0).getFlugNummer().equals("LX4"), "es muss LX4 sein");
    }

    private static void testRemovePassagier() {
        Flug flug = new Flug("LX5", "Madrid", 10, 45);
        Passagier p = new Passagier("Dora", "P4");
        flug.addPassagier(p);
        pruefe(flug.removePassagier(p), "remove muss true liefern");
        pruefe(!flug.removePassagier(p), "zweites remove muss false liefern");
    }

    private static void pruefe(boolean bedingung, String meldung) {
        if (!bedingung) {
            throw new AssertionError("FEHLGESCHLAGEN: " + meldung);
        }
    }
}
