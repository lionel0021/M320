package KR.N4_nico.D4.code;

public class InventarBefehl implements Befehl {
    public String schluesselwort() {
        return "inventar";
    }

    public String ausfuehren(Spielwelt welt, String argument) {
        Spieler spieler = welt.getSpieler();
        if (spieler.inventarLeer()) {
            return "Dein Inventar ist leer.";
        }
        return "Du traegst: " + String.join(", ", spieler.inventarNamen());
    }
}
