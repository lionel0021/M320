package KR.KN_D3.code;

public class MarktdatenException extends Exception {
    public MarktdatenException(String meldung) {
        super(meldung);
    }

    public MarktdatenException(String meldung, Throwable ursache) {
        super(meldung, ursache);
    }
}
