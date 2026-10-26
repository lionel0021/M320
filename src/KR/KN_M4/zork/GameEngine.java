package KR.KN_M4.zork;

/** Coordinates parsing and command execution. */
public final class GameEngine {
    private final GameState state;
    private final EventBus events;
    private final CommandParser parser;
    public GameEngine(GameState state, EventBus events) {
        this.state = state;
        this.events = events;
        SaveRepository repository = new PropertiesSaveRepository(java.nio.file.Path.of("arkenfall.save"));
        this.parser = new CommandParser(repository);
    }
    public String execute(String input) {
        try {
            state.history().record(input);
            boolean weatherChanged = state.clock().advance();
            String result = parser.parse(input).execute(state, events);
            if (weatherChanged) events.publish("weather", state.clock().phase().description());
            for (Achievement achievement : state.achievements().update(state))
                events.publish("achievement", "Erfolg freigeschaltet: " + achievement.title());
            if (state.hasFlag("spiel_gewonnen")) events.publish("ending", "Die Laterne flammt auf. Der Nebel zerreisst, und Arkenfall ist gerettet!");
            return result;
        } catch (RuntimeException ex) {
            return "Der Befehl konnte nicht ausgeführt werden. Prüfe die Eingabe und versuche es erneut.";
        }
    }
    public GameState state() { return state; }
}
