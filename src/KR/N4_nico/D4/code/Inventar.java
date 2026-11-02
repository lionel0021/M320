package KR.N4_nico.D4.code;

import java.util.ArrayList;
import java.util.List;

public class Inventar<T extends Spielobjekt> {
    private final List<T> dinge = new ArrayList<>();

    public void hinzufuegen(T ding) {
        dinge.add(ding);
    }

    public T finde(String name) {
        for (T ding : dinge) {
            if (ding.getName().equalsIgnoreCase(name)) {
                return ding;
            }
        }
        return null;
    }

    public T entferne(String name) {
        T ding = finde(name);
        if (ding != null) {
            dinge.remove(ding);
        }
        return ding;
    }

    public boolean enthaelt(String name) {
        return finde(name) != null;
    }

    public boolean istLeer() {
        return dinge.isEmpty();
    }

    public int groesse() {
        return dinge.size();
    }

    public List<String> namen() {
        List<String> namen = new ArrayList<>();
        for (T ding : dinge) {
            namen.add(ding.getName());
        }
        return namen;
    }
}
