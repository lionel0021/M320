package KR.KN_M4.zork;

import java.util.ArrayList;
import java.util.List;

/** Notifies listeners without coupling game rules to the console. */
public final class EventBus {
    private final List<GameEventListener> listeners = new ArrayList<>();

    public void subscribe(GameEventListener listener) {
        if (!listeners.contains(listener)) listeners.add(listener);
    }

    public void publish(String type, String message) {
        GameEvent event = new GameEvent(type, message);
        for (GameEventListener listener : List.copyOf(listeners)) listener.onEvent(event);
    }
}
