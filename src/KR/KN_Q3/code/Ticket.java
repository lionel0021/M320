package KR.KN_Q3.code;

import java.time.Duration;
import java.time.LocalDateTime;

public class Ticket implements Comparable<Ticket> {
    private static int naechsteId = 1;

    private final int id;
    private final String title;
    private final String description;
    private final LocalDateTime createdAt;
    private final int priority;
    private Status status;
    private String assignedTo;

    public Ticket(String title, String description, int priority) {
        this(title, description, priority, LocalDateTime.now());
    }

    public Ticket(String title, String description, int priority, LocalDateTime createdAt) {
        if (priority < 1 || priority > 5) {
            throw new IllegalArgumentException("Prioritaet muss zwischen 1 und 5 liegen.");
        }
        this.id = naechsteId++;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.createdAt = createdAt;
        this.status = Status.OPEN;
        this.assignedTo = null;
    }

    public void inBearbeitungNehmen() {
        if (status != Status.OPEN) {
            throw new IllegalStateException("Nur OPEN-Tickets koennen in Bearbeitung gehen (aktuell " + status + ").");
        }
        status = Status.IN_PROGRESS;
    }

    public void abschliessen() {
        if (status != Status.IN_PROGRESS) {
            throw new IllegalStateException("Nur IN_PROGRESS-Tickets koennen abgeschlossen werden (aktuell " + status + ").");
        }
        status = Status.DONE;
    }

    public void zuweisen(String person) {
        this.assignedTo = person;
    }

    public long minutenOffen(LocalDateTime jetzt) {
        return Duration.between(createdAt, jetzt).toMinutes();
    }

    public boolean slaVerletzt(int slaMinuten, LocalDateTime jetzt) {
        return status != Status.DONE && minutenOffen(jetzt) > slaMinuten;
    }

    @Override
    public int compareTo(Ticket andere) {
        int nachPrioritaet = Integer.compare(andere.priority, this.priority);
        if (nachPrioritaet != 0) {
            return nachPrioritaet;
        }
        return this.createdAt.compareTo(andere.createdAt);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getPriority() {
        return priority;
    }

    public Status getStatus() {
        return status;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    @Override
    public String toString() {
        String person = assignedTo == null ? "-" : assignedTo;
        return String.format("#%d [P%d] %s | %s | %s", id, priority, title, status, person);
    }
}
