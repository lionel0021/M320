
public class Responder
{

    public String generateResponse(String input)
    {
        if(input.contains ("PC") ){
            return "Oh sieht nach einem PC Problem aus...";
        }

        if(input.contains ("Hilfe") ){
            return "Haben sie versucht den PC aus und wieder an zu schalten?";
        }

        if(input.contains ("Drucker") ){
            return "Haben sie dem Drucker alles gegeben was er braucht?";
        }

        if(input.contains ("") ){
            return "Sie müssen mir schon eine Frage stellen das ich ihnen helfen kann :(";
        }

        if(input.contains ("bug") ){
            return "Haben sie versucht das Prpogramm neu zu installieren?";
        }

        if(input.contains ("dankeschön") ){
            return "Kein Problem, ich helfe gerne :)";
        }

        if(input.contains ("anrufen") ){
            return "Tut mir leid wir sind nicht per Telefon erreichbar";
        }

        if(input.contains ("internet") ){
            return "Haben sie schon versucht ihren Router neu zu starten?";
        }

        if(input.contains ("Bildschirm") ){
            return "Vielleicht haben sie ihn nicht eingesteckt.";
        }

        else
            return "Das Problem ist nicht lösbar schmeissen sie den PC weg";

    }
}
