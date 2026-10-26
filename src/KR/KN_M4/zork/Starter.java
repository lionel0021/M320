package KR.KN_M4.zork;

import java.util.Scanner;

/** Console entry point for the Zork-inspired adventure. */
public final class Starter {
    private Starter() { }
    public static void main(String[] args) {
        GameState state = new GameState(); EventBus events = new EventBus();
        events.subscribe(new ConsoleNarrator()); GameEngine game = new GameEngine(state, events);
        System.out.println("NEBEL ÜBER ARKENFALL\nEin Zork-inspiriertes Textabenteuer. Tippe ‚hilfe‘ für Befehle.\n");
        System.out.println(game.execute("schau"));
        try (Scanner scanner = new Scanner(System.in)) {
            while (state.running()) {
                System.out.print("\n> ");
                if (!scanner.hasNextLine()) break;
                String response = game.execute(scanner.nextLine());
                if (!response.isBlank()) System.out.println(response);
                if (state.hasFlag("spiel_gewonnen")) state.stop();
            }
        }
    }
}
