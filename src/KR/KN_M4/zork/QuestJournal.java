package KR.KN_M4.zork;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** Owns quest entries and derives readable progress from game state. */
public final class QuestJournal {
    private final List<Quest> quests = new ArrayList<>();
    public QuestJournal() {
        quests.add(new Quest("tower", "Das erloschene Leuchtfeuer", "Finde den Schlüssel und öffne den Leuchtturm.", "turm_offen"));
        quests.add(new Quest("crypt", "Licht in der Tiefe", "Bringe Licht in die Krypta.", "krypta_beleuchtet"));
        quests.add(new Quest("guardian", "Der steinerne Hüter", "Finde einen Weg, den Hüter zu beruhigen.", "hueter_passiert"));
        quests.add(new Quest("signal", "Das Signal", "Aktiviere das alte Leuchtfeuer.", "signal_aktiv"));
        quests.add(new Quest("escape", "Ein sicherer Hafen", "Öffne das Tor und entkomme dem Nebel.", "spiel_gewonnen"));
    }
    public void discover(String id) { find(id).ifPresent(Quest::discover); }
    public String describe(GameState state) {
        return quests.stream().filter(q -> q.status(state) != QuestStatus.UNBEKANNT)
                .map(q -> "[" + (q.status(state) == QuestStatus.ERLEDIGT ? "x" : " ") + "] " + q.title() + " — " + q.description())
                .reduce((a, b) -> a + "\n" + b).orElse("Dein Tagebuch ist noch leer.");
    }
    public void discoverFromLocation(RoomId room) {
        switch (room) {
            case KAI -> discover("tower");
            case KRYPTA -> { discover("crypt"); discover("guardian"); }
            case OBSERVATORIUM -> discover("signal");
            case AUSGANG -> discover("escape");
            default -> { }
        }
    }
    private Optional<Quest> find(String id) { return quests.stream().filter(q -> q.id().equals(id)).findFirst(); }
}
