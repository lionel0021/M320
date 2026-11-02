package KR.KN_M4.zork;

/** Signals user input that is empty, missing, or too long. */
public final class UngueltigeEingabeException extends IllegalArgumentException {
    public UngueltigeEingabeException(String message) {
        super(message);
    }
}
