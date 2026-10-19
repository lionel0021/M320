package KR.KN_D3_nico.code;

import java.util.List;

public class WetterLogik {
    private final EingabeValidator validator;
    private final WetterService service;

    public WetterLogik(EingabeValidator validator, WetterService service) {
        this.validator = validator;
        this.service = service;
    }

    public List<Wetter> abfragen(String eingabe) throws UngueltigeEingabeException, WetterException {
        List<Stadt> staedte = validator.validiereAuswahl(eingabe);
        return service.lade(staedte);
    }
}
