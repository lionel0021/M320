package KR.KN_M4.zork;

import java.io.IOException;

/** Signals a missing or invalid game save. */
public final class SpielstandException extends IOException {
    public SpielstandException(String message) {
        super(message);
    }

    public SpielstandException(String message, Throwable cause) {
        super(message, cause);
    }
}

