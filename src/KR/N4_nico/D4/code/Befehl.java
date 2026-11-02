package KR.N4_nico.D4.code;

public interface Befehl {
    String schluesselwort();

    String ausfuehren(Spielwelt welt, String argument) throws SpielException;
}
