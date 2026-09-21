package KR.KN_D3.code;

import java.util.List;
import java.util.Locale;

public class EingabeValidator {
    public List<Coin> validiereAuswahl(String eingabe) throws UngueltigeEingabeException {
        if (eingabe == null || eingabe.isBlank()) {
            throw new UngueltigeEingabeException("Bitte BTC, SOL, ETH oder ALLE eingeben.");
        }
        String auswahl = eingabe.strip().toUpperCase(Locale.ROOT);
        if (auswahl.equals("ALLE")) {
            return List.of(Coin.values());
        }
        try {
            return List.of(Coin.valueOf(auswahl));
        } catch (IllegalArgumentException e) {
            throw new UngueltigeEingabeException("Unbekannter Coin. Erlaubt sind BTC, SOL, ETH oder ALLE.");
        }
    }
}
