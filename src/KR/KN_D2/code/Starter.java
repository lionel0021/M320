package KR.KN_D2.code;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

// Konsolenmenue fuer die Eingabe von Fluegen und Passagieren.
public class Starter {
    public static void main(String[] args) {
        Zeitplan zeitplan = new Zeitplan();
        // Passagiere bleiben auch nach dem Entfernen aus einem Flug erhalten.
        List<Passagier> passagiere = new ArrayList<>();

        try (Scanner scanner = new Scanner(System.in)) {
            boolean weiter = true;
            while (weiter) {
                System.out.println("\n1 Flug erfassen");
                System.out.println("2 Passagier einem Flug hinzufuegen");
                System.out.println("3 Passagier aus einem Flug entfernen");
                System.out.println("4 Alle Fluege anzeigen");
                System.out.println("5 Fluege ab Uhrzeit anzeigen");
                System.out.println("6 Passagiere eines Fluges anzeigen");
                System.out.println("0 Beenden");

                switch (liesZahl(scanner, "Auswahl: ", 0, 6)) {
                    case 1:
                        String nummer = liesText(scanner, "Flugnummer: ");
                        if (zeitplan.sucheFlug(nummer) != null) {
                            System.out.println("Flugnummer existiert bereits.");
                            break;
                        }
                        String ziel = liesText(scanner, "Ziel: ");
                        int stunde = liesZahl(scanner, "Abflugstunde (0-23): ", 0, 23);
                        int minute = liesZahl(scanner, "Abflugminute (0-59): ", 0, 59);
                        zeitplan.addFlug(new Flug(nummer, ziel, stunde, minute));
                        System.out.println("Flug erfasst.");
                        break;
                    case 2:
                        Flug hinflug = liesFlug(scanner, zeitplan);
                        if (hinflug == null) {
                            break;
                        }
                        String passNummer = liesText(scanner, "Passnummer: ");
                        Passagier passagier = null;
                        for (Passagier vorhanden : passagiere) {
                            if (vorhanden.getPassNummer().equals(passNummer)) {
                                passagier = vorhanden;
                                break;
                            }
                        }
                        if (passagier == null) {
                            String name = liesText(scanner, "Name: ");
                            passagier = new Passagier(name, passNummer);
                            passagiere.add(passagier);
                        }
                        if (hinflug.getPassagiere().contains(passagier)) {
                            System.out.println("Passagier ist bereits auf diesem Flug.");
                        } else {
                            hinflug.addPassagier(passagier);
                            System.out.println("Passagier hinzugefuegt: " + passagier);
                        }
                        break;
                    case 3:
                        Flug rueckflug = liesFlug(scanner, zeitplan);
                        if (rueckflug == null) {
                            break;
                        }
                        String entfernen = liesText(scanner, "Passnummer: ");
                        boolean entfernt = false;
                        for (Passagier vorhanden : rueckflug.getPassagiere()) {
                            if (vorhanden.getPassNummer().equals(entfernen)) {
                                rueckflug.removePassagier(vorhanden);
                                System.out.println("Aus Flug entfernt. Passagier bleibt erhalten: " + vorhanden);
                                entfernt = true;
                                break;
                            }
                        }
                        if (!entfernt) {
                            System.out.println("Passagier auf diesem Flug nicht gefunden.");
                        }
                        break;
                    case 4:
                        zeigeFluege(zeitplan.fluegeAb(0, 0));
                        break;
                    case 5:
                        int abStunde = liesZahl(scanner, "Ab Stunde (0-23): ", 0, 23);
                        int abMinute = liesZahl(scanner, "Ab Minute (0-59): ", 0, 59);
                        zeigeFluege(zeitplan.fluegeAb(abStunde, abMinute));
                        break;
                    case 6:
                        Flug flug = liesFlug(scanner, zeitplan);
                        if (flug != null) {
                            System.out.println("Anzahl Passagiere: " + zeitplan.passagiereVon(flug.getFlugNummer()));
                            for (Passagier person : flug.getPassagiere()) {
                                System.out.println("  " + person);
                            }
                        }
                        break;
                    case 0:
                        weiter = false;
                        break;
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("\nEingabe beendet.");
        }
        System.out.println("Programm beendet.");
    }

    private static String liesText(Scanner scanner, String aufforderung) {
        while (true) {
            System.out.print(aufforderung);
            String eingabe = scanner.nextLine().trim();
            if (!eingabe.isEmpty()) {
                return eingabe;
            }
            System.out.println("Eingabe darf nicht leer sein.");
        }
    }

    private static int liesZahl(Scanner scanner, String aufforderung, int min, int max) {
        while (true) {
            String eingabe = liesText(scanner, aufforderung);
            try {
                int zahl = Integer.parseInt(eingabe);
                if (zahl >= min && zahl <= max) {
                    return zahl;
                }
            } catch (NumberFormatException e) {
                // Bei ungueltigen Zahlen erneut fragen.
            }
            System.out.println("Bitte ganze Zahl von " + min + " bis " + max + " eingeben.");
        }
    }

    private static Flug liesFlug(Scanner scanner, Zeitplan zeitplan) {
        Flug flug = zeitplan.sucheFlug(liesText(scanner, "Flugnummer: "));
        if (flug == null) {
            System.out.println("Flug nicht gefunden.");
        }
        return flug;
    }

    private static void zeigeFluege(List<Flug> fluege) {
        if (fluege.isEmpty()) {
            System.out.println("Keine Fluege gefunden.");
        }
        for (Flug flug : fluege) {
            System.out.println("  " + flug);
        }
    }
}
