package KR.KN_V2.code;

import java.util.List;

/**
 * Prueft die Fahrzeugmodelle und die Fuhrparkverwaltung mit ausfuehrbaren Selbsttests.
 *
 * <p>Die Tests decken Kostenberechnung, Liefereignung, doppelte Reservierungen,
 * Fahrzeugauswahl, Gesamtkosten und das Entfernen ab. Ein fehlgeschlagener Test
 * bricht die Ausfuehrung mit einem {@link AssertionError} ab.</p>
 */
public class FuhrparkTest {
    public static void main(String[] args) {
        testPolymorpheKosten();
        testKannLiefern();
        testReservierenException();
        testFindeGeeignete();
        testGesamtkosten();
        testRemoveById();
        System.out.println("KN-V2: Alle Tests erfolgreich.");
    }

    private static void testPolymorpheKosten() {
        Lieferfahrzeug transporter = new Transporter("T", "ZH-1", 1.0, 0, 1000, 500);
        Lieferfahrzeug elektro = new ElektroVan("E", "ZH-2", 1.0, 0, 100, 300);
        pruefe(Math.abs(transporter.berechneKosten(100) - 125.0) < 0.001, "Transporter-Kosten falsch");
        pruefe(Math.abs(elektro.berechneKosten(100) - 60.0) < 0.001, "ElektroVan-Kosten falsch");
    }

    private static void testKannLiefern() {
        Lieferfahrzeug ueberladen = new Transporter("T", "ZH-1", 1.0, 0, 1000, 1200);
        pruefe(!ueberladen.kannLiefern(50), "ueberladener Transporter darf nicht liefern");
        Lieferfahrzeug warm = new Kuehlfahrzeug("K", "ZH-2", 1.0, 0, 12.0, true);
        pruefe(!warm.kannLiefern(50), "zu warmes Kuehlfahrzeug darf nicht liefern");
        Lieferfahrzeug leer = new ElektroVan("E", "ZH-3", 1.0, 0, 10, 100);
        pruefe(!leer.kannLiefern(50), "ElektroVan mit zu wenig Reichweite darf nicht liefern");
        pruefe(leer.kannLiefern(5), "ElektroVan mit genug Reichweite muss liefern koennen");
    }

    private static void testReservierenException() {
        Lieferfahrzeug f = new Lastenrad("L", "ZH-9", 0.5, 0, 20);
        f.reservieren();
        try {
            f.reservieren();
            throw new AssertionError("zweites reservieren haette werfen muessen");
        } catch (IllegalStateException erwartet) {
        }
    }

    private static void testFindeGeeignete() {
        Fuhrpark fuhrpark = new Fuhrpark();
        fuhrpark.add(new ElektroVan("E1", "ZH-1", 1.0, 0, 100, 300));
        fuhrpark.add(new ElektroVan("E2", "ZH-2", 1.0, 0, 5, 100));
        List<Lieferfahrzeug> geeignet = fuhrpark.findeGeeigneteFahrzeuge(50);
        pruefe(geeignet.size() == 1, "nur ein Fahrzeug ist geeignet");
        pruefe(geeignet.get(0).getId().equals("E1"), "E1 muss geeignet sein");
    }

    private static void testGesamtkosten() {
        Fuhrpark fuhrpark = new Fuhrpark();
        fuhrpark.add(new Lastenrad("L1", "ZH-1", 1.0, 0, 100));
        fuhrpark.add(new Lastenrad("L2", "ZH-2", 2.0, 0, 100));
        pruefe(Math.abs(fuhrpark.berechneGesamtkosten(10) - 30.0) < 0.001, "Gesamtkosten falsch");
    }

    private static void testRemoveById() {
        Fuhrpark fuhrpark = new Fuhrpark();
        fuhrpark.add(new Lastenrad("L1", "ZH-1", 1.0, 0, 100));
        pruefe(fuhrpark.removeById("L1"), "remove muss true liefern");
        pruefe(!fuhrpark.removeById("L1"), "zweites remove muss false liefern");
    }

    private static void pruefe(boolean bedingung, String meldung) {
        if (!bedingung) {
            throw new AssertionError("FEHLGESCHLAGEN: " + meldung);
        }
    }
}
