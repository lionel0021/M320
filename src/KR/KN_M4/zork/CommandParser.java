package KR.KN_M4.zork;

import java.util.Locale;

/** Turns flexible German input into small command objects (Command pattern). */
public final class CommandParser {
    private final InputValidator validator = new InputValidator();
    private final SaveRepository saveRepository;
    public CommandParser(SaveRepository saveRepository) { this.saveRepository = saveRepository; }
    public Command parse(String input) {
        String line;
        try { line = validator.normalize(input); }
        catch (IllegalArgumentException exception) { return new InvalidCommand(exception.getMessage()); }
        String[] parts = line.split("\\s+", 2);
        String verb = parts[0]; String rest = parts.length > 1 ? parts[1] : "";
        return switch (verb) {
            case "schau", "schauen", "look" -> new LookCommand();
            case "geh", "gehe", "lauf", "reise", "go" -> new MoveCommand(rest);
            case "nimm", "nehme", "aufheben", "take" -> new TakeCommand(rest);
            case "inventar", "rucksack", "i" -> new InventoryCommand();
            case "benutze", "nutze", "use" -> new UseCommand(rest);
            case "rede", "sprich", "talk" -> new TalkCommand(rest);
            case "untersuche", "lies", "inspect" -> new InspectCommand(rest);
            case "hilfe", "?", "help" -> new HelpCommand();
            case "speichern", "save" -> new SaveCommand(saveRepository);
            case "laden", "load" -> new LoadCommand(saveRepository);
            case "karte", "map" -> new MapCommand();
            case "tagebuch", "aufgaben", "quests" -> new JournalCommand();
            case "erfolge", "erfolg" -> new AchievementsCommand();
            case "zeit", "wetter" -> new TimeCommand();
            case "verlauf", "history" -> new HistoryCommand();
            case "ende", "quit", "exit" -> new QuitCommand();
            default -> new UnknownCommand(line);
        };
    }
}
