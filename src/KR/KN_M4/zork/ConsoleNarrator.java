package KR.KN_M4.zork;

/** Console observer that displays events produced by the game model. */
public final class ConsoleNarrator implements GameEventListener {
    @Override
    public void onEvent(GameEvent event) {
        System.out.println("\n" + event.message());
    }
}
