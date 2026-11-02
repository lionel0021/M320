package KR.N4_nico.D4.code;

public class Spiel {
    private final Konsole konsole;
    private final Spielwelt welt;

    public Spiel(Konsole konsole, Spielwelt welt) {
        this.konsole = konsole;
        this.welt = welt;
    }

    public void spielen() {
        konsole.ausgeben("=== Die Nebelhoehle ===");
        konsole.ausgeben("Finde den Schatz. Tippe 'hilfe' fuer Befehle, 'ende' zum Beenden.");
        konsole.ausgeben(welt.aktuellerRaumText());
        boolean laeuft = true;
        while (laeuft) {
            String eingabe = konsole.einlesen("\n> ");
            if (eingabe.trim().equalsIgnoreCase("ende")) {
                laeuft = false;
                continue;
            }
            try {
                konsole.ausgeben(welt.verarbeite(eingabe));
                if (welt.istGewonnen()) {
                    konsole.ausgeben("Du haeltst das Gold in den Haenden. Gewonnen!");
                    laeuft = false;
                }
            } catch (SpielException e) {
                konsole.fehler(e.getMessage());
            }
        }
        konsole.ausgeben("Spiel beendet.");
    }
}
