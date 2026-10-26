package KR.KN_M4.zork;

public final class TalkCommand implements Command {
    private final String person;
    private final DialogueService dialogue = new DialogueService();
    public TalkCommand(String person) { this.person = person; }
    @Override public String execute(GameState state, EventBus events) {
        return dialogue.talk(state, person);
    }
}
