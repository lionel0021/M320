package KR.KN_D3.code;

import java.math.BigDecimal;
import java.time.Instant;

// Unveraenderliches Datenobjekt: Geldwerte werden ohne double-Rundungsfehler gespeichert.
public record Marktdaten(Coin coin, BigDecimal preis, BigDecimal hoch24h,
                        BigDecimal tief24h, BigDecimal volumen24h, Instant abgerufenAm) {
}
