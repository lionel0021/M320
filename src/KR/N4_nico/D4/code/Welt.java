package KR.N4_nico.D4.code;

public class Welt {
    public static Spielwelt bauen() {
        Raum eingang = new Raum("Eingang", "Du stehst am dunklen Eingang einer Hoehle.");
        Raum halle = new Raum("Halle", "Eine weite Halle mit feuchten Waenden.");
        Raum verlies = new Raum("Verlies", "Ein kaltes Verlies, Ketten haengen an der Wand.");
        Raum kristall = new Raum("Kristallhoehle", "Funkelnde Kristalle ueberall.");
        Raum see = new Raum("Unterirdischer See", "Ein stiller schwarzer See.");
        Raum schatz = new Raum("Schatzkammer", "Ein Raum voller alter Truhen.");

        eingang.setAusgang("norden", halle);
        halle.setAusgang("sueden", eingang);
        halle.setAusgang("westen", verlies);
        halle.setAusgang("norden", kristall);
        halle.setAusgang("osten", schatz);
        verlies.setAusgang("osten", halle);
        kristall.setAusgang("sueden", halle);
        kristall.setAusgang("norden", see);
        see.setAusgang("sueden", kristall);
        schatz.setAusgang("westen", halle);

        halle.verschliesse("osten", "schluessel");

        eingang.addGegenstand(new Lampe());
        verlies.addGegenstand(new Gegenstand("schluessel", "Ein rostiger Eisenschluessel."));
        schatz.addGegenstand(new Gegenstand("gold", "Ein Haufen Goldmuenzen."));

        Spieler spieler = new Spieler(eingang);
        Spielwelt welt = new Spielwelt(spieler);
        welt.registriere(new GeheBefehl());
        welt.registriere(new SchauBefehl());
        welt.registriere(new NimmBefehl());
        welt.registriere(new InventarBefehl());
        welt.registriere(new BenutzeBefehl());
        welt.registriere(new HilfeBefehl());
        return welt;
    }
}
