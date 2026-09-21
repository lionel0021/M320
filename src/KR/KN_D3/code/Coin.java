package KR.KN_D3.code;

public enum Coin {
    BTC("Bitcoin", "XBTUSD", "XXBTZUSD"),
    SOL("Solana", "SOLUSD", "SOLUSD"),
    ETH("Ethereum", "ETHUSD", "XETHZUSD");

    private final String name;
    private final String handelspaar;
    private final String antwortSchluessel;

    Coin(String name, String handelspaar, String antwortSchluessel) {
        this.name = name;
        this.handelspaar = handelspaar;
        this.antwortSchluessel = antwortSchluessel;
    }

    public String getName() { return name; }
    public String getHandelspaar() { return handelspaar; }
    public String getAntwortSchluessel() { return antwortSchluessel; }
}
