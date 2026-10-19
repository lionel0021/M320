package KR.KN_D3_nico.code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EingabeValidator {
    public List<Stadt> validiereAuswahl(String eingabe) throws UngueltigeEingabeException {
        if (eingabe == null || eingabe.isBlank()) {
            throw new UngueltigeEingabeException("Die Eingabe darf nicht leer sein.");
        }
        String wert = eingabe.trim().toUpperCase();
        if (wert.equals("ALLE")) {
            return new ArrayList<>(Arrays.asList(Stadt.values()));
        }
        List<Stadt> auswahl = new ArrayList<>();
        switch (wert) {
            case "ZUERICH", "ZH" -> auswahl.add(Stadt.ZUERICH);
            case "BERN", "BE" -> auswahl.add(Stadt.BERN);
            case "GENF", "GE" -> auswahl.add(Stadt.GENF);
            default -> throw new UngueltigeEingabeException("Unbekannte Auswahl: " + eingabe);
        }
        return auswahl;
    }
}
