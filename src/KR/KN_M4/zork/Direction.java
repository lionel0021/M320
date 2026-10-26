package KR.KN_M4.zork;

/** Cardinal and vertical exits used by the world map. */
public enum Direction {
    NORD, OST, SUED, WEST, OBEN, UNTEN;

    public static Direction from(String value) {
        String normalized = value.toLowerCase();
        return switch (normalized) {
            case "n", "nord", "north" -> NORD;
            case "o", "ost", "east", "e" -> OST;
            case "s", "sued", "süd", "south" -> SUED;
            case "w", "west" -> WEST;
            case "oben", "hoch", "up" -> OBEN;
            case "unten", "runter", "down" -> UNTEN;
            default -> throw new IllegalArgumentException("Unbekannte Richtung: " + value);
        };
    }
}
