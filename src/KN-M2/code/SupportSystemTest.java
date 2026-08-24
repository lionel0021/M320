import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class SupportSystemTest {
    public static void main(String[] args) {
        testResponderIstUnabhaengigVonGrossschreibung();
        testLeereEingabeUndSpaeteRegelnSindErreichbar();
        testVollstaendigerDialog();
        System.out.println("KN-M2: Alle Tests erfolgreich.");
    }

    private static void testResponderIstUnabhaengigVonGrossschreibung() {
        Responder responder = new Responder();
        pruefe(responder.generateResponse("Mein COMPUTER startet nicht").contains("PC-Problem"),
                "Computer-Stichwort wurde nicht erkannt");
        pruefe(responder.generateResponse("Der DRUCKER streikt").contains("Drucker"),
                "Drucker-Stichwort wurde nicht erkannt");
    }

    private static void testLeereEingabeUndSpaeteRegelnSindErreichbar() {
        Responder responder = new Responder();
        pruefe(responder.generateResponse("   ").contains("Frage stellen"),
                "Leere Eingabe wurde nicht erkannt");
        pruefe(responder.generateResponse("Mein Internet ist weg").contains("Router"),
                "Internet-Regel ist nicht erreichbar");
        pruefe(responder.generateResponse("Der Bildschirm bleibt schwarz").contains("Bildschirm"),
                "Bildschirm-Regel ist nicht erreichbar");
    }

    private static void testVollstaendigerDialog() {
        ByteArrayOutputStream ausgabePuffer = new ByteArrayOutputStream();
        PrintStream ausgabe = new PrintStream(ausgabePuffer, true, StandardCharsets.UTF_8);
        InputReader reader = new InputReader(new Scanner("Mein PC startet nicht\nexit\n"), ausgabe);
        SupportSystem supportSystem = new SupportSystem(reader, new Responder(), ausgabe);

        supportSystem.start();

        String text = ausgabePuffer.toString(StandardCharsets.UTF_8);
        pruefe(text.contains("Willkommen beim IT-Support"), "Begrüssung fehlt");
        pruefe(text.contains("PC-Problem"), "Support-Antwort fehlt");
        pruefe(text.contains("Tschüss"), "Verabschiedung fehlt");
    }

    private static void pruefe(boolean bedingung, String meldung) {
        if (!bedingung) {
            throw new AssertionError(meldung);
        }
    }
}
