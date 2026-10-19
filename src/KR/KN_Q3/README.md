# KN-Q3 – Mini Helpdesk Ticket System (Serie E)

Modul 320 · Java

Ein kleines Helpdesk-System für eine Schul-IT: Tickets kommen rein, werden nach
**FIFO** abgearbeitet, es gibt eine **Prioritätsansicht** (dringend zuerst),
**SLA-Überwachung** (Tickets müssen innerhalb X Minuten behandelt werden) und
einen **Tagesreport**.

Code unter [`code/`](code/).

## Ausführen

Vom Repository-Hauptordner aus (Java 17 oder neuer):

```powershell
javac -encoding UTF-8 -d out-q3 (Get-ChildItem src/KR/KN_Q3/code -Filter *.java).FullName
java -cp out-q3 KR.KN_Q3.code.HelpdeskTest
java -cp out-q3 KR.KN_Q3.code.Starter
```

`HelpdeskTest` prüft Comparable, Statusübergänge, FIFO, SLA und Report.
`Starter` startet das Konsolenmenü (mit Beispiel-Tickets).

## Die Klassen

| Klasse | Aufgabe |
|---|---|
| `Status` (enum) | `OPEN`, `IN_PROGRESS`, `DONE` |
| `Ticket` | `id` (auto-increment), `title`, `description`, `createdAt` (LocalDateTime), `priority` (1–5), `status`, `assignedTo` (optional). Implementiert `Comparable<Ticket>`. |
| `TicketQueue` | hält die Tickets: FIFO-Zugriff und Prioritätsansicht |
| `Helpdesk` | Verwaltung: erfassen, bearbeiten, abschliessen, zuweisen, SLA, Report |
| `Starter` | Konsolenmenü |

## Wichtige Punkte aus dem Auftrag

**Comparable (Priorität, dann createdAt)**
Priorität 1–5, **5 = am dringendsten**. Bei gleicher Priorität kommt das ältere
Ticket zuerst.
```java
@Override
public int compareTo(Ticket andere) {
    int nachPrioritaet = Integer.compare(andere.priority, this.priority);
    if (nachPrioritaet != 0) {
        return nachPrioritaet;
    }
    return this.createdAt.compareTo(andere.createdAt);
}
```

**FIFO-Queue plus Prioritätsansicht**
- FIFO: `naechstesOffenesFifo()` nimmt das zuerst erfasste offene Ticket.
- Priorität: `prioritaetsAnsicht()` gibt eine nach `compareTo` sortierte Kopie
  zurück (dringend zuerst). Die eigentliche Reihenfolge der Queue bleibt gleich.

**Statusübergänge (OPEN → IN_PROGRESS → DONE)**
Jeder Übergang wird geprüft; ein ungültiger Sprung wirft eine
`IllegalStateException`.
```java
public void inBearbeitungNehmen() {
    if (status != Status.OPEN) {
        throw new IllegalStateException("Nur OPEN-Tickets koennen in Bearbeitung gehen ...");
    }
    status = Status.IN_PROGRESS;
}
```

**SLA-Überwachung**
Ein Ticket verletzt das SLA, wenn es noch nicht `DONE` ist und länger als
`slaMinuten` offen ist.
```java
public boolean slaVerletzt(int slaMinuten, LocalDateTime jetzt) {
    return status != Status.DONE && minutenOffen(jetzt) > slaMinuten;
}
```

**Reporting**
`tagesReport()` zählt Tickets total, pro Status und die SLA-Verletzungen.

## Konsolenmenü

```text
1 = Ticket erfassen
2 = Alle Tickets anzeigen (FIFO)
3 = Prioritaetsansicht
4 = Naechstes Ticket bearbeiten (FIFO -> IN_PROGRESS)
5 = Ticket abschliessen (-> DONE)
6 = Ticket zuweisen
7 = SLA-Verletzungen anzeigen
8 = Tagesreport
9 = Beenden
```

## Dateien

```text
KN_Q3/
└── code/
    ├── Status.java
    ├── Ticket.java
    ├── TicketQueue.java
    ├── Helpdesk.java
    ├── Starter.java
    └── HelpdeskTest.java
```
