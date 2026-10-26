package KR.KN_M4.zork;

public final class KeyItem extends Item {
    public KeyItem() { super("eisenschluessel", "Eisenschlüssel", "Ein schwerer Schlüssel mit dem Wappen des Leuchtturms."); }
    @Override public String use(GameState state, String target) {
        if (target.contains("turm") || target.contains("tuer") || target.contains("tür")) {
            state.setFlag("turm_offen", true);
            return "Der Schlüssel dreht sich knirschend. Die Turmtür springt auf.";
        }
        return "Der Schlüssel passt hier nicht.";
    }
}
