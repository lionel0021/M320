package KR.KN_M4.zork;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public final class Inventory {
    private final Map<String, Item> items = new LinkedHashMap<>();
    public void add(Item item) { items.put(item.id(), item); }
    public Optional<Item> find(String query) {
        String q = query.toLowerCase();
        return items.values().stream().filter(i -> i.id().contains(q) || i.name().toLowerCase().contains(q)).findFirst();
    }
    public boolean contains(String id) { return items.containsKey(id); }
    public Collection<Item> items() { return items.values(); }
    public String describe() {
        if (items.isEmpty()) return "Dein Rucksack ist leer.";
        return "Im Rucksack: " + String.join(", ", items.values().stream().map(Item::name).toList());
    }
}
