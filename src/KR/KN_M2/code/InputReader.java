package KR.KN_M2.code;

import java.io.PrintStream;
import java.util.Objects;
import java.util.Scanner;

public class InputReader {
    private final Scanner reader;
    private final PrintStream output;

    public InputReader() {
        this(new Scanner(System.in), System.out);
    }

    public InputReader(Scanner reader, PrintStream output) {
        this.reader = Objects.requireNonNull(reader, "Der Scanner darf nicht null sein.");
        this.output = Objects.requireNonNull(output, "Die Ausgabe darf nicht null sein.");
    }

    public String getInput() {
        output.print("> ");
        return reader.hasNextLine() ? reader.nextLine() : "exit";
    }
}
