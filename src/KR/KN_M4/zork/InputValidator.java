package KR.KN_M4.zork;

import java.util.Locale;

/** Normalizes user input and rejects empty or excessively long commands. */
public final class InputValidator {
    private static final int MAX_LENGTH = 160;

    public String normalize(String input) {
        if (input == null) throw new UngueltigeEingabeException("Es wurde keine Eingabe übergeben.");
        String cleaned = input.strip().replaceAll("\\s+", " ");
        if (cleaned.isEmpty()) throw new UngueltigeEingabeException("Bitte gib einen Befehl ein.");
        if (cleaned.length() > MAX_LENGTH) throw new UngueltigeEingabeException("Der Befehl ist zu lang (maximal " + MAX_LENGTH + " Zeichen).");
        return cleaned.toLowerCase(Locale.ROOT);
    }
}
