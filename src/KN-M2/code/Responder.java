package kn.m2;

import java.util.Locale;
import java.util.Objects;

public class Responder {
    private static final String STANDARD_ANTWORT =
            "Dazu habe ich leider noch keine passende Lösung.";

    public String generateResponse(String input) {
        String normalisierteEingabe = Objects.requireNonNull(
                input, "Die Eingabe darf nicht null sein.")
                .strip()
                .toLowerCase(Locale.ROOT);

        if (normalisierteEingabe.isEmpty()) {
            return "Sie müssen mir schon eine Frage stellen, damit ich Ihnen helfen kann.";
        }

        if (enthaeltEines(normalisierteEingabe, "pc", "computer")) {
            return "Das sieht nach einem PC-Problem aus.";
        }

        if (normalisierteEingabe.contains("hilfe")) {
            return "Haben Sie versucht, den PC aus- und wieder einzuschalten?";
        }

        if (normalisierteEingabe.contains("drucker")) {
            return "Ist der Drucker eingeschaltet, verbunden und mit Papier versorgt?";
        }

        if (normalisierteEingabe.contains("bug")) {
            return "Haben Sie versucht, das Programm neu zu installieren?";
        }

        if (enthaeltEines(normalisierteEingabe, "dankeschön", "danke")) {
            return "Kein Problem, ich helfe gerne.";
        }

        if (enthaeltEines(normalisierteEingabe, "anrufen", "telefon")) {
            return "Tut mir leid, wir sind nicht per Telefon erreichbar.";
        }

        if (enthaeltEines(normalisierteEingabe, "internet", "router")) {
            return "Haben Sie schon versucht, Ihren Router neu zu starten?";
        }

        if (enthaeltEines(normalisierteEingabe, "bildschirm", "monitor")) {
            return "Prüfen Sie bitte, ob der Bildschirm eingesteckt und eingeschaltet ist.";
        }

        return STANDARD_ANTWORT;
    }

    private static boolean enthaeltEines(String text, String... suchbegriffe) {
        for (String suchbegriff : suchbegriffe) {
            if (text.contains(suchbegriff)) {
                return true;
            }
        }
        return false;
    }
}
