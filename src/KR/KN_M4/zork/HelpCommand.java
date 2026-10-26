package KR.KN_M4.zork;

public final class HelpCommand implements Command {
    @Override public String execute(GameState state, EventBus events) {
        return "Befehle: schau | geh <Richtung> | nimm <Ding> | benutze <Ding> an <Ziel> | " +
                "untersuche <Ding> | rede | inventar | tagebuch | erfolge | karte | zeit | verlauf | speichern | laden | ende.\n" +
                "Tipp: Lies Hinweise genau und probiere Gegenstände an passenden Orten aus.";
    }
}
