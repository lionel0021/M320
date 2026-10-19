package KR.KN_D3_nico.code;

public enum Stadt {
    ZUERICH("Zuerich"),
    BERN("Bern"),
    GENF("Genf");

    private final String anzeige;

    Stadt(String anzeige) {
        this.anzeige = anzeige;
    }

    public String getAnzeige() {
        return anzeige;
    }
}
