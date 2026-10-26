package KR.KN_M4.zork;

/** A journal entry whose completion is derived from named world flags. */
public final class Quest {
    private final String id;
    private final String title;
    private final String description;
    private final String completionFlag;
    private boolean discovered;

    public Quest(String id, String title, String description, String completionFlag) {
        this.id = id; this.title = title; this.description = description; this.completionFlag = completionFlag;
    }
    public String id() { return id; }
    public String title() { return title; }
    public String description() { return description; }
    public void discover() { discovered = true; }
    public QuestStatus status(GameState state) {
        if (state.hasFlag(completionFlag)) return QuestStatus.ERLEDIGT;
        return discovered ? QuestStatus.OFFEN : QuestStatus.UNBEKANNT;
    }
}
