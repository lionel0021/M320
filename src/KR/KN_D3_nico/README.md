# KN-D3 (Nico) – Delegation, Service-Interface und eigene Exception

Modul 320 · Java · eigene Variante von Nico (ZH-NICO)

> Eigenständige zweite Lösung zu KN-D3. Gleiche Kompetenz wie die Haupt-Lösung
> (Delegation, Trennung von Eingabe/Logik/Service, eigene Exception), aber mit
> einem anderen Thema und **ohne externe Bibliothek oder Internet** – läuft
> direkt in IntelliJ.

## Idee

Eine Konsolen-App fragt das Wetter von **Zürich, Bern, Genf** (oder `ALLE`) ab und
zeigt Temperatur, Zustand und Luftfeuchte mit Abrufzeit. Ungültige Eingaben
führen zu einer **eigenen Exception** und einer verständlichen Meldung, danach
kann erneut gewählt werden. `0` beendet die App.

Die Daten liefert ein `WetterService`. Die App kennt nur dieses **Interface**,
nicht die konkrete Quelle – hier ein `LokalerWetterService` mit festen Werten.
Eine echte API (HTTP) wäre nur eine weitere Implementierung, ohne Änderung an der
Logik.

## Ausführen

Vom Repository-Hauptordner aus (Java 17 oder neuer, kein Internet nötig):

```powershell
javac -encoding UTF-8 -d out-d3 (Get-ChildItem src/KR/KN_D3_nico/code -Filter *.java).FullName
java -cp out-d3 KR.KN_D3_nico.code.WetterTest
java -cp out-d3 KR.KN_D3_nico.code.Starter
```

## Klassen (Verantwortung getrennt)

| Klasse | Verantwortung |
|---|---|
| `Starter` | `main` startet nur die App |
| `WetterApp` | verbindet die Objekte, Schleife, fängt Exceptions ab |
| `Konsole` | liest Eingaben (Scanner) und formatiert die Ausgabe |
| `EingabeValidator` | prüft die Auswahl; wirft `UngueltigeEingabeException` |
| `WetterLogik` | lässt validieren und beauftragt dann den Service (**Delegation**) |
| `WetterService` **(Interface)** | Vertrag: `lade(List<Stadt>)` |
| `LokalerWetterService` | konkrete Quelle mit festen Werten |
| `Stadt` / `Wetter` | erlaubte Städte / unveränderliches Ergebnisobjekt |
| `UngueltigeEingabeException`, `WetterException` | eigene Exceptions |

## Antworten für die Besprechung

**Wo ist die Delegation?**
`WetterLogik` prüft nicht selbst und lädt keine Daten selbst, sondern reicht beides
an ihre Helfer weiter:
```java
public List<Wetter> abfragen(String eingabe) throws UngueltigeEingabeException, WetterException {
    List<Stadt> staedte = validator.validiereAuswahl(eingabe);
    return service.lade(staedte);
}
```
Delegation über Methodenaufrufe an andere Objekte, nicht über Vererbung. Weil der
Service ein Interface ist, kann der Test einen Ersatz-Service einsetzen.

**Wo werden Eingaben validiert und Exceptions geworfen?**
`EingabeValidator.validiereAuswahl()` erlaubt nur ZUERICH, BERN, GENF und ALLE.
Leere oder unbekannte Eingaben werfen `UngueltigeEingabeException`. Die Exception
läuft durch `WetterLogik` zurück zur `WetterApp`, die sie auffängt und die Meldung
über `Konsole` ausgibt. Bei ungültiger Eingabe wird der Service **gar nicht**
aufgerufen (ein Test prüft genau das).

**Wie sind Eingabe, Logik und Datenquelle getrennt?**
Nur `Konsole` kennt den Scanner und die Textausgabe. `WetterLogik` kennt weder
Scanner noch die konkrete Quelle. Nur `LokalerWetterService` kennt die Daten.
Fehlt eine Quelle, meldet der Service das als `WetterException`.

## Menü

```text
Staedte: ZUERICH, BERN, GENF | ALLE | 0 = Beenden
```

## Dateien

```text
KN_D3_nico/
├── README.md
└── code/
    ├── Starter.java
    ├── WetterApp.java
    ├── Konsole.java
    ├── EingabeValidator.java
    ├── WetterLogik.java
    ├── WetterService.java
    ├── LokalerWetterService.java
    ├── Stadt.java
    ├── Wetter.java
    ├── UngueltigeEingabeException.java
    ├── WetterException.java
    └── WetterTest.java
```
