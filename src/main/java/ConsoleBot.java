import BotLogic.BotLogic;
import BotLogic.BotMessage;
import java.util.Objects;
import java.util.Scanner;

public class ConsoleBot {

    private final BotLogic botLogic;
    private final Scanner scanner;
    private Boolean flag;

    public ConsoleBot(BotLogic botLogic){
        this.botLogic = botLogic;
        scanner = new Scanner(System.in);
        flag = true;
    }

    public void start() {
        System.out.println("Для начала введите /start");
        BotMessage message = new BotMessage("", 0L);
        while(flag){
            message.setText(scanner.nextLine());
            botLogic.readUpdate(message);
            message = botLogic.getAnswer();
            if (!Objects.equals(message.getText(), ""))
                System.out.println(message.getText());
            else
                flag = false;
        }
    }
}
