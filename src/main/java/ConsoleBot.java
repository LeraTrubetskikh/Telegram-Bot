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
        BotMessage message = new BotMessage("", 0L);
        while(true){
            message.setText(scanner.nextLine());
            botLogic.readUpdate(message);
            message = botLogic.getAnswer();
            System.out.println(message.getText());
        }
    }
}
