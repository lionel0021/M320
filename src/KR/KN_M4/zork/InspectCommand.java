package KR.KN_M4.zork;

public final class InspectCommand implements Command {
    private final String target;
    public InspectCommand(String target) { this.target = target; }
    @Override public String execute(GameState state, EventBus events) {
        if (target.isBlank()) return "Was möchtest du untersuchen?";
        if (target.contains("logbuch") && state.player().location() == RoomId.TURM_HALLE)
            return "Im Logbuch steht: ‚Drei Sterne weisen den Weg: Rabe, Walfisch, Krone.‘";
        if (target.contains("inschrift") && state.player().location() == RoomId.BIBLIOTHEK)
            return "Die Inschrift zeigt Rabe, Walfisch und Krone. Ordne die Sternscheiben in dieser Reihenfolge.";
        if (target.contains("sockel") && state.player().location() == RoomId.STEINKREIS)
            return "Der Sockel ist leer. Hier sollte etwas eingesetzt werden.";
        Room room = state.world().room(state.player().location());
        Item item = room.items().values().stream().filter(i -> i.name().toLowerCase().contains(target)).findFirst().orElse(null);
        if (item != null) return item.description();
        return "Du findest nichts Besonderes an „" + target + "“.";
    }
}
