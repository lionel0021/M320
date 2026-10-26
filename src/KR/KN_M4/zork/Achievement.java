package KR.KN_M4.zork;

/** An optional reward unlocked by completing a story milestone. */
public final class Achievement {
    private final String id;
    private final String title;
    private final String description;
    private final String requiredFlag;
    private boolean unlocked;
    public Achievement(String id, String title, String description, String requiredFlag) {
        this.id = id; this.title = title; this.description = description; this.requiredFlag = requiredFlag;
    }
    public String id() { return id; }
    public String title() { return title; }
    public String description() { return description; }
    public boolean unlocked() { return unlocked; }
    public boolean update(GameState state) {
        if (!unlocked && state.hasFlag(requiredFlag)) { unlocked = true; return true; }
        return false;
    }
}
