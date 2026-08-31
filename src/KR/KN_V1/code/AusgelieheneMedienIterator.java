package KR.KN_V1.code;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/** Iterator, der nicht ausgeliehene Medien ueberspringt. */
public class AusgelieheneMedienIterator implements Iterator<Medium> {
    private final Iterator<Medium> basisIterator;
    private Medium naechstes;
    private boolean vorbereitet;

    public AusgelieheneMedienIterator(Iterator<Medium> basisIterator) {
        this.basisIterator = Objects.requireNonNull(
                basisIterator, "basisIterator darf nicht null sein");
    }

    @Override
    public boolean hasNext() {
        vorbereiten();
        return naechstes != null;
    }

    @Override
    public Medium next() {
        vorbereiten();
        if (naechstes == null) {
            throw new NoSuchElementException("keine weiteren ausgeliehenen Medien");
        }
        Medium ergebnis = naechstes;
        naechstes = null;
        vorbereitet = false;
        return ergebnis;
    }

    private void vorbereiten() {
        if (vorbereitet) {
            return;
        }
        naechstes = null;
        while (basisIterator.hasNext()) {
            Medium kandidat = basisIterator.next();
            if (kandidat.isAusgeliehen()) {
                naechstes = kandidat;
                break;
            }
        }
        vorbereitet = true;
    }
}
