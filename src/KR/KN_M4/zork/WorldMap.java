package KR.KN_M4.zork;

import java.util.EnumSet;
import java.util.Set;

/** Presents a compact map while respecting what the player has discovered. */
public final class WorldMap {
    private static final Set<RoomId> VILLAGE = EnumSet.of(RoomId.KAI, RoomId.MARKT, RoomId.TAVERNE);
    private static final Set<RoomId> FOREST = EnumSet.of(RoomId.WALDRAND, RoomId.WALD, RoomId.STEINKREIS);
    private static final Set<RoomId> TOWER = EnumSet.of(RoomId.TURM_EINGANG, RoomId.TURM_HALLE,
            RoomId.BIBLIOTHEK, RoomId.OBSERVATORIUM, RoomId.KRYPTA);
    public String describe(GameState state) {
        StringBuilder map = new StringBuilder("== Arkenfall ==\n");
        appendRegion(map, "Dorf", VILLAGE, state);
        appendRegion(map, "Wald", FOREST, state);
        appendRegion(map, "Leuchtturm", TOWER, state);
        if (state.visitedRooms().contains(RoomId.AUSGANG)) appendRoom(map, RoomId.AUSGANG, state);
        map.append("\nVerbindungen: Dorf ↔ Wald · Kai ↔ Turmeingang · Turm ↔ Krypta");
        return map.toString();
    }
    private void appendRegion(StringBuilder map, String title, Set<RoomId> rooms, GameState state) {
        map.append("\n").append(title).append(": ");
        rooms.forEach(room -> appendRoom(map, room, state));
    }
    private void appendRoom(StringBuilder map, RoomId id, GameState state) {
        if (state.visitedRooms().contains(id)) map.append("[").append(state.world().room(id).name()).append("] ");
        else map.append("[?] ");
    }
}
