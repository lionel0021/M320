package KR.KN_D3.code;

import java.util.List;

public class KryptoLogik {
    private final EingabeValidator validator;
    private final MarktdatenService service;

    public KryptoLogik(EingabeValidator validator, MarktdatenService service) {
        this.validator = validator;
        this.service = service;
    }

    public List<Marktdaten> abfragen(String eingabe)
            throws UngueltigeEingabeException, MarktdatenException {
        // Delegation: Der Validator prueft; erst danach wird der Service aufgerufen.
        List<Coin> coins = validator.validiereAuswahl(eingabe);
        return service.lade(coins);
    }
}
