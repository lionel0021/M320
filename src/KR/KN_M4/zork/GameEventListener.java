package KR.KN_M4.zork;

/** Observer interface for narrative, quest and persistence events. */
public interface GameEventListener {
    void onEvent(GameEvent event);
}
