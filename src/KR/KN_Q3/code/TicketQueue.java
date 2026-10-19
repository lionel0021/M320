package KR.KN_Q3.code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TicketQueue {
    private final LinkedList<Ticket> tickets = new LinkedList<>();

    public void add(Ticket ticket) {
        tickets.addLast(ticket);
    }

    public Ticket naechstesOffenesFifo() {
        for (Ticket t : tickets) {
            if (t.getStatus() == Status.OPEN) {
                return t;
            }
        }
        return null;
    }

    public List<Ticket> prioritaetsAnsicht() {
        List<Ticket> offen = new ArrayList<>();
        for (Ticket t : tickets) {
            if (t.getStatus() != Status.DONE) {
                offen.add(t);
            }
        }
        Collections.sort(offen);
        return offen;
    }

    public Ticket findeById(int id) {
        for (Ticket t : tickets) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    public List<Ticket> alle() {
        return new ArrayList<>(tickets);
    }

    public int size() {
        return tickets.size();
    }

    public boolean isEmpty() {
        return tickets.isEmpty();
    }
}
