package KR.KN_M4.zork;

public final class MapCommand implements Command {
    private final WorldMap map = new WorldMap();
    @Override public String execute(GameState state, EventBus events) {
        return map.describe(state);
    }
}
