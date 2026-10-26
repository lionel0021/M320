package KR.KN_M4.zork;

public final class MoveCommand implements Command {
    private final String direction;
    public MoveCommand(String direction) { this.direction = direction; }
    @Override public String execute(GameState state, EventBus events) {
        Direction dir;
        try { dir = Direction.from(direction); } catch (IllegalArgumentException e) { return "Wohin möchtest du? (nord, ost, sued, west, oben, unten)"; }
        Room current = state.world().room(state.player().location());
        RoomId destination = current.exits().get(dir);
        if (destination == null) return "In diese Richtung führt hier kein Weg.";
        if (destination == RoomId.TURM_HALLE && !state.hasFlag("turm_offen")) return "Die Turmtür ist verschlossen. Vielleicht findest du einen passenden Schlüssel.";
        if (destination == RoomId.KRYPTA && !state.hasFlag("krypta_beleuchtet")) return "Die Treppe ist stockdunkel. Eine Lichtquelle könnte helfen.";
        if (destination == RoomId.AUSGANG && (!state.hasFlag("hueter_passiert") || !state.hasFlag("signal_aktiv")))
            return "Das Tor bleibt verschlossen. Der Hüter muss dich passieren lassen und das Leuchtfeuer muss wieder brennen.";
        state.player().moveTo(destination);
        state.markVisited(destination);
        state.journal().discoverFromLocation(destination);
        if (destination == RoomId.MARKT) state.setFlag("market_seen", true);
        if (destination == RoomId.AUSGANG) state.setFlag("spiel_gewonnen", true);
        events.publish("room", new LookCommand().execute(state, events));
        return "";
    }
}
