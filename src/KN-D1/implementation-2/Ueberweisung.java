import java.time.LocalDateTime;

public class Ueberweisung {
    private final String quellkonto;
    private final String zielkonto;
    private final long betragInRappen;
    private final LocalDateTime zeitpunkt;

    public Ueberweisung(String quellkonto, String zielkonto, long betragInRappen) {
        this.quellkonto = quellkonto;
        this.zielkonto = zielkonto;
        this.betragInRappen = betragInRappen;
        this.zeitpunkt = LocalDateTime.now();
    }

    public String beschreibung() {
        return String.format("%s: %s -> %s, CHF %.2f",
                zeitpunkt.withNano(0), quellkonto, zielkonto, betragInRappen / 100.0);
    }
}
