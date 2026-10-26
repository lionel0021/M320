package KR.KN_M4.zork;

public final class UseCommand implements Command {
    private final String request;
    public UseCommand(String request) { this.request = request; }
    @Override public String execute(GameState state, EventBus events) {
        if (request.isBlank()) return "Was möchtest du benutzen? Beispiel: benutze Schlüssel an Turmtür";
        String[] parts = request.split("\\s+(an|auf|mit)\\s+", 2);
        String itemName = parts[0]; String target = parts.length > 1 ? parts[1] : "";
        Item item = state.player().inventory().find(itemName).orElse(null);
        if (item == null) return "Diesen Gegenstand trägst du nicht.";
        if (item instanceof PrismItem && state.player().location() == RoomId.OBSERVATORIUM && target.isBlank()) target = "globus";
        String result = item.use(state, target);
        events.publish("action", result);
        return "";
    }
}
