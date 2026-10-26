package KR.KN_M4.zork;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

/** Persistence adapter; the game model depends only on SaveRepository. */
public final class PropertiesSaveRepository implements SaveRepository {
    private final Path path;
    public PropertiesSaveRepository(Path path) { this.path = path; }
    @Override public void save(GameState state) throws IOException {
        Properties p = new Properties(); p.setProperty("room", state.player().location().name());
        p.setProperty("turn", Integer.toString(state.clock().turns()));
        p.setProperty("flags", String.join(",", state.flags()));
        p.setProperty("items", state.player().inventory().items().stream().map(Item::id).collect(Collectors.joining(",")));
        try (OutputStream out = Files.newOutputStream(path)) { p.store(out, "Arkenfall-Spielstand"); }
    }
    @Override public void load(GameState state) throws IOException {
        if (!Files.exists(path)) throw new IOException("Noch kein Spielstand vorhanden.");
        Properties p = new Properties();
        try (InputStream in = Files.newInputStream(path)) { p.load(in); }
        try {
            RoomId room = RoomId.valueOf(p.getProperty("room", "KAI"));
            Set<String> flags = split(p.getProperty("flags", ""));
            Set<String> items = split(p.getProperty("items", ""));
            state.restore(room, flags, items);
            state.restoreTurn(Integer.parseInt(p.getProperty("turn", "0")));
        } catch (IllegalArgumentException ex) { throw new IOException("Der Spielstand ist beschädigt.", ex); }
    }
    private Set<String> split(String text) {
        return Arrays.stream(text.split(",")).map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.toSet());
    }
}
