package KR.KN_M4.zork;

/** Shared item behavior. Concrete items specialize their use rules. */
public abstract class Item {
    private final String id;
    private final String name;
    private final String description;

    protected Item(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String id() { return id; }
    public String name() { return name; }
    public String description() { return description; }
    public abstract String use(GameState state, String target);
}
