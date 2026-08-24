package KR.KN_D1.garage;

import java.util.ArrayList;
import java.util.List;

/**
 * Die Garage verwaltet die zur Reparatur registrierten Fahrzeuge.
 * Sie erzeugt die Fahrzeug-Objekte und delegiert das Reparieren an das
 * jeweilige Fahrzeug (Objektkommunikation).
 */
public class Garage {
    private final List<Fahrzeug> fahrzeuge = new ArrayList<>();

    /** Registriert ein Fahrzeug zur Reparatur und gibt es zurueck. */
    public Fahrzeug registriere(String kennzeichen, long kostenInRappen) {
        if (suche(kennzeichen) != null) {
            throw new IllegalArgumentException("Kennzeichen bereits registriert: " + kennzeichen);
        }
        Fahrzeug fahrzeug = new Fahrzeug(kennzeichen, kostenInRappen);
        fahrzeuge.add(fahrzeug);
        return fahrzeug;
    }

    /** Markiert ein Fahrzeug als repariert. Delegiert an das Fahrzeug-Objekt. */
    public boolean markiereRepariert(String kennzeichen) {
        Fahrzeug fahrzeug = suche(kennzeichen);
        if (fahrzeug == null) {
            return false;
        }
        fahrzeug.markiereRepariert();
        return true;
    }

    public Fahrzeug suche(String kennzeichen) {
        for (Fahrzeug fahrzeug : fahrzeuge) {
            if (fahrzeug.getKennzeichen().equals(kennzeichen)) {
                return fahrzeug;
            }
        }
        return null;
    }

    public List<Fahrzeug> reparierteFahrzeuge() {
        List<Fahrzeug> treffer = new ArrayList<>();
        for (Fahrzeug fahrzeug : fahrzeuge) {
            if (fahrzeug.istRepariert()) {
                treffer.add(fahrzeug);
            }
        }
        return treffer;
    }

    public List<Fahrzeug> offeneFahrzeuge() {
        List<Fahrzeug> treffer = new ArrayList<>();
        for (Fahrzeug fahrzeug : fahrzeuge) {
            if (!fahrzeug.istRepariert()) {
                treffer.add(fahrzeug);
            }
        }
        return treffer;
    }

    /** Summe aller Reparaturkosten (in Rappen). */
    public long gesamtkostenInRappen() {
        long summe = 0;
        for (Fahrzeug fahrzeug : fahrzeuge) {
            summe += fahrzeug.getKostenInRappen();
        }
        return summe;
    }

    public int anzahl() {
        return fahrzeuge.size();
    }
}
