package KR.KN_V2.code;

import java.util.ArrayList;
import java.util.List;

/**
 * Verwaltet Lieferfahrzeuge mit eindeutigen IDs.
 *
 * <p>Unterstuetzt das Hinzufuegen, Suchen und Entfernen von Fahrzeugen sowie
 * die Auswahl geeigneter Fahrzeuge fuer eine Lieferstrecke. Eignung und Kosten
 * werden ueber die Methoden von {@link Lieferfahrzeug} bestimmt.</p>
 */
public class Fuhrpark {
    private final List<Lieferfahrzeug> fahrzeuge = new ArrayList<>();

    /**
     * Nimmt ein Fahrzeug auf. Eindeutige IDs verhindern mehrdeutige Such- und Loeschvorgaenge.
     *
     * @param fahrzeug aufzunehmendes Fahrzeug
     * @throws IllegalArgumentException wenn seine ID bereits im Fuhrpark vorhanden ist
     */
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

    /**
     * Summiert die Kosten, als wuerde jedes geeignete Fahrzeug die gesamte Strecke fahren.
     * Die Strecke wird dabei nicht auf mehrere Fahrzeuge verteilt.
     *
     * @param distanzKm Strecke je Fahrzeug in Kilometern
     * @return Summe in CHF; {@code 0.0}, wenn kein Fahrzeug geeignet ist
     */
    public double berechneGesamtkosten(double distanzKm) {
        double summe = 0.0;
        for (Lieferfahrzeug f : findeGeeigneteFahrzeuge(distanzKm)) {
            summe += f.berechneKosten(distanzKm);
        }
        return summe;
    }

    /**
     * Laesst jeden Fahrzeugtyp seine eigenen Lieferbedingungen pruefen.
     * Dadurch benoetigt der Fuhrpark keine Fallunterscheidung nach Fahrzeugtyp.
     *
     * @param distanzKm geplante Strecke in Kilometern
     * @return neue Liste geeigneter Fahrzeuge in der Reihenfolge ihrer Aufnahme
     */
    public List<Lieferfahrzeug> findeGeeigneteFahrzeuge(double distanzKm) {
        List<Lieferfahrzeug> treffer = new ArrayList<>();
        for (Lieferfahrzeug f : fahrzeuge) {
            if (f.kannLiefern(distanzKm)) {
                treffer.add(f);
            }
        }
        return treffer;
    }

    /**
     * Liefert eine Kopie der Liste, damit Aufrufer den Fahrzeugbestand nicht direkt aendern.
     * Die enthaltenen Fahrzeuge selbst werden nicht kopiert.
     *
     * @return neue Liste mit denselben Fahrzeugobjekten
     */
    public List<Lieferfahrzeug> alle() {
        return new ArrayList<>(fahrzeuge);
    }

    public int anzahl() {
        return fahrzeuge.size();
    }
}
