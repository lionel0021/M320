public class SupportSystem
{
    private InputReader reader;
    private Responder responder;


    public SupportSystem()
    {
        reader = new InputReader();
        responder = new Responder();
    }


    public void start()
    {
        boolean finished = false;

        printWelcome();

        while(!finished) {
            String input = reader.getInput();

            if(input.startsWith("exit")) {
                finished = true;
            }
            else {
                String response = responder.generateResponse(input);
                System.out.println(response);
            }
        }
        printGoodbye();
    }

    public void printWelcome()
    {
        System.out.println("Willkommen zum IT Support");
        System.out.println();
        System.out.println("Bitte lassen sie uns wissen was ihr Problem ist");
        System.out.println("Wir werden ihnen versuchen so gut wie es geht zu helfen");
        System.out.println("Bitte schreiben sie 'exit' um den IT SUpport zu beenden");
    }

    public void printGoodbye()
    {
        System.out.println("Ich hoffe ich konnte helfen :) Tschüss...");
    }
}
