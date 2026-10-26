package KR.KN_M4.zork;

import java.util.EnumMap;
import java.util.Map;

public final class World {
    private final Map<RoomId, Room> rooms = new EnumMap<>(RoomId.class);
    public void add(Room room) { rooms.put(room.id(), room); }
    public Room room(RoomId id) { return rooms.get(id); }
}
