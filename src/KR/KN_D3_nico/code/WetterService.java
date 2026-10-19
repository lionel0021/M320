package KR.KN_D3_nico.code;

import java.util.List;

public interface WetterService {
    List<Wetter> lade(List<Stadt> staedte) throws WetterException;
}
