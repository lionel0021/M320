package KR.KN_M4.zork;

public final class QuitCommand implements Command {
    @Override public String execute(GameState state, EventBus events) { state.stop(); return "Das Abenteuer ruht. Auf Wiedersehen."; }
}
