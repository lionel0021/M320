package KR.KN_D3_nico.code;

import java.util.List;

public class WetterApp {
    private final Konsole konsole = new Konsole();
    private final WetterLogik logik = new WetterLogik(new EingabeValidator(), new LokalerWetterService());

    public void start() {
        konsole.zeigeText("=== Wetter-Abfrage ===");
        boolean laeuft = true;
        while (laeuft) {
            konsole.zeigeMenue();
            String eingabe = konsole.liesEingabe();
            if (eingabe.trim().equals("0")) {
                laeuft = false;
                continue;
            }
            try {
                List<Wetter> daten = logik.abfragen(eingabe);
                konsole.zeige(daten);
            } catch (UngueltigeEingabeException | WetterException e) {
                konsole.zeigeFehler(e.getMessage());
            }
        }
        konsole.zeigeText("Programm beendet.");
    }
}
