# KN-V1 – Vererbung, `AbstractList` und `Iterator`

Diese Lösung setzt den vollständigen Bibliotheksauftrag um. Der ausführbare Code
liegt unter [`code/`](code/), die bewusst duplizierte Ausgangslösung aus Aufgabe 1
unter [`code/ohnevererbung/`](code/ohnevererbung/) und das Klassendiagramm unter
[`diagramm/klassendiagramm.puml`](diagramm/klassendiagramm.puml).

## Ausführen

Vom Repository-Hauptordner aus (Java 11 oder neuer):

```powershell
javac -encoding UTF-8 -d out-v1 (Get-ChildItem src/KR/KN_V1/code -Recurse -Filter *.java).FullName
java -cp out-v1 KR.KN_V1.code.MedienTest
java -cp out-v1 KR.KN_V1.code.Starter
```

`MedienTest` prüft Vererbung, Bibliotheksverwaltung, die eigene Liste, beide
Iteratoren und das E-Book-Verhalten. `Starter` öffnet eine menügesteuerte
Konsolenanwendung. Alle Medien können dort per Benutzereingabe erfasst, gesucht,
ausgeliehen, zurückgegeben, entfernt und angezeigt werden.

## 1–3: Ausgangslage und einfache Hierarchie

Die Klassen `BuchOhneVererbung` und `ZeitschriftOhneVererbung` zeigen zunächst
die Duplikation. Danach wird der gemeinsame Teil in `Medium` verschoben.

1. **Welche Attribute gehören in `Medium`?**  `titel`, `erscheinungsjahr`,
   `inventarnummer` und `ausgeliehen`, weil sie bei allen ursprünglichen
   Medientypen vorkommen.
2. **Welche Attribute bleiben in den Subklassen?** Beim Buch `autor` und `isbn`,
   bei der Zeitschrift `ausgabe` und `monat`. Das sind typspezifische Daten.
3. **Was wurde vereinfacht?** Gemeinsame Felder, Getter und die Logik für
   Ausleihe/Rückgabe existieren nur noch einmal in `Medium`. Änderungen daran
   müssen dadurch nur an einer Stelle vorgenommen werden.

Die geerbte Funktionalität ist in `MedienTest` sichtbar: Ein `Buch` kann etwa
`getTitel()`, `isAusgeliehen()`, `ausleihen()` und `zurueckgeben()` verwenden,
obwohl diese Methoden ausschließlich in `Medium` programmiert sind.

## 4–7: Neue Anforderung DVD

4. **Wo wird `DVD` eingehängt?** Direkt unter `Medium`, also
   `DVD extends Medium`.
5. **Was kann wiederverwendet werden?** Titel, Erscheinungsjahr,
   Inventarnummer, Ausleihstatus und sämtliche gemeinsamen Methoden.
6. **Welche Klassen mussten angepasst werden?** Für den neuen Typ musste nur
   `DVD` ergänzt werden. `Buch` und `Zeitschrift` blieben unverändert. Die
   Bibliothek speichert ohnehin den Obertyp `Medium`.
7. **Welchen Vorteil zeigt das?** Eine bestehende Hierarchie lässt sich um eine
   Subklasse erweitern, ohne den bereits funktionierenden Code der anderen
   Subklassen zu duplizieren oder umzubauen. Polymorphie erlaubt zudem, alle
   Typen als `Medium` zu behandeln.

## 5: Bibliotheksverwaltung

`Bibliothek` verwaltet alle Objekte in einer gemeinsamen `MedienListe`. Sie
stellt die geforderten Methoden bereit:

- `hinzufuegen(Medium)`; doppelte Inventarnummern werden abgelehnt
- `suchen(String)`; liefert das Medium oder `null`
- `entfernen(String)`; eine unbekannte Nummer bleibt ohne Wirkung
- `anzahlMedien()`

Damit können `Buch`, `Zeitschrift`, `DVD` und `EBook` gemeinsam gespeichert
werden.

## 6: Eigene Liste und Fragen 8–10

`MedienListe extends AbstractList<Medium>` kapselt intern eine
`ArrayList<Medium>`.

8. **Warum müssen `get()` und `size()` implementiert werden?** Beide sind in
   `AbstractList` abstrakt. `get()` definiert den indexbasierten Zugriff und
   `size()` die Anzahl Elemente. Auf diesen zwei Grundoperationen baut die
   abstrakte Klasse viele weitere Leseoperationen auf.
9. **Was liefert `AbstractList` bereits?** Unter anderem `iterator()`,
   `listIterator()`, `isEmpty()`, `contains()`, `indexOf()`, `equals()`,
   `hashCode()` und `toString()`. Viele davon funktionieren bereits allein mit
   `get()` und `size()`.
10. **Warum nicht alle Methoden von `List` neu programmieren?**
    `AbstractList` ist eine Skelettimplementierung des `List`-Interfaces. Sie
    stellt allgemeinen Code bereit und verlangt nur die grundlegenden
    Operationen. Das reduziert Code und Fehlerquellen.

Für eine sinnvoll veränderbare Liste implementiert die Lösung zusätzlich
`add(int, Medium)`, `set(int, Medium)` und `remove(int)`. Das geerbte
`add(Medium)` delegiert an `add(size(), medium)` und funktioniert dadurch
ebenfalls.

## 7: Iterator verwenden und Fragen 11–13

11. **Woher stammt `iterator()`?** `MedienListe` erbt die konkrete
    Implementierung von `AbstractList`.
12. **Warum funktioniert sie ohne eigenen Standarditerator?** Der Iterator von
    `AbstractList` greift über `get(index)` und `size()` auf die Elemente zu.
    Genau diese beiden Operationen stellt `MedienListe` bereit.
13. **Rolle von `AbstractList` und `Iterable`/`List`:** `List` erweitert
    `Collection`, und `Collection` erweitert `Iterable`. Daher verspricht jede
    Liste eine `iterator()`-Methode und kann in einer for-each-Schleife stehen.
    `AbstractList` erfüllt dieses Versprechen mit einer fertigen
    Implementierung; `MedienListe` liefert dafür den konkreten Datenzugriff.

## 8: Eigener Iterator

`AusgelieheneMedienIterator implements Iterator<Medium>` dekoriert den
normalen Listeniterator. `hasNext()` sucht und puffert das nächste ausgeliehene
Medium; `next()` gibt es zurück. Dadurch überspringt er freie Medien, mehrfaches
Aufrufen von `hasNext()` verliert kein Element, und `next()` wirft am Ende wie
vom Interface verlangt eine `NoSuchElementException`.

## 9: E-Book und Fragen 14–17

14. **Was passt weiterhin?** Titel, Erscheinungsjahr und Inventarnummer sind
    auch für ein E-Book sinnvolle gemeinsame Metadaten. Darum ist ein E-Book im
    allgemeinen Sinn weiterhin ein `Medium`.
15. **Was ist problematisch?** Die geerbten Methoden `ausleihen()` und
    `zurueckgeben()` modellieren eine physische Zustandsänderung. Bei einem
    digitalen Medium ohne physische Einzelausleihe sind sie unpassend.
16. **Würde ich die Hierarchie ändern?** Ja. Die langfristig saubere Lösung ist,
    in `Medium` nur gemeinsame Metadaten zu halten. Die Ausleihoperationen
    gehören in ein Interface `Ausleihbar` oder in eine Zwischenklasse
    `PhysischesMedium`. `Buch`, `Zeitschrift` und `DVD` implementieren/erweitern
    diese; `EBook` erweitert nur `Medium`. In dieser Auftragslösung überschreibt
    `EBook` die unpassenden Operationen und wirft
    `UnsupportedOperationException`, um das Problem ausführbar sichtbar zu
    machen. Das ist eine Demonstration, nicht das empfohlene Enddesign.
17. **Wann wird eine Hierarchie unpraktisch?** Wenn neue Subtypen wesentliche
    geerbte Operationen nicht sinnvoll erfüllen können, Sonderfälle oder leere
    Methoden brauchen oder das Ersetzen des Obertyps nicht mehr sicher ist
    (Verstoß gegen das Liskovsche Substitutionsprinzip). Dann ist der Obertyp zu
    breit modelliert und sollte aufgeteilt oder durch Interfaces/Komposition
    ergänzt werden.

## 10: Fehleranalyse und Fragen 18–21

Ausgangscode:

```java
public class MedienListe extends AbstractList<Medium> {
    private ArrayList<Medium> daten = new ArrayList<>();

    public Medium get(int index) {
        return daten.get(index);
    }

    // size() fehlt
}
```

18. **Kompiliert die Klasse?** Nein. Die Klasse ist konkret, implementiert aber
    nicht alle geerbten abstrakten Methoden.
19. **Warum verlangt `AbstractList` Methoden?** Es kennt den konkreten Speicher
    der Unterklasse nicht. Die abstrakten Grundoperationen bilden den Vertrag,
    über den die bereits implementierten Listenmethoden auf die Daten zugreifen.
20. **Was bedeutet `abstract` hier?** Eine abstrakte Methode gibt Signatur und
    Vertrag vor, besitzt aber keine konkrete Implementierung. Die erste konkrete
    Subklasse muss sie implementieren.
21. **Welche Methode fehlt mindestens?** `public int size()`. Für eine rein
    lesbare Liste genügen dann `get()` und `size()`; für `add()` muss außerdem
    eine passende Änderungsoperation überschrieben werden.

Korrektur:

```java
@Override
public int size() {
    return daten.size();
}
```

## 11: Transfer – Interface oder abstrakte Klasse?

| Situation | Sinnvolle Wahl | Begründung |
|---|---|---|
| Medientypen teilen Attribute und Verhalten | eigene Superklasse (`Medium`) | Sie bündelt gemeinsamen Zustand und fertigen Code. |
| Eigene Sammlung soll sich wie eine Java-Liste verhalten | abstrakte Bibliotheksklasse (`AbstractList`) | Die Skelettimplementierung liefert den Großteil des Listenverhaltens. |
| Eine Klasse soll per for-each durchlaufen werden | Interface (`Iterable<T>`) | Es beschreibt die Fähigkeit, einen Iterator bereitzustellen, unabhängig von einer Klassenhierarchie. |
| Eigener Mechanismus zum schrittweisen Durchlaufen | Interface (`Iterator<T>`) | `hasNext()` und `next()` bilden den standardisierten Laufmechanismus. |

## Abschlussfragen 22–31

22. **Vorteile einer einfachen Vererbungshierarchie:** Gemeinsamer Code steht
    zentral, neue Subklassen können ihn wiederverwenden und bestehende
    Algorithmen können polymorph mit dem Obertyp arbeiten. Das macht passende
    Erweiterungen klein und lokal.
23. **`extends` und `super(...)`:** `extends` erklärt die Ist-ein-Beziehung und
    vererbt zugängliche Eigenschaften und Methoden. `super(...)` ruft im
    Subklassenkonstruktor den Konstruktor der Superklasse auf und initialisiert
    deren Teil des Objekts; der Aufruf muss als erste Anweisung erfolgen.
24. **Nachteil zu vieler spezifischer Eigenschaften in der Superklasse:**
    Unterklassen erben unpassenden Zustand und unpassendes Verhalten. Es
    entstehen Sonderfälle, ungültige Zustände, hohe Kopplung und Änderungen an
    vielen Klassen.
25. **Eigene Superklasse vs. abstrakte Java-Bibliotheksklasse:** Eine eigene
    Superklasse modelliert die Fachdomäne und wird vollständig selbst gestaltet.
    Eine Bibliotheksklasse wie `AbstractList` liefert einen von Java definierten
    Vertrag samt wiederverwendbarer Implementierung; die eigene Klasse ergänzt
    nur die geforderten Grundoperationen.
26. **Warum kann `AbstractList` nicht instanziiert werden?** Die Klasse ist
    `abstract` und besitzt unimplementierte, vom konkreten Speicher abhängige
    Operationen. Erst eine konkrete Subklasse vervollständigt sie.
27. **Mindestens nötige Methoden für `MedienListe`:** Für eine konkrete lesbare
    `AbstractList`: `get(int)` und `size()`. Damit `add(...)` funktioniert,
    zusätzlich `add(int, Medium)` (oder eine eigene passende Add-Methode).
28. **Was ist ein Interface?** Ein Java-Typ, der einen Vertrag aus
    Methodensignaturen (und optional `default`-/`static`-Methoden) beschreibt,
    ohne eine konkrete Klassenvererbung vorzuschreiben. Eine Klasse kann mehrere
    Interfaces implementieren.
29. **Aufgabe von `Iterator`:** Elemente einer Datenstruktur nacheinander
    zugänglich machen, ohne deren interne Speicherung offenzulegen.
30. **`hasNext()` und `next()`:** `hasNext()` prüft, ob noch ein Element
    vorhanden ist, ohne es zu verbrauchen. `next()` liefert das nächste Element
    und rückt weiter; ist keines vorhanden, muss es `NoSuchElementException`
    werfen.
31. **Warum kann eine neue Anforderung Umbau erzwingen?** Eine Hierarchie beruht
    auf Annahmen über alle Subtypen. Widerlegt ein neuer Typ diese Annahmen –
    beim E-Book etwa die physische Ausleihbarkeit –, passt der Vertrag der
    Superklasse nicht mehr. Um Sonderfälle und Vertragsverletzungen zu
    vermeiden, muss die gemeinsame Abstraktion neu zugeschnitten werden.

## Struktur der finalen Lösung

```text
Medium
├── Buch
├── Zeitschrift
├── DVD
└── EBook

Bibliothek ── verwendet ──> MedienListe ── erweitert ──> AbstractList<Medium>
AusgelieheneMedienIterator ── implementiert ──> Iterator<Medium>
```
