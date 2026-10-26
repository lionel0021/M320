package KR.KN_M4.zork;

public final class Player {
    private RoomId location;
    private final Inventory inventory = new Inventory();
    public Player(RoomId start) { location = start; }
    public RoomId location() { return location; }
    public void moveTo(RoomId room) { location = room; }
    public Inventory inventory() { return inventory; }
}
