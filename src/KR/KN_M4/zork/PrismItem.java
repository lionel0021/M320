package KR.KN_M4.zork;

public final class PrismItem extends Item {
    public PrismItem() { super("prisma", "Mondprisma", "Ein geschliffenes Prisma, das selbst schwaches Licht bündelt."); }
    @Override public String use(GameState state, String target) {
        if (state.player().location() != RoomId.OBSERVATORIUM)
            return "Das Prisma braucht den Globus im Observatorium.";
        if (target.equals("rabe walfisch krone")) {
            state.setFlag("signal_aktiv", true);
            return "Das Prisma bündelt das Mondlicht. Ein Signal antwortet aus dem Nebel.";
        }
        return "Die Sternscheiben müssen nach der Inschrift geordnet sein: Rabe, Walfisch, Krone.";
    }
}
