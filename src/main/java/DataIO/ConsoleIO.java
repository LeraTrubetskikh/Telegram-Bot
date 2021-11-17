package DataIO;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Scanner;

public class ConsoleIO implements IDataIO {

    private Scanner scanner;
    public ConsoleIO(){
        scanner = new Scanner(System.in);
    }

    @Override
    public String read() {
        return scanner.nextLine();
    }

    @Override
    public void write(String message) {
        System.out.println(message);
    }

    @Override
    public void readUpdate(Message message) {}

    @Override
    public SendMessage getAnswer() {
        return null;
    }
}
