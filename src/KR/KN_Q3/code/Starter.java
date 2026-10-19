package KR.KN_Q3.code;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Starter {
    private final Scanner scanner = new Scanner(System.in);
    private final Helpdesk helpdesk = new Helpdesk(60);

    public static void main(String[] args) {
        new Starter().start();
    }

    private void start() {
        beispieldaten();
        System.out.println("=== Helpdesk Ticket System (SLA " + helpdesk.getSlaMinuten() + " min) ===");
        boolean laeuft = true;
        while (laeuft) {
            zeigeMenue();
            switch (scanner.nextLine().trim()) {
                case "1" -> ticketErfassen();
                case "2" -> anzeigen(helpdesk.alle(), "Alle Tickets (FIFO)");
                case "3" -> anzeigen(helpdesk.prioritaetsAnsicht(), "Prioritaetsansicht (dringend zuerst)");
                case "4" -> naechstesBearbeiten();
                case "5" -> ticketAbschliessen();
                case "6" -> ticketZuweisen();
                case "7" -> slaAnzeigen();
                case "8" -> System.out.println(helpdesk.tagesReport(LocalDateTime.now()));
                case "9" -> laeuft = false;
                default -> System.out.println("Ungueltige Auswahl.");
            }
            System.out.println();
        }
        System.out.println("Programm beendet.");
    }

    private void zeigeMenue() {
        System.out.println("1 = Ticket erfassen");
        System.out.println("2 = Alle Tickets anzeigen (FIFO)");
        System.out.println("3 = Prioritaetsansicht");
        System.out.println("4 = Naechstes Ticket bearbeiten (FIFO -> IN_PROGRESS)");
        System.out.println("5 = Ticket abschliessen (-> DONE)");
        System.out.println("6 = Ticket zuweisen");
        System.out.println("7 = SLA-Verletzungen anzeigen");
        System.out.println("8 = Tagesreport");
        System.out.println("9 = Beenden");
        System.out.print("Auswahl: ");
    }

    private void beispieldaten() {
        helpdesk.erfasse(new Ticket("Drucker offline", "Drucker im 2. Stock reagiert nicht", 2,
                LocalDateTime.now().minusMinutes(90)));
        helpdesk.erfasse(new Ticket("Server down", "Mailserver nicht erreichbar", 5,
                LocalDateTime.now().minusMinutes(120)));
        helpdesk.erfasse(new Ticket("Passwort vergessen", "Login fuer Schueler zuruecksetzen", 3,
                LocalDateTime.now().minusMinutes(20)));
        helpdesk.erfasse(new Ticket("Beamer flackert", "Zimmer 3.14 Beamer", 1,
                LocalDateTime.now().minusMinutes(5)));
    }

    private void ticketErfassen() {
        String title = frageText("Titel: ");
        String beschreibung = frageText("Beschreibung: ");
        int prio = (int) frageZahl("Prioritaet (1-5): ");
        try {
            Ticket t = helpdesk.erfasse(title, beschreibung, prio);
            System.out.println("Erfasst: " + t);
        } catch (IllegalArgumentException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }

    private void naechstesBearbeiten() {
        Ticket t = helpdesk.bearbeiteNaechstes();
        System.out.println(t == null ? "Kein offenes Ticket." : "In Bearbeitung: " + t);
    }

    private void ticketAbschliessen() {
        int id = (int) frageZahl("Ticket-Id: ");
        try {
            System.out.println(helpdesk.schliesseAb(id) ? "Abgeschlossen." : "Ticket nicht gefunden.");
        } catch (IllegalStateException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }

    private void ticketZuweisen() {
        int id = (int) frageZahl("Ticket-Id: ");
        String person = frageText("Zuweisen an: ");
        System.out.println(helpdesk.weiseZu(id, person) ? "Zugewiesen." : "Ticket nicht gefunden.");
    }

    private void slaAnzeigen() {
        List<Ticket> verletzt = helpdesk.slaVerletzungen(LocalDateTime.now());
        if (verletzt.isEmpty()) {
            System.out.println("Keine SLA-Verletzungen.");
            return;
        }
        System.out.println("SLA-Verletzungen (aelter als " + helpdesk.getSlaMinuten() + " min, nicht DONE):");
        for (Ticket t : verletzt) {
            System.out.println("  " + t + " | offen seit " + t.minutenOffen(LocalDateTime.now()) + " min");
        }
    }

    private void anzeigen(List<Ticket> liste, String titel) {
        System.out.println(titel + ":");
        if (liste.isEmpty()) {
            System.out.println("  (keine)");
            return;
        }
        for (Ticket t : liste) {
            System.out.println("  " + t);
        }
    }

    private String frageText(String frage) {
        System.out.print(frage);
        return scanner.nextLine().trim();
    }

    private double frageZahl(String frage) {
        while (true) {
            System.out.print(frage);
            try {
                return Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Bitte eine Zahl eingeben.");
            }
        }
    }
}
