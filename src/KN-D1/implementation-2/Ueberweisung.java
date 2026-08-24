import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Ueberweisung {
    private final String quellkonto;
    private final String zielkonto;
    private final long betragInRappen;
    private final LocalDateTime zeitpunkt;

    public Ueberweisung(String quellkonto, String zielkonto, long betragInRappen) {
        this.quellkonto = Objects.requireNonNull(quellkonto, "Das Quellkonto darf nicht null sein.");
        this.zielkonto = Objects.requireNonNull(zielkonto, "Das Zielkonto darf nicht null sein.");
        if (betragInRappen <= 0) {
            throw new IllegalArgumentException("Der Betrag muss grösser als null sein.");
        }
        this.betragInRappen = betragInRappen;
        this.zeitpunkt = LocalDateTime.now();
    }

    public String beschreibung() {
        return String.format("%s: %s -> %s, CHF %s",
                zeitpunkt.withNano(0), quellkonto, zielkonto,
                BigDecimal.valueOf(betragInRappen, 2).toPlainString());
    }
}
