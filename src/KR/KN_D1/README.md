# KR.KN-D1 – Banksimulation

| | |
|---|---|
| **Kompetenznachweis** | KR.KN-D1 – Objektkommunikation und Datenkapselung |
| **Szenario** | Banksimulation |
| **Sprache** | Java |
| **Datum** | 17.08.2026 |

Original-Auftrag: <https://gitlab.com/ch-tbz-it/Stud/m320/-/tree/main/Kompetenzen/KN-D1>

## 1. Auftrag

Das Programm soll zeigen, wie eigene Klassen entworfen, Objekte erzeugt und
Methoden zwischen Objekten aufgerufen werden. Attribute dürfen nicht direkt von
aussen verändert werden. Einzahlen, Abheben und Überweisen verändern den
Zustand der beteiligten Konto-Objekte.

Der Auftrag verlangt zwei Implementationen. Deshalb enthält diese Abgabe zwei
Varianten der Banksimulation.

## 2. Implementation 1: direkte Kommunikation

Die einfache Variante besitzt zwei Klassen:

- `Konto` kapselt Kontonummer, Inhaber und Saldo.
- `Starter` erzeugt zwei Konten und stellt ein Konsolenmenü bereit, über das
  der Benutzer selbst einzahlt, abhebt und überweist (Eingabe über die Tastatur).

Beim Aufruf `privatkonto.ueberweisenAn(sparkonto, 30_000)` erhält das
Quellkonto ein anderes `Konto`-Objekt und einen Betrag. Das Quellkonto ruft
anschliessend `zielkonto.einzahlen(...)` auf. Die beiden Objekte kommunizieren
also direkt miteinander.

```mermaid
classDiagram
    class Konto {
        -String kontonummer
        -String inhaber
        -long saldoInRappen
        +einzahlen(long betragInRappen)
        +abheben(long betragInRappen)
        +ueberweisenAn(Konto zielkonto, long betragInRappen)
    }
    class Starter
    Starter ..> Konto : erzeugt und verwendet
    Konto --> Konto : überweist an
```

### Ausführen

```powershell
cd src\KR\KN_D1\implementation_1
javac -encoding UTF-8 -d out *.java
java -cp out KR.KN_D1.implementation_1.Starter
java -cp out KR.KN_D1.implementation_1.KontoTest
```

## 3. Zweites Beispiel: Garagen-Simulation

Ein zweites, eigenständiges Beispiel im Ordner `garage/`. Es zeigt dieselben
KN-D1-Prinzipien (Objektkommunikation und Datenkapselung) an einem anderen
Thema: einer Autogarage.

- `Fahrzeug` kapselt Kennzeichen, Reparaturkosten und den Reparatur-Status.
  Der Status kann nur über `markiereRepariert()` gesetzt werden.
- `Garage` verwaltet die registrierten Fahrzeuge. Sie erzeugt die
  `Fahrzeug`-Objekte und delegiert das Reparieren an das jeweilige Fahrzeug.
- `GarageStarter` ist die interaktive Simulation: der Benutzer registriert
  Fahrzeuge zur Reparatur (mit Kosten), markiert sie als repariert und fragt ab,
  welche Fahrzeuge schon repariert sind und wie hoch die Kosten sind.
- `GarageTest` prüft Registrieren, Reparatur-Status und Gesamtkosten.

```mermaid
classDiagram
    class Fahrzeug {
        -String kennzeichen
        -long kostenInRappen
        -boolean repariert
        +markiereRepariert()
    }
    class Garage {
        +registriere(String kennzeichen, long kostenInRappen) Fahrzeug
        +markiereRepariert(String kennzeichen) boolean
        +reparierteFahrzeuge() List
        +gesamtkostenInRappen() long
    }
    Garage "1" o-- "0..*" Fahrzeug : verwaltet
    Garage ..> Fahrzeug : erzeugt und delegiert
```

### Ausführen

```powershell
cd src\KR\KN_D1\garage
javac -encoding UTF-8 -d out *.java
java -cp out KR.KN_D1.garage.GarageStarter
java -cp out KR.KN_D1.garage.GarageTest
```

## 4. Implementation 2: Bank als Vermittler

Die erweiterte Variante trennt die Verantwortlichkeiten:

- `Kunde` speichert Kundennummer und Name.
- `Bankkonto` verwaltet einen gekapselten Saldo und gehört einem Kunden.
- `Bank` verwaltet mehrere Konten und stösst atomare Überweisungen an.
- `Ueberweisung` speichert einen unveränderlichen Eintrag im Verlauf.
- `BankSimulation` stellt ein interaktives Konsolenmenü bereit.

Der Benutzer kann Konten anzeigen, Geld einzahlen, Geld abheben, zwischen zwei
Konten überweisen und den Überweisungsverlauf anzeigen.

```mermaid
classDiagram
    class Kunde
    class Bankkonto
    class Bank
    class Ueberweisung
    class BankSimulation
    Kunde "1" <-- "0..*" Bankkonto : gehört
    Bank "1" o-- "0..*" Bankkonto : verwaltet
    Bank "1" *-- "0..*" Ueberweisung : protokolliert
    BankSimulation --> Bank : verwendet
```

### Ausführen

```powershell
cd src\KR\KN_D1\implementation_2
javac -encoding UTF-8 -d out *.java
java -cp out KR.KN_D1.implementation_2.BankSimulation
java -cp out KR.KN_D1.implementation_2.BankTest
```

Zu Beginn existieren diese Konten:

| Konto | Inhaber | Startsaldo |
|---|---|---:|
| `CH-2001` | Nico | CHF 1'500.00 |
| `CH-2002` | Lea | CHF 800.00 |

## 5. Fragen zur Besprechung

### Wie wird die Datenkapselung umgesetzt?

Alle Attribute sind `private`. Der Saldo kann nicht mit
`konto.saldoInRappen = ...` verändert werden. Eine Änderung ist nur über
kontrollierte Methoden wie `einzahlen`, `abheben` oder `ueberweisen` möglich.
Diese Methoden prüfen den Betrag und verhindern einen negativen Saldo.

`final` schützt ausserdem Werte wie Kontonummer und Inhaber davor, nach dem
Erzeugen des Objekts ausgetauscht zu werden. Listen werden als unveränderbare
Kopien oder Ansichten zurückgegeben.

### Wie kommunizieren die Objekte und welche Werte werden übergeben?

In Implementation 1 ruft ein Konto eine Methode des Zielkontos auf:

```java
zielkonto.einzahlen(betragInRappen);
```

Dabei werden das komplexe Objekt `zielkonto` und der primitive Wert
`betragInRappen` an die Methode `ueberweisenAn` übergeben.

In Implementation 2 kommuniziert `BankSimulation` mit `Bank`. Die Bank sucht
die beiden `Bankkonto`-Objekte und stösst über `ueberweisenAn(...)` die
Überweisung an. Das Quellkonto prüft beide neuen Salden, bevor es eines der
Objekte verändert. Dadurch bleibt der Vorgang auch bei einem Zahlenüberlauf
atomar.

### Wie verändert sich der Zustand eines Objekts?

Der Saldo ist der veränderbare Zustand eines Kontos. Eine Einzahlung erhöht
ihn, eine Abhebung reduziert ihn. Eine Überweisung verändert zwei Objekte:
Der Saldo des Quellkontos sinkt und der Saldo des Zielkontos steigt.

Vor einer Änderung werden alle Bedingungen geprüft. Bei einer ungültigen
Operation wird eine Exception ausgelöst und der Saldo bleibt unverändert.

### Was ist der Unterschied zwischen primitiven und komplexen Datentypen?

Primitive Datentypen speichern direkt einen einfachen Wert. Beispiele aus dem
Code sind `long saldoInRappen`, `int kundennummer` und `boolean laeuft`.

Komplexe Datentypen sind Objekte mit Attributen und Methoden. Beispiele sind
`String`, `Kunde`, `Bankkonto`, `Bank`, `List<Bankkonto>` und
`LocalDateTime`. Eine Variable eines komplexen Typs enthält eine Referenz auf
das Objekt.

## 6. Wesentliche Sicherheitsregeln

- Beträge müssen grösser als null sein.
- Ein Konto darf nicht überzogen werden.
- Quell- und Zielkonto müssen verschieden sein.
- Kontonummern müssen innerhalb einer Bank eindeutig sein.
- Geld wird intern als ganze Rappen (`long`) gespeichert; dadurch entstehen
  keine Rundungsfehler durch `double`.
- Ungültige Benutzereingaben und Zahlenüberläufe werden abgefangen; eine
  fehlgeschlagene Operation verändert keinen Saldo.

## 7. Dateien

```text
KN_D1/
├── README.md
├── implementation_1/
│   ├── Konto.java
│   ├── KontoTest.java
│   └── Starter.java
├── implementation_2/
│   ├── Bank.java
│   ├── Bankkonto.java
│   ├── BankSimulation.java
│   ├── BankTest.java
│   ├── Kunde.java
│   └── Ueberweisung.java
└── garage/
    ├── Fahrzeug.java
    ├── Garage.java
    ├── GarageStarter.java
    └── GarageTest.java
```

## 8. Lernziele-Check

- [x] Eigene Klassen entworfen und Objekte instanziiert
- [x] Methodenaufrufe zwischen Objekten gezeigt
- [x] Primitive Werte und komplexe Objekte übergeben
- [x] Attribute durch `private` vor direkter Veränderung geschützt
- [x] Zustandsänderungen durch Einzahlen, Abheben und Überweisen gezeigt
- [x] Zwei unterschiedliche Implementationen erstellt
