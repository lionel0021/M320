package KR.KN_M4.zork;

public final class TakeCommand implements Command {
    private final String item;
    public TakeCommand(String item) { this.item = item; }
    @Override public String execute(GameState state, EventBus events) {
        if (item.isBlank()) return "Was möchtest du nehmen?";
        Item found = state.world().room(state.player().location()).remove(item);
        if (found == null) return "Das finde ich hier nicht.";
        state.player().inventory().add(found);
        events.publish("item", "Du nimmst: " + found.name() + ".");
        return "";
    }
}
