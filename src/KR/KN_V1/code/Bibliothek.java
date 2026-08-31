package KR.KN_V1.code;

import java.util.Objects;

public class Bibliothek {
    private final MedienListe medien = new MedienListe();

    public void hinzufuegen(Medium medium) {
        Objects.requireNonNull(medium, "medium darf nicht null sein");
        if (suchen(medium.getInventarnummer()) != null) {
            throw new IllegalArgumentException(
                    "inventarnummer existiert bereits: " + medium.getInventarnummer());
        }
        medien.add(medium);
    }

    public Medium suchen(String inventarnummer) {
        Objects.requireNonNull(inventarnummer, "inventarnummer darf nicht null sein");
        for (Medium medium : medien) {
            if (medium.getInventarnummer().equals(inventarnummer)) {
                return medium;
            }
        }
        return null;
    }

    public void entfernen(String inventarnummer) {
        Medium medium = suchen(inventarnummer);
        if (medium != null) {
            medien.remove(medium);
        }
    }

    public int anzahlMedien() {
        return medien.size();
    }

    public MedienListe getMedien() {
        return medien;
    }

    public AusgelieheneMedienIterator ausgelieheneMedienIterator() {
        return new AusgelieheneMedienIterator(medien.iterator());
    }
}
