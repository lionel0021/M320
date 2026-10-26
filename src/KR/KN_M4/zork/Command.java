package KR.KN_M4.zork;

public interface Command {
    String execute(GameState state, EventBus events);
}
