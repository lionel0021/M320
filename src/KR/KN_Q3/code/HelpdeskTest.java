package KR.KN_Q3.code;

import java.time.LocalDateTime;
import java.util.List;

public class HelpdeskTest {
    public static void main(String[] args) {
        testComparablePrioritaetDannZeit();
        testStatusUebergaengeGueltig();
        testStatusUebergangUngueltig();
        testFifoReihenfolge();
        testSlaVerletzung();
        testReportZaehlt();
        System.out.println("KN-Q3: Alle Tests erfolgreich.");
    }

    private static void testComparablePrioritaetDannZeit() {
        LocalDateTime basis = LocalDateTime.of(2026, 1, 1, 8, 0);
        Ticket niedrig = new Ticket("a", "", 2, basis);
        Ticket hochFrueh = new Ticket("b", "", 5, basis);
        Ticket hochSpaet = new Ticket("c", "", 5, basis.plusMinutes(10));
        pruefe(hochFrueh.compareTo(niedrig) < 0, "hohe Prioritaet muss vor niedriger kommen");
        pruefe(hochFrueh.compareTo(hochSpaet) < 0, "bei gleicher Prioritaet muss aelteres zuerst kommen");
    }

    private static void testStatusUebergaengeGueltig() {
        Ticket t = new Ticket("x", "", 3);
        pruefe(t.getStatus() == Status.OPEN, "neu muss OPEN sein");
        t.inBearbeitungNehmen();
        pruefe(t.getStatus() == Status.IN_PROGRESS, "muss IN_PROGRESS sein");
        t.abschliessen();
        pruefe(t.getStatus() == Status.DONE, "muss DONE sein");
    }

    private static void testStatusUebergangUngueltig() {
        Ticket t = new Ticket("x", "", 3);
        try {
            t.abschliessen();
            throw new AssertionError("OPEN direkt auf DONE haette werfen muessen");
        } catch (IllegalStateException erwartet) {
        }
    }

    private static void testFifoReihenfolge() {
        Helpdesk helpdesk = new Helpdesk(60);
        helpdesk.erfasse("erst", "", 1);
        helpdesk.erfasse("zweit", "", 5);
        Ticket bearbeitet = helpdesk.bearbeiteNaechstes();
        pruefe(bearbeitet.getTitle().equals("erst"), "FIFO muss zuerst erfasstes Ticket nehmen");
    }

    private static void testSlaVerletzung() {
        LocalDateTime jetzt = LocalDateTime.of(2026, 1, 1, 12, 0);
        Helpdesk helpdesk = new Helpdesk(60);
        helpdesk.erfasse(new Ticket("alt", "", 3, jetzt.minusMinutes(90)));
        helpdesk.erfasse(new Ticket("neu", "", 3, jetzt.minusMinutes(10)));
        List<Ticket> verletzt = helpdesk.slaVerletzungen(jetzt);
        pruefe(verletzt.size() == 1, "genau ein Ticket verletzt das SLA");
        pruefe(verletzt.get(0).getTitle().equals("alt"), "das alte Ticket muss es sein");
    }

    private static void testReportZaehlt() {
        Helpdesk helpdesk = new Helpdesk(60);
        helpdesk.erfasse("a", "", 3);
        helpdesk.erfasse("b", "", 3);
        helpdesk.bearbeiteNaechstes();
        String report = helpdesk.tagesReport(LocalDateTime.now());
        pruefe(report.contains("Tickets total: 2"), "Report muss 2 Tickets zaehlen");
        pruefe(report.contains("IN_PROGRESS: 1"), "Report muss 1 in Bearbeitung zeigen");
        pruefe(report.contains("OPEN: 1"), "Report muss 1 offenes zeigen");
    }

    private static void pruefe(boolean bedingung, String meldung) {
        if (!bedingung) {
            throw new AssertionError("FEHLGESCHLAGEN: " + meldung);
        }
    }
}
