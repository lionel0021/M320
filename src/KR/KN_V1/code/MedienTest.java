package KR.KN_V1.code;

import java.util.Iterator;
import java.util.NoSuchElementException;

/** Einfache Tests ohne externe Testbibliothek. */
public class MedienTest {
    public static void main(String[] args) {
        testVererbungUndGemeinsameFunktionalitaet();
        testBibliothek();
        testMedienListe();
        testStandardIterator();
        testAusgelieheneMedienIterator();
        testEBook();
        System.out.println("KN-V1: Alle Tests erfolgreich.");
    }

    private static void testVererbungUndGemeinsameFunktionalitaet() {
        Buch buch = buch("B-1");
        pruefe(buch.getTitel().equals("Java"), "Titel wird geerbt");
        pruefe(!buch.isAusgeliehen(), "Medium ist anfangs verfuegbar");
        buch.ausleihen();
        pruefe(buch.isAusgeliehen(), "ausleihen setzt Status");
        buch.zurueckgeben();
        pruefe(!buch.isAusgeliehen(), "zurueckgeben setzt Status zurueck");
        erwarteFehler(IllegalStateException.class, buch::zurueckgeben,
                "nicht ausgeliehenes Medium kann nicht zurueckgegeben werden");
    }

    private static void testBibliothek() {
        Bibliothek bibliothek = new Bibliothek();
        Buch buch = buch("B-2");
        bibliothek.hinzufuegen(buch);
        bibliothek.hinzufuegen(new DVD("Film", 2020, "D-1", 90, "Regie"));
        bibliothek.hinzufuegen(new Zeitschrift("Magazin", 2026, "Z-1", 1, 8));
        pruefe(bibliothek.anzahlMedien() == 3, "drei Medientypen in einer Liste");
        pruefe(bibliothek.suchen("B-2") == buch, "suchen findet das Objekt");
        pruefe(bibliothek.suchen("X") == null, "suchen liefert null, wenn nichts passt");
        erwarteFehler(IllegalArgumentException.class,
                () -> bibliothek.hinzufuegen(buch("B-2")),
                "Inventarnummern muessen eindeutig sein");
        bibliothek.entfernen("D-1");
        pruefe(bibliothek.anzahlMedien() == 2, "entfernen reduziert Anzahl");
        bibliothek.entfernen("X");
        pruefe(bibliothek.anzahlMedien() == 2, "unbekannte Nummer ist kein Fehler");
    }

    private static void testMedienListe() {
        MedienListe liste = new MedienListe();
        Buch erstes = buch("B-3");
        Buch zweites = buch("B-4");
        pruefe(liste.add(erstes), "geerbtes add liefert true");
        liste.add(0, zweites);
        pruefe(liste.size() == 2, "size stimmt");
        pruefe(liste.get(0) == zweites, "add(index) und get funktionieren");
        pruefe(liste.contains(erstes), "contains wird von AbstractList bereitgestellt");
        liste.remove(0);
        pruefe(liste.size() == 1, "remove funktioniert");
    }

    private static void testStandardIterator() {
        MedienListe liste = new MedienListe();
        liste.add(buch("B-5"));
        liste.add(new DVD("Film", 2021, "D-2", 95, "Regie"));
        Iterator<Medium> iterator = liste.iterator();
        pruefe(iterator.hasNext(), "Standarditerator hat erstes Element");
        pruefe(iterator.next().getInventarnummer().equals("B-5"),
                "Standarditerator verwendet Listenreihenfolge");
        pruefe(iterator.next().getInventarnummer().equals("D-2"),
                "Standarditerator hat zweites Element");
        pruefe(!iterator.hasNext(), "Standarditerator ist danach leer");
    }

    private static void testAusgelieheneMedienIterator() {
        Bibliothek bibliothek = new Bibliothek();
        Buch frei = buch("B-6");
        Buch ausgeliehen1 = buch("B-7");
        DVD ausgeliehen2 = new DVD("Film", 2022, "D-3", 100, "Regie");
        ausgeliehen1.ausleihen();
        ausgeliehen2.ausleihen();
        bibliothek.hinzufuegen(frei);
        bibliothek.hinzufuegen(ausgeliehen1);
        bibliothek.hinzufuegen(new Zeitschrift("Magazin", 2026, "Z-2", 2, 7));
        bibliothek.hinzufuegen(ausgeliehen2);

        Iterator<Medium> iterator = bibliothek.ausgelieheneMedienIterator();
        pruefe(iterator.hasNext() && iterator.hasNext(),
                "mehrfaches hasNext darf nichts ueberspringen");
        pruefe(iterator.next() == ausgeliehen1, "erstes ausgeliehenes Medium");
        pruefe(iterator.next() == ausgeliehen2, "zweites ausgeliehenes Medium");
        pruefe(!iterator.hasNext(), "nur ausgeliehene Medien wurden geliefert");
        erwarteFehler(NoSuchElementException.class, iterator::next,
                "next muss am Ende NoSuchElementException werfen");
    }

    private static void testEBook() {
        EBook ebook = new EBook("Digital", 2026, "E-1", 4.2, "PDF");
        pruefe(ebook.getTitel().equals("Digital"), "gemeinsame Metadaten passen");
        pruefe(!ebook.isAusgeliehen(), "E-Book ist nicht physisch ausgeliehen");
        erwarteFehler(UnsupportedOperationException.class, ebook::ausleihen,
                "physisches Ausleihen ist fuer E-Books nicht sinnvoll");
    }

    private static Buch buch(String inventarnummer) {
        return new Buch("Java", 2025, inventarnummer, "Ada", "ISBN-" + inventarnummer);
    }

    private static void pruefe(boolean bedingung, String meldung) {
        if (!bedingung) {
            throw new AssertionError("FEHLGESCHLAGEN: " + meldung);
        }
    }

    private static void erwarteFehler(Class<? extends Throwable> typ,
                                      Runnable aktion, String meldung) {
        try {
            aktion.run();
            throw new AssertionError("FEHLGESCHLAGEN: " + meldung);
        } catch (Throwable fehler) {
            if (!typ.isInstance(fehler)) {
                throw new AssertionError("FEHLGESCHLAGEN: " + meldung
                        + " (falscher Fehlertyp: " + fehler + ")");
            }
        }
    }
}
