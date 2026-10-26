package KR.KN_M4.zork;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/** Keeps a short, newest-last record of actions for the player's journal. */
public final class CommandHistory {
    private static final int LIMIT = 12;
    private final Deque<String> entries = new ArrayDeque<>();

    public void record(String input) {
        if (input == null || input.isBlank()) return;
        entries.addLast(input.strip());
        while (entries.size() > LIMIT) entries.removeFirst();
    }

    public List<String> entries() { return List.copyOf(entries); }

    public String describe() {
        if (entries.isEmpty()) return "Du hast noch keine Befehle ausgeführt.";
        StringBuilder result = new StringBuilder("Letzte Aktionen:\n");
        int index = 1;
        for (String entry : entries) result.append(index++).append(". ").append(entry).append("\n");
        return result.toString().stripTrailing();
    }
}
