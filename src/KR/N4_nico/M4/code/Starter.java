package KR.N4_nico.M4.code;

public class Starter {
    public static void main(String[] args) {
        Bibliothek bib = new Bibliothek();
        bib.aufnehmen(new Buch("Clean Code", 2008, "Robert C. Martin", 464));
        bib.aufnehmen(new DVD("Inception", 2010, "Christopher Nolan", 148));
        bib.aufnehmen(new Zeitschrift("CHIP", 2026, 11));

        System.out.println("=== Alle Medien ===");
        for (Medium m : bib.alle()) {
            System.out.println(m.info());
        }

        System.out.println();
        System.out.println("Leihe 'Clean Code' an Nico aus...");
        bib.alle().get(0).ausleihen("Nico");

        System.out.println();
        System.out.println("=== Verfuegbar ===");
        for (Medium m : bib.verfuegbare()) {
            System.out.println(m.info());
        }
    }
}
