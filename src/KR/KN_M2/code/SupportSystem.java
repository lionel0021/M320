package KR.KN_M2.code;

import java.io.PrintStream;
import java.util.Locale;
import java.util.Objects;

public class SupportSystem {
    private final InputReader reader;
    private final Responder responder;
    private final PrintStream output;

    public SupportSystem() {
        this(new InputReader(), new Responder(), System.out);
    }

    public SupportSystem(InputReader reader, Responder responder, PrintStream output) {
        this.reader = Objects.requireNonNull(reader, "Der InputReader darf nicht null sein.");
        this.responder = Objects.requireNonNull(responder, "Der Responder darf nicht null sein.");
        this.output = Objects.requireNonNull(output, "Die Ausgabe darf nicht null sein.");
    }

    public void start() {
        boolean finished = false;
        printWelcome();

        while (!finished) {
            String input = reader.getInput();
            if (istBeendenBefehl(input)) {
                finished = true;
            } else {
                output.println(responder.generateResponse(input));
            }
        }
        printGoodbye();
    }

    public void printWelcome() {
        output.println("Willkommen beim IT-Support");
        output.println();
        output.println("Bitte lassen Sie uns wissen, was Ihr Problem ist.");
        output.println("Wir werden versuchen, Ihnen so gut wie möglich zu helfen.");
        output.println("Schreiben Sie 'exit', um den IT-Support zu beenden.");
    }

    public void printGoodbye() {
        output.println("Ich hoffe, ich konnte helfen. Tschüss!");
    }

    private static boolean istBeendenBefehl(String input) {
        return input.stripLeading().toLowerCase(Locale.ROOT).startsWith("exit");
    }
}
