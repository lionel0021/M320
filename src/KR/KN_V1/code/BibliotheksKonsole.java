package KR.KN_V1.code;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.Objects;
import java.util.Scanner;

/** Menügesteuerte Benutzerschnittstelle für die Bibliothek. */
public class BibliotheksKonsole {
    private final Bibliothek bibliothek;
    private final Scanner scanner;
    private final PrintStream ausgabe;

    public BibliotheksKonsole(Bibliothek bibliothek, Scanner scanner, PrintStream ausgabe) {
        this.bibliothek = Objects.requireNonNull(bibliothek, "bibliothek darf nicht null sein");
        this.scanner = Objects.requireNonNull(scanner, "scanner darf nicht null sein");
        this.ausgabe = Objects.requireNonNull(ausgabe, "ausgabe darf nicht null sein");
    }

    public void starten() {
        ausgabe.println("Willkommen bei der Medienbibliothek.");
        boolean laeuft = true;

        while (laeuft && scanner.hasNextLine()) {
            menueAnzeigen();
            String auswahl = scanner.nextLine().trim();

            try {
                switch (auswahl) {
                    case "1" -> buchHinzufuegen();
                    case "2" -> zeitschriftHinzufuegen();
                    case "3" -> dvdHinzufuegen();
                    case "4" -> ebookHinzufuegen();
                    case "5" -> alleMedienAnzeigen();
                    case "6" -> mediumSuchen();
                    case "7" -> mediumAusleihen();
                    case "8" -> mediumZurueckgeben();
                    case "9" -> mediumEntfernen();
                    case "10" -> ausgelieheneMedienAnzeigen();
                    case "0" -> laeuft = false;
                    default -> ausgabe.println("Ungültige Auswahl.");
                }
            } catch (IllegalArgumentException | IllegalStateException
                     | UnsupportedOperationException exception) {
                ausgabe.println("Fehler: " + exception.getMessage());
            }
        }

        ausgabe.println("Auf Wiedersehen.");
    }

    private void menueAnzeigen() {
        ausgabe.println();
        ausgabe.println("1 Buch hinzufügen");
        ausgabe.println("2 Zeitschrift hinzufügen");
        ausgabe.println("3 DVD hinzufügen");
        ausgabe.println("4 E-Book hinzufügen");
        ausgabe.println("5 Alle Medien anzeigen");
        ausgabe.println("6 Medium suchen");
        ausgabe.println("7 Medium ausleihen");
        ausgabe.println("8 Medium zurückgeben");
        ausgabe.println("9 Medium entfernen");
        ausgabe.println("10 Ausgeliehene Medien anzeigen");
        ausgabe.println("0 Beenden");
        ausgabe.print("Auswahl: ");
    }

    private void buchHinzufuegen() {
        String titel = textLesen("Titel: ");
        int jahr = intLesen("Erscheinungsjahr: ");
        String nummer = textLesen("Inventarnummer: ");
        String autor = textLesen("Autor: ");
        String isbn = textLesen("ISBN: ");
        bibliothek.hinzufuegen(new Buch(titel, jahr, nummer, autor, isbn));
        ausgabe.println("Buch wurde hinzugefügt.");
    }

    private void zeitschriftHinzufuegen() {
        String titel = textLesen("Titel: ");
        int jahr = intLesen("Erscheinungsjahr: ");
        String nummer = textLesen("Inventarnummer: ");
        int ausgabeNummer = intLesen("Ausgabe: ");
        int monat = intLesen("Monat (1-12): ");
        bibliothek.hinzufuegen(
                new Zeitschrift(titel, jahr, nummer, ausgabeNummer, monat));
        ausgabe.println("Zeitschrift wurde hinzugefügt.");
    }

    private void dvdHinzufuegen() {
        String titel = textLesen("Titel: ");
        int jahr = intLesen("Erscheinungsjahr: ");
        String nummer = textLesen("Inventarnummer: ");
        int laufzeit = intLesen("Laufzeit in Minuten: ");
        String regisseur = textLesen("Regisseur: ");
        bibliothek.hinzufuegen(new DVD(titel, jahr, nummer, laufzeit, regisseur));
        ausgabe.println("DVD wurde hinzugefügt.");
    }

    private void ebookHinzufuegen() {
        String titel = textLesen("Titel: ");
        int jahr = intLesen("Erscheinungsjahr: ");
        String nummer = textLesen("Inventarnummer: ");
        double groesse = doubleLesen("Dateigröße in MB: ");
        String format = textLesen("Dateiformat: ");
        bibliothek.hinzufuegen(new EBook(titel, jahr, nummer, groesse, format));
        ausgabe.println("E-Book wurde hinzugefügt.");
    }

    private void alleMedienAnzeigen() {
        if (bibliothek.anzahlMedien() == 0) {
            ausgabe.println("Die Bibliothek ist leer.");
            return;
        }
        ausgabe.println("Alle Medien:");
        for (Medium medium : bibliothek.getMedien()) {
            ausgabe.println("- " + medium);
        }
    }

    private void mediumSuchen() {
        Medium medium = mediumNachNummerLesen();
        ausgabe.println(medium == null ? "Kein Medium gefunden." : medium);
    }

    private void mediumAusleihen() {
        Medium medium = mediumNachNummerLesen();
        if (medium == null) {
            ausgabe.println("Kein Medium gefunden.");
            return;
        }
        medium.ausleihen();
        ausgabe.println("Medium wurde ausgeliehen.");
    }

    private void mediumZurueckgeben() {
        Medium medium = mediumNachNummerLesen();
        if (medium == null) {
            ausgabe.println("Kein Medium gefunden.");
            return;
        }
        medium.zurueckgeben();
        ausgabe.println("Medium wurde zurückgegeben.");
    }

    private void mediumEntfernen() {
        String nummer = textLesen("Inventarnummer: ");
        if (bibliothek.suchen(nummer) == null) {
            ausgabe.println("Kein Medium gefunden.");
            return;
        }
        bibliothek.entfernen(nummer);
        ausgabe.println("Medium wurde entfernt.");
    }

    private void ausgelieheneMedienAnzeigen() {
        Iterator<Medium> iterator = bibliothek.ausgelieheneMedienIterator();
        if (!iterator.hasNext()) {
            ausgabe.println("Keine Medien sind ausgeliehen.");
            return;
        }
        ausgabe.println("Ausgeliehene Medien:");
        while (iterator.hasNext()) {
            ausgabe.println("- " + iterator.next());
        }
    }

    private Medium mediumNachNummerLesen() {
        return bibliothek.suchen(textLesen("Inventarnummer: "));
    }

    private String textLesen(String aufforderung) {
        ausgabe.print(aufforderung);
        if (!scanner.hasNextLine()) {
            throw new IllegalStateException("Eingabe wurde beendet");
        }
        String wert = scanner.nextLine().trim();
        if (wert.isEmpty()) {
            throw new IllegalArgumentException("Eingabe darf nicht leer sein");
        }
        return wert;
    }

    private int intLesen(String aufforderung) {
        String wert = textLesen(aufforderung);
        try {
            return Integer.parseInt(wert);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Bitte eine ganze Zahl eingeben");
        }
    }

    private double doubleLesen(String aufforderung) {
        String wert = textLesen(aufforderung).replace(',', '.');
        try {
            return Double.parseDouble(wert);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Bitte eine gültige Zahl eingeben");
        }
    }
}
