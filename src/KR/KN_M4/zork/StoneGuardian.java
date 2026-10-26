package KR.KN_M4.zork;

public final class StoneGuardian implements Character {
    @Override public String id() { return "hueter"; }
    @Override public String speak(GameState state) {
        state.journal().discover("guardian");
        if (state.hasFlag("hueterberuhigt")) {
            state.setFlag("hueter_passiert", true);
            return "Der Hüter neigt den Kopf und lässt dich passieren.";
        }
        return "Der steinerne Hüter knurrt: ‚Bring Frieden, Reisender, dann öffne ich das Tor.‘";
    }
}
