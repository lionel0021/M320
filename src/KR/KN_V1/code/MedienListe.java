package KR.KN_V1.code;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Objects;

/** Eine eigene, voll veraenderbare Liste mit ArrayList als internem Speicher. */
public class MedienListe extends AbstractList<Medium> {
    private final ArrayList<Medium> daten = new ArrayList<>();

    @Override
    public Medium get(int index) {
        return daten.get(index);
    }

    @Override
    public int size() {
        return daten.size();
    }

    @Override
    public void add(int index, Medium medium) {
        daten.add(index, Objects.requireNonNull(medium, "medium darf nicht null sein"));
        modCount++;
    }

    @Override
    public Medium set(int index, Medium medium) {
        return daten.set(index, Objects.requireNonNull(medium, "medium darf nicht null sein"));
    }

    @Override
    public Medium remove(int index) {
        Medium entfernt = daten.remove(index);
        modCount++;
        return entfernt;
    }
}
