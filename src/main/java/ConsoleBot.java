import BotLogic.BotLogic;
import BotLogic.BotMessage;
import java.util.Scanner;

public class ConsoleBot {

    private final BotLogic botLogic;
    private final Scanner scanner;

    public ConsoleBot(BotLogic botLogic){
        this.botLogic = botLogic;
        scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Для начала введите /start");
        BotMessage inMessage = new BotMessage("", 0L);
        while(true){
            inMessage.setText(scanner.nextLine());
            BotMessage outMessage = botLogic.getNewMessage(inMessage);
            System.out.println(outMessage.getText());
            if(outMessage.multipleMessages)
                System.out.println(outMessage.getText2());
        }
    }
}
