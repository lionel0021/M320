package KR.KN_D3_nico.code;

import java.util.List;
import java.util.Scanner;

public class Konsole {
    private final Scanner scanner = new Scanner(System.in);

    public void zeigeMenue() {
        System.out.println("Staedte: ZUERICH, BERN, GENF | ALLE | 0 = Beenden");
        System.out.print("Auswahl: ");
    }

    public String liesEingabe() {
        return scanner.nextLine();
    }

    public void zeige(List<Wetter> daten) {
        for (Wetter wetter : daten) {
            System.out.println("  " + wetter);
        }
    }

    public void zeigeFehler(String meldung) {
        System.out.println("Fehler: " + meldung);
    }

    public void zeigeText(String text) {
        System.out.println(text);
    }
}
