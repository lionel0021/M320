package KR.KN_D3_nico.code;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class LokalerWetterService implements WetterService {
    private final Map<Stadt, double[]> werte = new EnumMap<>(Stadt.class);
    private final Map<Stadt, String> zustaende = new EnumMap<>(Stadt.class);

    public LokalerWetterService() {
        erfasse(Stadt.ZUERICH, 18.5, 65, "leicht bewoelkt");
        erfasse(Stadt.BERN, 16.0, 70, "Regen");
        erfasse(Stadt.GENF, 20.2, 55, "sonnig");
    }

    private void erfasse(Stadt stadt, double temperatur, int luftfeuchte, String zustand) {
        werte.put(stadt, new double[]{temperatur, luftfeuchte});
        zustaende.put(stadt, zustand);
    }

    @Override
    public List<Wetter> lade(List<Stadt> staedte) throws WetterException {
        List<Wetter> ergebnis = new ArrayList<>();
        for (Stadt stadt : staedte) {
            if (!werte.containsKey(stadt)) {
                throw new WetterException("Keine Wetterdaten fuer " + stadt.getAnzeige());
            }
            double[] daten = werte.get(stadt);
            ergebnis.add(new Wetter(stadt, daten[0], zustaende.get(stadt), (int) daten[1], LocalDateTime.now()));
        }
        return ergebnis;
    }
}
