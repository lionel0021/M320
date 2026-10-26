package KR.KN_M4.zork;

public final class MarketTrader implements Character {
    @Override public String id() { return "haendlerin"; }
    @Override public String speak(GameState state) {
        state.journal().discover("tower");
        if (state.player().inventory().contains("eisenschluessel"))
            return "Die Händlerin nickt: ‚Du hast gefunden, was der Turm braucht.‘";
        return "Die Händlerin sagt: ‚Der Schlüssel lag einst im Brunnen. Der Nebel hat ihn auf den Markt gespült.‘";
    }
}
