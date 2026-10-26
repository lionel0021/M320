package KR.KN_M4.zork;

public final class LoadCommand implements Command {
    private final SaveRepository repository;
    public LoadCommand(SaveRepository repository) { this.repository = repository; }
    @Override public String execute(GameState state, EventBus events) {
        try { repository.load(state); events.publish("load", "Spielstand geladen."); return ""; }
        catch (java.io.IOException ex) { return "Laden fehlgeschlagen: " + ex.getMessage(); }
    }
}
