package KR.KN_M4.zork;

public final class SaveCommand implements Command {
    private final SaveRepository repository;
    public SaveCommand(SaveRepository repository) { this.repository = repository; }
    @Override public String execute(GameState state, EventBus events) {
        try { repository.save(state); return "Spielstand gespeichert."; }
        catch (java.io.IOException ex) { return "Speichern fehlgeschlagen: " + ex.getMessage(); }
    }
}
