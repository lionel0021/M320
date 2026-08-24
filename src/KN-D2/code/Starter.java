package kn.d2;

// Kleine Demo, damit man die Objekte im Zusammenspiel sieht.
public class Starter {
    public static void main(String[] args) {
        Zeitplan zeitplan = new Zeitplan();

        Flug lx14 = new Flug("LX14", "London", 8, 30);
        Flug lx88 = new Flug("LX88", "New York", 13, 5);
        zeitplan.addFlug(lx14);   // Aggregation: Flug kommt von aussen dazu
        zeitplan.addFlug(lx88);

        Passagier anna = new Passagier("Anna Meier", "P123");
        Passagier ben = new Passagier("Ben Muster", "P456");

        lx14.addPassagier(anna);  // Aggregation: Passagier kommt von aussen dazu
        lx14.addPassagier(ben);
        lx88.addPassagier(anna);  // derselbe Passagier auf zwei Fluegen

        System.out.println("Alle Fluege:");
        for (Flug flug : zeitplan.fluegeAb(0, 0)) {
            System.out.println("  " + flug);
        }

        System.out.println("Passagiere auf LX14: " + zeitplan.passagiereVon("LX14"));
        System.out.println("Fluege ab 10:00 Uhr:");
        for (Flug flug : zeitplan.fluegeAb(10, 0)) {
            System.out.println("  " + flug);
        }

        lx14.removePassagier(ben);
        System.out.println("Nach Entfernen von Ben -> LX14 hat " + lx14.anzahlPassagiere() + " Passagiere");
        System.out.println("Ben lebt weiter (Aggregation): " + ben);
    }
}
