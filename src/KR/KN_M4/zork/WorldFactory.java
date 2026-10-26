package KR.KN_M4.zork;

/** Builds the story map in one place; room links are explicit and easy to review. */
public final class WorldFactory {
    private WorldFactory() { }
    public static World create() {
        World w = new World();
        add(w, RoomId.KAI, "Nebelsteg", "Der Kai von Arkenfall liegt still. Nebel verschluckt das Meer; die Leuchtturmlaterne ist erloschen.");
        add(w, RoomId.MARKT, "Alter Markt", "Verlassene Stände knarren im Wind. Eine Händlerin wartet unter einem blauen Tuch.");
        add(w, RoomId.TAVERNE, "Zum gestrandeten Wal", "Der Kamin glimmt. Wirtin Mara kennt die alten Geschichten des Dorfes.");
        add(w, RoomId.WALDRAND, "Waldsaum", "Schwarze Tannen stehen dicht. Ein Pfad führt tiefer in den Wald.");
        add(w, RoomId.WALD, "Flüsterwald", "Zwischen den Stämmen schweben Irrlichter. Etwas raschelt nahe einer Wurzelhöhle.");
        add(w, RoomId.STEINKREIS, "Steinkreis", "Runen ziehen sich über uralte Steine. In der Mitte liegt ein leerer Sockel.");
        add(w, RoomId.TURM_EINGANG, "Leuchtturm, Eingang", "Eine eisenbeschlagene Tür versperrt den Weg nach oben.");
        add(w, RoomId.TURM_HALLE, "Leuchtturm, Halle", "Eine Wendeltreppe führt hinauf. An der Wand hängt ein verblasstes Logbuch.");
        add(w, RoomId.BIBLIOTHEK, "Leuchtturm, Archiv", "Karten und Bücher stapeln sich. Eine Inschrift nennt drei Sternbilder.");
        add(w, RoomId.OBSERVATORIUM, "Leuchtturm, Observatorium", "Ein gewaltiger Globus steht unter einer zerbrochenen Linse.");
        add(w, RoomId.KRYPTA, "Versunkene Krypta", "Kalte Stufen führen in die Dunkelheit. Ein steinerner Hüter bewacht ein Tor.");
        add(w, RoomId.AUSGANG, "Der sichere Hafen", "Die Glocke des Hafens klingt durch den klaren Morgen. Arkenfall ist gerettet.");
        link(w, RoomId.KAI, Direction.NORD, RoomId.MARKT); link(w, RoomId.KAI, Direction.WEST, RoomId.TURM_EINGANG);
        link(w, RoomId.MARKT, Direction.SUED, RoomId.KAI); link(w, RoomId.MARKT, Direction.WEST, RoomId.TAVERNE);
        link(w, RoomId.MARKT, Direction.OST, RoomId.WALDRAND); link(w, RoomId.TAVERNE, Direction.OST, RoomId.MARKT);
        link(w, RoomId.WALDRAND, Direction.WEST, RoomId.MARKT); link(w, RoomId.WALDRAND, Direction.NORD, RoomId.WALD);
        link(w, RoomId.WALD, Direction.SUED, RoomId.WALDRAND); link(w, RoomId.WALD, Direction.OST, RoomId.STEINKREIS);
        link(w, RoomId.STEINKREIS, Direction.WEST, RoomId.WALD); link(w, RoomId.TURM_EINGANG, Direction.OST, RoomId.KAI);
        link(w, RoomId.TURM_EINGANG, Direction.OBEN, RoomId.TURM_HALLE); link(w, RoomId.TURM_HALLE, Direction.UNTEN, RoomId.TURM_EINGANG);
        link(w, RoomId.TURM_HALLE, Direction.OBEN, RoomId.BIBLIOTHEK); link(w, RoomId.BIBLIOTHEK, Direction.UNTEN, RoomId.TURM_HALLE);
        link(w, RoomId.BIBLIOTHEK, Direction.OBEN, RoomId.OBSERVATORIUM); link(w, RoomId.OBSERVATORIUM, Direction.UNTEN, RoomId.BIBLIOTHEK);
        link(w, RoomId.OBSERVATORIUM, Direction.NORD, RoomId.KRYPTA); link(w, RoomId.KRYPTA, Direction.SUED, RoomId.OBSERVATORIUM);
        link(w, RoomId.KRYPTA, Direction.NORD, RoomId.AUSGANG);
        w.room(RoomId.MARKT).place(new KeyItem()); w.room(RoomId.TAVERNE).place(new LanternItem());
        w.room(RoomId.WALD).place(new HerbItem()); w.room(RoomId.STEINKREIS).place(new PrismItem());
        return w;
    }
    private static void add(World w, RoomId id, String name, String description) { w.add(new Room(id, name, description)); }
    private static void link(World w, RoomId from, Direction direction, RoomId to) { w.room(from).connect(direction, to); }
}
