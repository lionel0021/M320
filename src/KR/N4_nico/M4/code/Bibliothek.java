package KR.N4_nico.M4.code;

import java.util.ArrayList;
import java.util.List;

public class Bibliothek {
    private final List<Medium> medien = new ArrayList<>();

    public void aufnehmen(Medium medium) {
        medien.add(medium);
    }

    public List<Medium> alle() {
        return new ArrayList<>(medien);
    }

    public List<Medium> verfuegbare() {
        List<Medium> frei = new ArrayList<>();
        for (Medium m : medien) {
            if (m.istVerfuegbar()) {
                frei.add(m);
            }
        }
        return frei;
    }

    public int anzahl() {
        return medien.size();
    }
}
