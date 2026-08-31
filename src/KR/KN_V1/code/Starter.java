package KR.KN_V1.code;

import java.util.Scanner;

/** Startpunkt der interaktiven Bibliotheksverwaltung. */
public class Starter {
    public static void main(String[] args) {
        Bibliothek bibliothek = new Bibliothek();
        BibliotheksKonsole konsole = new BibliotheksKonsole(
                bibliothek, new Scanner(System.in), System.out);
        konsole.starten();
    }
}
