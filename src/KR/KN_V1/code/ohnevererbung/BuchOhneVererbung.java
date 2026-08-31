package KR.KN_V1.code.ohnevererbung;

/** Zwischenstand aus Aufgabe 1: gemeinsamer Code ist hier noch dupliziert. */
public class BuchOhneVererbung {
    private final String titel;
    private final int erscheinungsjahr;
    private final String inventarnummer;
    private boolean ausgeliehen;
    private final String autor;
    private final String isbn;

    public BuchOhneVererbung(String titel, int erscheinungsjahr, String inventarnummer,
                             String autor, String isbn) {
        this.titel = titel;
        this.erscheinungsjahr = erscheinungsjahr;
        this.inventarnummer = inventarnummer;
        this.autor = autor;
        this.isbn = isbn;
    }

    public String getTitel() { return titel; }
    public int getErscheinungsjahr() { return erscheinungsjahr; }
    public String getInventarnummer() { return inventarnummer; }
    public boolean isAusgeliehen() { return ausgeliehen; }
    public String getAutor() { return autor; }
    public String getIsbn() { return isbn; }
    public void ausleihen() { ausgeliehen = true; }
    public void zurueckgeben() { ausgeliehen = false; }
}
