package KR.KN_M4.zork;

import java.io.IOException;

public interface SaveRepository {
    void save(GameState state) throws IOException;
    void load(GameState state) throws IOException;
}
