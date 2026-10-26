package KR.KN_M4.zork;

public final class TimeCommand implements Command {
    @Override public String execute(GameState state, EventBus events) {
        return state.clock().describe();
    }
}
