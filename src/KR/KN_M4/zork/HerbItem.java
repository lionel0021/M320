package KR.KN_M4.zork;

public final class HerbItem extends Item {
    public HerbItem() { super("mondkraut", "Mondkraut", "Silbrige Blätter, die im Dunkeln leuchten."); }
    @Override public String use(GameState state, String target) {
        if (target.contains("hueter") || target.contains("hüter") || target.contains("geist")) {
            state.setFlag("hueterberuhigt", true);
            return "Der Hüter atmet den Duft ein. Seine Stimme wird sanfter.";
        }
        return "Das Kraut duftet nach Regen und kaltem Stein.";
    }
}
