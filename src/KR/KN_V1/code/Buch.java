package KR.KN_V1.code;

import java.util.Objects;

public class Buch extends Medium {
    private final String autor;
    private final String isbn;

    public Buch(String titel, int erscheinungsjahr, String inventarnummer,
                String autor, String isbn) {
        super(titel, erscheinungsjahr, inventarnummer);
        this.autor = Objects.requireNonNull(autor, "autor darf nicht null sein");
        this.isbn = Objects.requireNonNull(isbn, "isbn darf nicht null sein");
    }

    public String getAutor() {
        return autor;
    }

    public String getIsbn() {
        return isbn;
    }
}
