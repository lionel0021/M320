package KR.KN_M4.zork;

public final class LookCommand implements Command {
    @Override public String execute(GameState state, EventBus events) {
        Room room = state.world().room(state.player().location());
        String exits = room.exits().keySet().stream().map(d -> d.name().toLowerCase()).reduce((a,b) -> a + ", " + b).orElse("keine");
        String items = room.items().isEmpty() ? "" : " Hier liegt: " + String.join(", ", room.items().values().stream().map(Item::name).toList()) + ".";
        return "== " + room.name() + " ==\n" + room.description(state.clock().phase()) + items + "\nAusgänge: " + exits + ".";
    }
}
