package KR.KN_V3.code;

import java.util.List;

public interface StockExchange {
    String getName();

    boolean istKotiert(String symbol);

    double getKurs(String symbol);

    List<Aktie> getAktien();
}
