package KR.KN_M4.zork;

public final class InventoryCommand implements Command {
    @Override public String execute(GameState state, EventBus events) { return state.player().inventory().describe(); }
}
