package KR.KN_M4.zork;

import java.util.List;

/** Delegates dialogue to the character object found in the current room. */
public final class DialogueService {
    private final List<Character> characters = List.of(new Mara(), new MarketTrader(), new StoneGuardian());
    public String talk(GameState state, String requestedPerson) {
        Character character = characterAt(state.player().location());
        if (character == null) return "Hier ist niemand, mit dem du sprechen kannst.";
        if (!requestedPerson.isBlank() && !character.id().contains(requestedPerson.toLowerCase()))
            return "Hier ist niemand namens ‚" + requestedPerson + "‘.";
        return character.speak(state);
    }
    private Character characterAt(RoomId room) {
        String id = switch (room) { case TAVERNE -> "mara"; case MARKT -> "haendlerin"; case KRYPTA -> "hueter"; default -> ""; };
        return characters.stream().filter(character -> character.id().equals(id)).findFirst().orElse(null);
    }
}
