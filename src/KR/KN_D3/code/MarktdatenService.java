package KR.KN_D3.code;

import java.util.List;

public interface MarktdatenService {
    List<Marktdaten> lade(List<Coin> coins) throws MarktdatenException;
}
