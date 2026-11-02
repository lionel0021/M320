package KR.N4_nico.D4.code;

public class UnbekannterBefehlException extends SpielException {
    public UnbekannterBefehlException(String wort) {
        super("Das verstehe ich nicht: " + wort);
    }
}
