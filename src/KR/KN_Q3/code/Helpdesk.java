package KR.KN_Q3.code;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Helpdesk {
    private final TicketQueue queue = new TicketQueue();
    private final int slaMinuten;

    public Helpdesk(int slaMinuten) {
        this.slaMinuten = slaMinuten;
    }

    public Ticket erfasse(String title, String description, int priority) {
        Ticket ticket = new Ticket(title, description, priority);
        queue.add(ticket);
        return ticket;
    }

    public void erfasse(Ticket ticket) {
        queue.add(ticket);
    }

    public Ticket bearbeiteNaechstes() {
        Ticket ticket = queue.naechstesOffenesFifo();
        if (ticket == null) {
            return null;
        }
        ticket.inBearbeitungNehmen();
        return ticket;
    }

    public boolean schliesseAb(int id) {
        Ticket ticket = queue.findeById(id);
        if (ticket == null) {
            return false;
        }
        ticket.abschliessen();
        return true;
    }

    public boolean weiseZu(int id, String person) {
        Ticket ticket = queue.findeById(id);
        if (ticket == null) {
            return false;
        }
        ticket.zuweisen(person);
        return true;
    }

    public List<Ticket> prioritaetsAnsicht() {
        return queue.prioritaetsAnsicht();
    }

    public List<Ticket> alle() {
        return queue.alle();
    }

    public List<Ticket> slaVerletzungen(LocalDateTime jetzt) {
        List<Ticket> treffer = new ArrayList<>();
        for (Ticket t : queue.alle()) {
            if (t.slaVerletzt(slaMinuten, jetzt)) {
                treffer.add(t);
            }
        }
        return treffer;
    }

    public String tagesReport(LocalDateTime jetzt) {
        int offen = 0;
        int inArbeit = 0;
        int erledigt = 0;
        for (Ticket t : queue.alle()) {
            switch (t.getStatus()) {
                case OPEN -> offen++;
                case IN_PROGRESS -> inArbeit++;
                case DONE -> erledigt++;
            }
        }
        int verletzungen = slaVerletzungen(jetzt).size();
        StringBuilder sb = new StringBuilder();
        sb.append("=== Tagesreport ===\n");
        sb.append("Tickets total: ").append(queue.size()).append("\n");
        sb.append("OPEN: ").append(offen).append("\n");
        sb.append("IN_PROGRESS: ").append(inArbeit).append("\n");
        sb.append("DONE: ").append(erledigt).append("\n");
        sb.append("SLA-Verletzungen (> ").append(slaMinuten).append(" min): ").append(verletzungen);
        return sb.toString();
    }

    public int getSlaMinuten() {
        return slaMinuten;
    }
}
