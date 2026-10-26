package KR.KN_M4.zork;

/** Character dialogue is polymorphic and independent of the command parser. */
public interface Character {
    String id();
    String speak(GameState state);
}
