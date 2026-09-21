package KR.KN_D3.code;

import java.util.Scanner;

public class KryptoApp {
    private final Konsole konsole;
    private final KryptoLogik logik;

    public KryptoApp() {
        this(new Konsole(new Scanner(System.in), System.out),
                new KryptoLogik(new EingabeValidator(), new KrakenService()));
    }

    public KryptoApp(Konsole konsole, KryptoLogik logik) {
        this.konsole = konsole;
        this.logik = logik;
    }

    public void start() {
        konsole.begruessen();
        while (!Thread.currentThread().isInterrupted()) {
            String eingabe = konsole.liesAuswahl();
            if (eingabe == null || eingabe.strip().equals("0")) {
                break;
            }
            try {
                konsole.zeige(logik.abfragen(eingabe));
            } catch (UngueltigeEingabeException | MarktdatenException e) {
                konsole.fehler(e.getMessage());
            }
        }
        konsole.verabschieden();
    }
}
