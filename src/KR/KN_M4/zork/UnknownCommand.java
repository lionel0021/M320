package KR.KN_M4.zork;

public final class UnknownCommand implements Command {
    private final String input;
    public UnknownCommand(String input) { this.input = input; }
    @Override public String execute(GameState state, EventBus events) {
        return input.isBlank() ? "Gib einen Befehl ein. Mit ‚hilfe‘ siehst du die Möglichkeiten." : "Diesen Befehl kenne ich nicht. Tippe ‚hilfe‘.";
    }
}
