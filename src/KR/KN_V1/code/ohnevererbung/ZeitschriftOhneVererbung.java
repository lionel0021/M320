package KR.KN_V1.code.ohnevererbung;

/** Zwischenstand aus Aufgabe 1: dieselben Medienfelder wie beim Buch. */
public class ZeitschriftOhneVererbung {
    private final String titel;
    private final int erscheinungsjahr;
    private final String inventarnummer;
    private boolean ausgeliehen;
    private final int ausgabe;
    private final int monat;

    public ZeitschriftOhneVererbung(String titel, int erscheinungsjahr,
                                    String inventarnummer, int ausgabe, int monat) {
        this.titel = titel;
        this.erscheinungsjahr = erscheinungsjahr;
        this.inventarnummer = inventarnummer;
        this.ausgabe = ausgabe;
        this.monat = monat;
    }

    public String getTitel() { return titel; }
    public int getErscheinungsjahr() { return erscheinungsjahr; }
    public String getInventarnummer() { return inventarnummer; }
    public boolean isAusgeliehen() { return ausgeliehen; }
    public int getAusgabe() { return ausgabe; }
    public int getMonat() { return monat; }
    public void ausleihen() { ausgeliehen = true; }
    public void zurueckgeben() { ausgeliehen = false; }
}
