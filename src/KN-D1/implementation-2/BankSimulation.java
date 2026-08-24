import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Scanner;

public class BankSimulation {
    private final Bank bank;
    private final Scanner scanner;

    public BankSimulation(Bank bank, Scanner scanner) {
        this.bank = Objects.requireNonNull(bank, "Die Bank darf nicht null sein.");
        this.scanner = Objects.requireNonNull(scanner, "Der Scanner darf nicht null sein.");
    }

    public static void main(String[] args) {
        Bank bank = new Bank("M320 Bank");
        Bankkonto privatkonto = bank.kontoEroeffnen("CH-2001", new Kunde(1, "Nico"));
        Bankkonto sparkonto = bank.kontoEroeffnen("CH-2002", new Kunde(2, "Lea"));
        bank.einzahlen(privatkonto.getKontonummer(), 150_000);
        bank.einzahlen(sparkonto.getKontonummer(), 80_000);

        new BankSimulation(bank, new Scanner(System.in)).starten();
    }

    public void starten() {
        boolean laeuft = true;
        System.out.println("Willkommen bei der " + bank.getName());

        while (laeuft) {
            menueAnzeigen();
            if (!scanner.hasNextLine()) {
                break;
            }
            String auswahl = scanner.nextLine().trim();
            try {
                switch (auswahl) {
                    case "1" -> kontenAnzeigen();
                    case "2" -> einzahlen();
                    case "3" -> abheben();
                    case "4" -> ueberweisen();
                    case "5" -> verlaufAnzeigen();
                    case "0" -> laeuft = false;
                    default -> System.out.println("Ungültige Auswahl.");
                }
            } catch (IllegalArgumentException | IllegalStateException | ArithmeticException exception) {
                System.out.println("Fehler: " + exception.getMessage());
            }
        }
        System.out.println("Auf Wiedersehen.");
    }

    private void menueAnzeigen() {
        System.out.println("\n1 Konten | 2 Einzahlen | 3 Abheben | 4 Überweisen | 5 Verlauf | 0 Ende");
        System.out.print("Auswahl: ");
    }

    private void kontenAnzeigen() {
        for (Bankkonto konto : bank.getKonten()) {
            System.out.println(konto.beschreibung());
        }
    }

    private void einzahlen() {
        String kontonummer = kontonummerLesen("Kontonummer: ");
        bank.einzahlen(kontonummer, betragLesen());
        System.out.println("Einzahlung ausgeführt.");
    }

    private void abheben() {
        String kontonummer = kontonummerLesen("Kontonummer: ");
        bank.abheben(kontonummer, betragLesen());
        System.out.println("Abhebung ausgeführt.");
    }

    private void ueberweisen() {
        String quelle = kontonummerLesen("Quellkonto: ");
        String ziel = kontonummerLesen("Zielkonto: ");
        bank.ueberweisen(quelle, ziel, betragLesen());
        System.out.println("Überweisung ausgeführt.");
    }

    private void verlaufAnzeigen() {
        if (bank.getUeberweisungen().isEmpty()) {
            System.out.println("Noch keine Überweisungen vorhanden.");
            return;
        }
        for (Ueberweisung ueberweisung : bank.getUeberweisungen()) {
            System.out.println(ueberweisung.beschreibung());
        }
    }

    private String kontonummerLesen(String aufforderung) {
        System.out.print(aufforderung);
        return scanner.nextLine().trim();
    }

    private long betragLesen() {
        System.out.print("Betrag in CHF: ");
        return betragInRappenUmwandeln(scanner.nextLine());
    }

    static long betragInRappenUmwandeln(String eingabe) {
        Objects.requireNonNull(eingabe, "Die Eingabe darf nicht null sein.");
        try {
            return new BigDecimal(eingabe.trim().replace(',', '.'))
                    .movePointRight(2)
                    .setScale(0, RoundingMode.UNNECESSARY)
                    .longValueExact();
        } catch (ArithmeticException | NumberFormatException exception) {
            throw new IllegalArgumentException("Bitte einen gültigen CHF-Betrag eingeben.");
        }
    }
}
