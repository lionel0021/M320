package KR.KN_M4.zork;

import java.util.HashSet;
import java.util.Set;
import java.util.EnumSet;

/** Mutable quest state shared by commands and item strategies. */
public final class GameState {
    private final Player player = new Player(RoomId.KAI);
    private final World world = WorldFactory.create();
    private final Set<String> flags = new HashSet<>();
    private final QuestJournal journal = new QuestJournal();
    private final AchievementTracker achievements = new AchievementTracker();
    private final GameClock clock = new GameClock();
    private final CommandHistory history = new CommandHistory();
    private final Set<RoomId> visitedRooms = EnumSet.of(RoomId.KAI);
    private boolean running = true;
    public Player player() { return player; }
    public World world() { return world; }
    public QuestJournal journal() { return journal; }
    public AchievementTracker achievements() { return achievements; }
    public GameClock clock() { return clock; }
    public CommandHistory history() { return history; }
    public Set<RoomId> visitedRooms() { return Set.copyOf(visitedRooms); }
    public void markVisited(RoomId room) { visitedRooms.add(room); }
    public boolean running() { return running; }
    public void stop() { running = false; }
    public boolean hasFlag(String flag) { return flags.contains(flag); }
    public void setFlag(String flag, boolean value) { if (value) flags.add(flag); else flags.remove(flag); }
    public Set<String> flags() { return Set.copyOf(flags); }
    public void restore(RoomId room, Set<String> restoredFlags, Set<String> itemIds) {
        player.moveTo(room); visitedRooms.add(room); flags.clear(); flags.addAll(restoredFlags);
        for (String id : itemIds) {
            Item item = itemById(id);
            if (item != null) { for (RoomId roomId : RoomId.values()) world.room(roomId).remove(id); player.inventory().add(item); }
        }
    }
    public void restoreTurn(int turn) { clock.restore(turn); }
    private Item itemById(String id) {
        return switch (id) { case "eisenschluessel" -> new KeyItem(); case "laterne" -> new LanternItem();
            case "prisma" -> new PrismItem(); case "mondkraut" -> new HerbItem(); default -> null; };
    }
}
