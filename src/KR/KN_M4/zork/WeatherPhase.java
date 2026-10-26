package KR.KN_M4.zork;

/** The sea fog changes character as the adventure advances. */
public enum WeatherPhase {
    STILL, RISING, THICK, BREAKING;

    public String description() {
        return switch (this) {
            case STILL -> "Der Nebel liegt ruhig über dem Wasser.";
            case RISING -> "Der Wind frischt auf; Nebelschwaden ziehen durch die Gassen.";
            case THICK -> "Der Nebel ist dicht. Das Leuchtfeuer wird dringend gebraucht.";
            case BREAKING -> "Ein heller Streifen zeigt sich am Horizont.";
        };
    }
}
