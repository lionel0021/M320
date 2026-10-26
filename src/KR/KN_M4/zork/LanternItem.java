package KR.KN_M4.zork;

public final class LanternItem extends Item {
    public LanternItem() { super("laterne", "Sturmlaterne", "Eine alte Laterne; ihr Glas ist verrusst, aber intakt."); }
    @Override public String use(GameState state, String target) {
        if (target.contains("krypt") || target.contains("dunkel")) {
            state.setFlag("krypta_beleuchtet", true);
            return "Das Licht enthüllt eine Treppe in der Krypta.";
        }
        return "Die Laterne spendet warmes Licht. Hier gibt es nichts Neues zu sehen.";
    }
}
