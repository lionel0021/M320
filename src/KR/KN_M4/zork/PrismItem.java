package KR.KN_M4.zork;

public final class PrismItem extends Item {
    public PrismItem() { super("prisma", "Mondprisma", "Ein geschliffenes Prisma, das selbst schwaches Licht bündelt."); }
    @Override public String use(GameState state, String target) {
        if (target.contains("globus") || target.contains("linse") || target.contains("licht")) {
            state.setFlag("signal_aktiv", true);
            return "Das Prisma bündelt das Mondlicht. Ein Signal antwortet aus dem Nebel.";
        }
        return "Im Prisma tanzen blasse Farben. Es braucht eine Lichtquelle oder Linse.";
    }
}
