package KR.N4_nico.M4.code;

public class MedienTest {
    public static void main(String[] args) {
        testPolymorphieInfo();
        testAusleihenUndZurueckgeben();
        testDoppeltesAusleihenWirft();
        testVerfuegbareListe();
        System.out.println("KN-M4 (nico): Alle Tests erfolgreich.");
    }

    private static void testPolymorphieInfo() {
        Medium buch = new Buch("Titel", 2000, "Autor", 100);
        Medium dvd = new DVD("Film", 2001, "Regie", 90);
        pruefe(buch.art().equals("Buch"), "Buch art falsch");
        pruefe(dvd.art().equals("DVD"), "DVD art falsch");
        pruefe(buch.info().contains("verfuegbar"), "neues Medium muss verfuegbar sein");
    }

    private static void testAusleihenUndZurueckgeben() {
        Medium m = new Buch("B", 2000, "A", 10);
        pruefe(m.istVerfuegbar(), "neu muss verfuegbar sein");
        m.ausleihen("Nico");
        pruefe(!m.istVerfuegbar(), "nach ausleihen nicht verfuegbar");
        m.zurueckgeben();
        pruefe(m.istVerfuegbar(), "nach zurueckgeben wieder verfuegbar");
    }

    private static void testDoppeltesAusleihenWirft() {
        Medium m = new DVD("F", 2000, "R", 90);
        m.ausleihen("A");
        pruefeWirft(() -> m.ausleihen("B"), "doppeltes Ausleihen muss werfen");
    }

    private static void testVerfuegbareListe() {
        Bibliothek bib = new Bibliothek();
        Medium a = new Buch("A", 2000, "x", 10);
        Medium b = new Zeitschrift("Z", 2000, 1);
        bib.aufnehmen(a);
        bib.aufnehmen(b);
        a.ausleihen("Nico");
        pruefe(bib.verfuegbare().size() == 1, "nur ein Medium darf verfuegbar sein");
        pruefe(bib.anzahl() == 2, "Bibliothek muss 2 Medien haben");
    }

    private interface Codeblock {
        void fuehreAus();
    }

    private static void pruefeWirft(Codeblock block, String meldung) {
        try {
            block.fuehreAus();
            throw new AssertionError("FEHLGESCHLAGEN (keine Exception): " + meldung);
        } catch (IllegalStateException erwartet) {
        }
    }

    private static void pruefe(boolean bedingung, String meldung) {
        if (!bedingung) {
            throw new AssertionError("FEHLGESCHLAGEN: " + meldung);
        }
    }
}
