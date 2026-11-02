package KR.N4_nico.D4.code;

import java.util.Scanner;

public class Konsole {
    private final Scanner scanner = new Scanner(System.in);

    public String einlesen(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public void ausgeben(String text) {
        System.out.println(text);
    }

    public void fehler(String meldung) {
        System.out.println("[!] " + meldung);
    }
}
