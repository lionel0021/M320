package KR.N4_nico.D4.code;

import java.util.List;

public class Spieler {
    private Raum raum;
    private final Inventar<Gegenstand> inventar = new Inventar<>();

    public Spieler(Raum start) {
        this.raum = start;
    }

    public Raum getRaum() {
        return raum;
    }

    public void setRaum(Raum raum) {
        this.raum = raum;
    }

    public void nimm(Gegenstand g) {
        inventar.hinzufuegen(g);
    }

    public Gegenstand ausInventar(String name) {
        return inventar.finde(name);
    }

    public boolean hat(String name) {
        return inventar.enthaelt(name);
    }

    public List<String> inventarNamen() {
        return inventar.namen();
    }

    public boolean inventarLeer() {
        return inventar.istLeer();
    }
}
