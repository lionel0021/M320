package KR.KN_D3_nico.code;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Wetter {
    private final Stadt stadt;
    private final double temperaturC;
    private final String zustand;
    private final int luftfeuchteProzent;
    private final LocalDateTime abrufzeit;

    public Wetter(Stadt stadt, double temperaturC, String zustand, int luftfeuchteProzent, LocalDateTime abrufzeit) {
        this.stadt = stadt;
        this.temperaturC = temperaturC;
        this.zustand = zustand;
        this.luftfeuchteProzent = luftfeuchteProzent;
        this.abrufzeit = abrufzeit;
    }

    public Stadt getStadt() {
        return stadt;
    }

    public double getTemperaturC() {
        return temperaturC;
    }

    public String getZustand() {
        return zustand;
    }

    public int getLuftfeuchteProzent() {
        return luftfeuchteProzent;
    }

    public LocalDateTime getAbrufzeit() {
        return abrufzeit;
    }

    @Override
    public String toString() {
        return String.format("%s: %.1f C, %s, %d%% Luftfeuchte (abgerufen %s)",
                stadt.getAnzeige(), temperaturC, zustand, luftfeuchteProzent,
                abrufzeit.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }
}
