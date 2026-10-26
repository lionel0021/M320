package KR.KN_M4.zork;

public final class JournalCommand implements Command {
    @Override public String execute(GameState state, EventBus events) {
        return "== Tagebuch ==\n" + state.journal().describe(state);
    }
}
