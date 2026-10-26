package KR.KN_M4.zork;

import java.util.ArrayList;
import java.util.List;

/** Tracks optional milestones and announces new rewards once. */
public final class AchievementTracker {
    private final List<Achievement> achievements = new ArrayList<>();
    public AchievementTracker() {
        achievements.add(new Achievement("first-step", "Erster Schritt", "Du hast den Nebelsteg verlassen.", "market_seen"));
        achievements.add(new Achievement("old-iron", "Altes Eisen", "Du hast den Leuchtturm geöffnet.", "turm_offen"));
        achievements.add(new Achievement("kindness", "Ein gutes Wort", "Der steinerne Hüter hat dich passieren lassen.", "hueter_passiert"));
        achievements.add(new Achievement("beacon", "Lichtbringer", "Das Mondsignal ist wieder aktiv.", "signal_aktiv"));
        achievements.add(new Achievement("home", "Heimkehr", "Du hast Arkenfall aus dem Nebel geführt.", "spiel_gewonnen"));
    }
    public List<Achievement> update(GameState state) {
        List<Achievement> unlockedNow = new ArrayList<>();
        for (Achievement achievement : achievements) if (achievement.update(state)) unlockedNow.add(achievement);
        return unlockedNow;
    }
    public String describe() {
        long earned = achievements.stream().filter(Achievement::unlocked).count();
        String rows = achievements.stream().map(a -> (a.unlocked() ? "[x] " : "[ ] ") + a.title() + " — " + a.description())
                .reduce((a, b) -> a + "\n" + b).orElse("");
        return "Erfolge " + earned + "/" + achievements.size() + "\n" + rows;
    }
}
