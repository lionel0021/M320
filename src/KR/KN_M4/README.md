# KR.KN-M4 – Eigenes OO-Projekt: Nebel über Arkenfall

**Projekt:** Zork-inspiriertes Konsolen-Textadventure in Java  
**Stand:** 2. November 2026

## Auftrag und Ziel

Im Niveau 4 (Promised Land of Expert Skills) werden die OO-Konzepte in einem eigenen Produkt eingesetzt. Das Repository nennt als Modulziele: Anwendungsprobleme analysieren, OO-Programme modellieren und dokumentieren, OO-Design implementieren sowie Korrektheit und Qualität überprüfen. Als N4-Themen sind Abstraktionen (abstrakte Klassen und Interfaces) und Design Patterns aufgeführt. Eine separate Bewertungsrubrik für dieses Zork-Projekt ist im lokalen Repository nicht vorhanden; der Stand unten ist deshalb ein fachlicher Abgleich mit diesen Lernzielen, keine offizielle Note.

## Spiel

Arkenfall versinkt im Nebel. Die Spielerin oder der Spieler erkundet Dorf, Wald, Leuchtturm und Krypta, sammelt Gegenstände, spricht mit Figuren und löst das Sternbild-Rätsel, um das Leuchtfeuer wieder zu aktivieren.

### Starten

Im Projektstamm:

```powershell
javac -encoding UTF-8 -d out src/KR/KN_M4/zork/*.java
java -cp out KR.KN_M4.zork.Starter
```

Befehle im Spiel: `hilfe`, `schau`, `geh <Richtung>`, `nimm <Gegenstand>`, `benutze <Gegenstand> an <Ziel>`, `untersuche <Objekt>`, `rede`, `inventar`, `tagebuch`, `karte`, `zeit`, `verlauf`, `speichern`, `laden`, `ende`.

### Lösungsweg

1. Auf dem Markt den Eisenschlüssel holen; in der Taverne mit Mara sprechen und die Sturmlaterne mitnehmen.
2. Im Wald das Mondkraut holen und im Steinkreis das Mondprisma aufnehmen.
3. Mit dem Schlüssel den Leuchtturm öffnen. Logbuch und Inschrift untersuchen.
4. In der Krypta die Laterne benutzen und mit dem Hüter sprechen. Danach das Mondkraut beim Hüter einsetzen und erneut mit ihm sprechen.
5. Im Observatorium das Prisma mit der Reihenfolge aus der Inschrift benutzen: `benutze Mondprisma an Rabe Walfisch Krone`.
6. Zum Tor in der Krypta zurückkehren und nach Norden gehen.

## OO-Entwurf und N4-Konzepte

| Konzept / Pattern | Einsatz im Projekt |
|---|---|
| Abstrakte Klasse | `Item` definiert gemeinsame Gegenstandsdaten und die abstrakte Verwendung. |
| Interface und Polymorphie | `Command`, `Character`, `SaveRepository` und `GameEventListener` legen austauschbare Verträge fest. |
| Command Pattern | `CommandParser` erzeugt konkrete Befehlsobjekte; `GameEngine` führt sie aus. |
| Factory | `WorldFactory` baut Räume, Verbindungen und Startgegenstände. |
| Observer | `EventBus` benachrichtigt Listener, etwa `ConsoleNarrator`, über Ereignisse. |
| Repository | `SaveRepository` trennt Laden/Speichern vom Dateiformat; `PropertiesSaveRepository` schreibt Properties. |
| Eigene Exceptions | `UngueltigeEingabeException` kennzeichnet fehlerhafte Eingaben; `SpielstandException` beschreibt fehlende oder beschädigte Speicherstände. |
| Delegation | `TalkCommand` delegiert Dialoge an `DialogueService`; Spielzustand und Welt sind in eigene Klassen verteilt. |

## Modellierung

- [Klassendiagramm (PlantUML)](diagramm/klassendiagramm.puml)
- [Sequenzdiagramm: Gegenstand benutzen](diagramm/sequenzdiagramm.puml)

Das Klassendiagramm zeigt die zentralen Klassen, Interfaces, abstrakte Klasse und Beziehungen. Das Sequenzdiagramm dokumentiert den Ablauf eines Gegenstand-Einsatzes. Beide Diagramme sind PlantUML-Quellen; gerenderte PNG/SVG-Dateien sind nicht enthalten.

## Qualitätssicherung und aktueller Abgleich

| Lernziel / Nachweis | Stand |
|---|---|
| Eigenes analysiertes Produkt mit nachvollziehbaren Spielregeln | Umgesetzt; Spielziel, Raumkarte, Gegenstände, Figuren und Siegbedingung vorhanden. |
| Interfaces und abstrakte Klassen sinnvoll einsetzen | Umgesetzt; mehrere Verträge und `Item`-Vererbung werden im laufenden Spiel verwendet. |
| Design Patterns im Code einsetzen | Umgesetzt; Command, Factory, Observer und Repository klar erkennbar; Delegation ebenfalls vorhanden. |
| UML modellieren | Teilweise; beide Quelldiagramme vorhanden, aber nicht gerendert und nach den jüngsten Änderungen abzugleichen. |
| Korrektheit/Qualität nachweisen | Noch offen: Es gibt keine automatisierten Tests und keine dokumentierten Testläufe. Die manuelle Abnahmeliste unten ist vorbereitet, aber nicht ausgeführt. |
| Bedienung und Abgabe dokumentieren | Diese README ergänzt Start, Befehle, Lösungsweg und Architektur. |

### Manuelle Abnahmeliste (noch auszuführen)

- Schlüssel öffnet den Turm; ohne Schlüssel bleibt der Zugang gesperrt.
- Krypta bleibt ohne Licht unzugänglich.
- Hüter lässt erst nach Mondkraut und erneutem Gespräch passieren.
- Nur die geordnete Sternbild-Antwort aktiviert das Signal.
- Das Ausgangstor bleibt bis Hüter-Passage und aktivem Signal gesperrt.
- Speichern/Laden erhält Standort, Inventar, Flags, Spielzeit und besuchte Räume; wiederholtes Laden erzeugt keine Gegenstand-Duplikate.
- Leere, unbekannte und zu lange Eingaben stürzen das Spiel nicht ab.

## Noch offen für einen belastbaren Niveau-4-Nachweis

1. Abnahmeliste tatsächlich durchspielen und Ergebnisse dokumentieren.
2. Automatisierte Tests für Spielregeln, Eingabe und Speichern/Laden ergänzen.
3. PlantUML-Diagramme an den aktuellen Code anpassen und als Bild rendern.
4. Den konkreten Kompetenzauftrag bzw. die Lehrpersonen-Rubrik ergänzen, sobald sie vorliegt; sie ist lokal nicht abgelegt.

Die Punkte 1–3 sind die klaren Nachweis-Lücken im aktuellen Stand. Sie sind keine Behauptung, dass das Produkt insgesamt unvollständig wäre; die spielbare Kernfunktion und mehrere zentrale N4-Konzepte sind bereits umgesetzt.


