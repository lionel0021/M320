package KR.KN_M4.zork;

import java.util.LinkedHashMap;
import java.util.Map;

public final class Room {
    private final RoomId id;
    private final String name;
    private final String description;
    private final Map<Direction, RoomId> exits = new LinkedHashMap<>();
    private final Map<String, Item> items = new LinkedHashMap<>();

    public Room(RoomId id, String name, String description) {
        this.id = id; this.name = name; this.description = description;
    }
    public RoomId id() { return id; }
    public String name() { return name; }
    public String description() { return description; }
    public String description(WeatherPhase weather) {
        String detail = switch (id) {
            case KAI -> " " + weather.description();
            case WALD, WALDRAND -> weather == WeatherPhase.THICK
                    ? " Der dichte Nebel verschluckt die Baumstämme."
                    : " Zwischen den Ästen bewegt sich der Wind.";
            case STEINKREIS -> weather == WeatherPhase.BREAKING
                    ? " Ein Lichtstrahl fällt auf die Runen."
                    : " Die Runen schimmern matt im Dunst.";
            case OBSERVATORIUM -> weather == WeatherPhase.BREAKING
                    ? " Die Wolkendecke reisst für einen Moment auf."
                    : " Wolken verdecken den Himmel über dem Globus.";
            default -> "";
        };
        return description + detail;
    }
    public Map<Direction, RoomId> exits() { return Map.copyOf(exits); }
    public Map<String, Item> items() { return items; }
    public void connect(Direction direction, RoomId destination) { exits.put(direction, destination); }
    public void place(Item item) { items.put(item.id(), item); }
    public Item remove(String query) {
        String q = query.toLowerCase();
        String key = items.entrySet().stream().filter(e -> e.getKey().contains(q) || e.getValue().name().toLowerCase().contains(q))
                .map(Map.Entry::getKey).findFirst().orElse(null);
        return key == null ? null : items.remove(key);
    }
}
