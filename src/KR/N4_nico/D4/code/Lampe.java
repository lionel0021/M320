package KR.N4_nico.D4.code;

public class Lampe extends Gegenstand implements Benutzbar {
    private boolean an;

    public Lampe() {
        super("lampe", "Eine alte Oellampe.");
        this.an = false;
    }

    public String benutze(Spieler spieler) {
        an = !an;
        return an ? "Die Lampe leuchtet, der Raum wird hell." : "Du loeschst die Lampe.";
    }

    public boolean istAn() {
        return an;
    }

    public String untersuchen() {
        return getBeschreibung() + (an ? " Sie brennt." : " Sie ist aus.");
    }
}
