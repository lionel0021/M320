package KR.KN_M4.zork;

public final class HistoryCommand implements Command {
    @Override public String execute(GameState state, EventBus events) {
        return state.history().describe();
    }
}
