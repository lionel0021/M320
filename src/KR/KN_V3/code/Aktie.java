package KR.KN_V3.code;

import java.util.Objects;

public class Aktie {
    private final String symbol;
    private final String name;

    public Aktie(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Aktie)) {
            return false;
        }
        return symbol.equals(((Aktie) o).symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }

    @Override
    public String toString() {
        return symbol + " (" + name + ")";
    }
}
