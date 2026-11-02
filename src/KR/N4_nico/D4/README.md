# KN-D4 (Nico) – Delegation, eigene Exceptions, Generics

Modul 320 · Niveau 4 · Java · eigene Aufgabenstellung von Nico (ZH-NICO)

> **Kompetenz D4:** Die eigene Aufgabenstellung zeigt **Delegation** (klare Trennung
> von Benutzerschnittstelle und Logik), **eigene Exceptions** fangen fehlerhafte
> Eingaben ab, und **Generics** werden in einer eigenen Klasse eingesetzt.

Dies ist die erste lauffähige Umsetzung des in [M4](../M4/README.md) geplanten
Text-Adventures *„Die Nebelhoehle"*.

## Das Spiel

Ein kleines Text-Adventure (wie Zork). Der Spieler bewegt sich per Texteingabe
durch sechs Räume, nimmt Gegenstände auf und benutzt sie. Im `Verlies` liegt ein
`schluessel`, der die verschlossene `Schatzkammer` öffnet. Wer das `gold` nimmt,
gewinnt.

```text
Befehle: gehe <richtung>, schau, nimm <ding>, inventar, benutze <ding>, hilfe, ende
```

## Ausführen

Vom Repository-Hauptordner aus (Java 17 oder neuer):

```powershell
javac -encoding UTF-8 -d out-d4 (Get-ChildItem src/KR/N4_nico/D4/code -Filter *.java).FullName
java -cp out-d4 KR.N4_nico.D4.code.SpielTest
java -cp out-d4 KR.N4_nico.D4.code.Starter
```

---

## 1. Delegation: Benutzerschnittstelle getrennt von der Logik

Drei Schichten mit klaren Aufgaben – jede delegiert, statt alles selbst zu tun:

| Schicht | Klasse | Aufgabe | kennt NICHT |
|---|---|---|---|
| Benutzerschnittstelle | `Konsole` | liest Eingaben (`Scanner`), gibt Text aus | die Spielregeln / den Weltzustand |
| Steuerung | `Spiel` | die Schleife; verbindet Konsole und Logik | Scanner-Details, Raum-Interna |
| Logik | `Spielwelt` | verarbeitet Befehle, verwaltet die Welt | `Scanner`, `System.out` |

`Spiel` liest über die `Konsole`, **delegiert** die Verarbeitung an `Spielwelt`
und gibt das Ergebnis wieder über die `Konsole` aus:

```java
String eingabe = konsole.einlesen("\n> ");
...
konsole.ausgeben(welt.verarbeite(eingabe));
```

`Spielwelt.verarbeite()` entscheidet nichts selbst, sondern **delegiert** an den
passenden `Befehl`:

```java
Befehl befehl = befehle.get(teile[0]);
...
return befehl.ausfuehren(this, argument);
```

Weil die Logik keine Konsole kennt, lässt sie sich direkt testen (siehe `SpielTest`)
– ganz ohne Tastatureingabe.

## 2. Eigene Exceptions für fehlerhafte Eingaben

Eine kleine Hierarchie eigener, geprüfter Exceptions:

```text
SpielException
├── UngueltigeEingabeException     (leere Eingabe)
├── UnbekannterBefehlException     (unbekanntes Befehlswort)
└── FehlendesArgumentException     (Befehl ohne nötiges Argument, z.B. "gehe")
```

Die **Logik wirft**, die **Benutzerschnittstelle fängt**:

```java
// Spielwelt (Logik) wirft bei fehlerhafter Eingabe
if (eingabe == null || eingabe.isBlank()) {
    throw new UngueltigeEingabeException("Bitte gib einen Befehl ein.");
}
...
if (befehl == null) {
    throw new UnbekannterBefehlException(teile[0]);
}
```

```java
// Spiel (Benutzerschnittstelle) faengt und zeigt die Meldung
try {
    konsole.ausgeben(welt.verarbeite(eingabe));
} catch (SpielException e) {
    konsole.fehler(e.getMessage());
}
```

So stürzt das Programm bei Falscheingaben nie ab, sondern antwortet freundlich.

## 3. Generics in eigener Klasse: `Inventar<T>`

`Inventar<T extends Spielobjekt>` ist eine selbst geschriebene generische Klasse.
Der Typparameter `T` ist auf `Spielobjekt` beschränkt, damit `getName()` benutzt
werden darf:

```java
public class Inventar<T extends Spielobjekt> {
    private final List<T> dinge = new ArrayList<>();
    public void hinzufuegen(T ding) { dinge.add(ding); }
    public T finde(String name) { ... }
    public T entferne(String name) { ... }
    public boolean enthaelt(String name) { ... }
}
```

Dieselbe Klasse wird **typsicher mehrfach** eingesetzt:

- `Spieler` hat ein `Inventar<Gegenstand>` (das Inventar).
- `Raum` hat ein `Inventar<Gegenstand>` (die Gegenstände im Raum).

Ohne Generics müsste man mit `Object` arbeiten und ständig casten; so liefert
`finde()` direkt den richtigen Typ zurück.

---

## Klassen

| Klasse | Rolle |
|---|---|
| `Spielobjekt` **(abstrakt)** | Basis mit Name/Beschreibung, abstrakte `untersuchen()` |
| `Raum`, `Gegenstand`, `Lampe` | Welt-Bausteine (`Lampe` ist `Benutzbar`) |
| `Inventar<T>` **(generisch)** | typsichere Sammlung für Spieler und Raum |
| `Spieler` | aktueller Raum + Inventar |
| `Befehl` **(Interface)** | Vertrag für alle Befehle |
| `Gehe/Schau/Nimm/Inventar/Benutze/HilfeBefehl` | die einzelnen Befehle |
| `Spielwelt` | **Logik**: Parser + Befehls-Dispatch + Weltzustand |
| `Konsole` | **Benutzerschnittstelle**: Ein-/Ausgabe |
| `Spiel` | **Steuerung**: Schleife, verbindet Konsole und Logik |
| `SpielException` + 3 Unterklassen | eigene Exceptions für Falscheingaben |
| `Welt` | baut die Karte (6 Räume) |
| `Starter` | `main` |
| `SpielTest` | Tests ohne JUnit |

## Beispiel-Durchlauf (Gewinn)

```text
=== Die Nebelhoehle ===
Du stehst am dunklen Eingang einer Hoehle.
Hier liegt: lampe
Ausgaenge: norden

> nimm lampe        -> Du nimmst: lampe
> benutze lampe     -> Die Lampe leuchtet, der Raum wird hell.
> gehe norden       -> Halle (Ausgaenge: sueden, westen, norden, osten)
> gehe osten        -> Die Tuer ist verschlossen. Du brauchst: schluessel
> gehe westen       -> Verlies (Hier liegt: schluessel)
> nimm schluessel   -> Du nimmst: schluessel
> gehe osten        -> Halle
> gehe osten        -> Schatzkammer (Hier liegt: gold)
> nimm gold         -> Du haeltst das Gold in den Haenden. Gewonnen!
```

## Antworten für die Besprechung

**Wo ist die Delegation?** `Spiel` macht weder Ein-/Ausgabe noch Spiellogik selbst,
sondern reicht beides weiter: Eingabe/Ausgabe an `Konsole`, Verarbeitung an
`Spielwelt`. Und `Spielwelt` reicht jeden Befehl an die passende `Befehl`-Klasse
weiter. Dadurch ist die Benutzerschnittstelle klar von der Logik getrennt.

**Wie werden fehlerhafte Eingaben abgefangen?** Die Logik wirft eine eigene
`SpielException` (leer, unbekannt, fehlendes Argument). Die Benutzerschnittstelle
fängt sie in einem `try/catch` und zeigt eine Meldung – kein Absturz.

**Wo wird Generics eingesetzt?** In der eigenen Klasse `Inventar<T extends
Spielobjekt>`. Sie wird typsicher als `Inventar<Gegenstand>` im `Spieler` und im
`Raum` verwendet.
