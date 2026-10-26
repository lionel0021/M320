package KR.N4_nico.M4.code;

public interface Ausleihbar {
    boolean istVerfuegbar();

    void ausleihen(String name);

    void zurueckgeben();
}
