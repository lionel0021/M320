package KR.KN_M4.zork;

/** Counts meaningful player actions and cycles the story's weather mood. */
public final class GameClock {
    private int turns;

    public int turns() { return turns; }
    public void restore(int turn) { turns = Math.max(0, turn); }
    public WeatherPhase phase() {
        return switch ((turns / 5) % 4) {
            case 0 -> WeatherPhase.STILL;
            case 1 -> WeatherPhase.RISING;
            case 2 -> WeatherPhase.THICK;
            default -> WeatherPhase.BREAKING;
        };
    }
    public boolean advance() {
        WeatherPhase before = phase();
        turns++;
        return before != phase();
    }
    public String describe() {
        return "Aktion " + turns + " — " + phase().description();
    }
}
