package KR.KN_M4.zork;

public final class Mara implements Character {
    @Override public String id() { return "mara"; }
    @Override public String speak(GameState state) {
        state.journal().discover("tower");
        return "Mara flüstert: ‚Der Leuchtturm öffnet sich nur dem alten Eisen. Im Wald aber liegt ein Licht für die Toten.‘";
    }
}
