package KR.KN_M4.zork;

/** Returns a specific validation message without attempting game logic. */
public final class InvalidCommand implements Command {
    private final String message;
    public InvalidCommand(String message) { this.message = message; }
    @Override public String execute(GameState state, EventBus events) { return message; }
}
