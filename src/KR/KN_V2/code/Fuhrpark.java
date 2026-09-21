package KR.KN_V2.code;

import java.util.ArrayList;
import java.util.List;

public class Fuhrpark {
    private final List<Lieferfahrzeug> fahrzeuge = new ArrayList<>();

    public void add(Lieferfahrzeug fahrzeug) {
        if (findById(fahrzeug.getId()) != null) {
            throw new IllegalArgumentException("Id bereits vorhanden: " + fahrzeug.getId());
        }
        fahrzeuge.add(fahrzeug);
    }

    public boolean removeById(String id) {
        return fahrzeuge.removeIf(f -> f.getId().equals(id));
    }

    public Lieferfahrzeug findById(String id) {
        for (Lieferfahrzeug f : fahrzeuge) {
            if (f.getId().equals(id)) {
                return f;
            }
        }
        return null;
    }

    public void printAlle() {
        if (fahrzeuge.isEmpty()) {
            System.out.println("(keine Fahrzeuge)");
            return;
        }
        for (Lieferfahrzeug f : fahrzeuge) {
            System.out.println(f.printOut());
        }
    }

    public double berechneGesamtkosten(double distanzKm) {
        double summe = 0.0;
        for (Lieferfahrzeug f : findeGeeigneteFahrzeuge(distanzKm)) {
            summe += f.berechneKosten(distanzKm);
        }
        return summe;
    }

    public List<Lieferfahrzeug> findeGeeigneteFahrzeuge(double distanzKm) {
        List<Lieferfahrzeug> treffer = new ArrayList<>();
        for (Lieferfahrzeug f : fahrzeuge) {
            if (f.kannLiefern(distanzKm)) {
                treffer.add(f);
            }
        }
        return treffer;
    }

    public List<Lieferfahrzeug> alle() {
        return new ArrayList<>(fahrzeuge);
    }

    public int anzahl() {
        return fahrzeuge.size();
    }
}
