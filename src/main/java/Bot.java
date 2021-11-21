import DataIO.IDataIO;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

    private final String token;
    private final String username;
    private final IDataIO dataIO;

    public Bot(String token, String username, IDataIO dataIO) {
        this.token = token;
        this.username = username;
        this.dataIO = dataIO;
    }

    public Bot(IDataIO dataIO){
        this.token = null;
        this.username = null;
        this.dataIO = dataIO;
            start();
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                Message inMessage = update.getMessage();
                dataIO.readUpdate(inMessage);

                SendMessage outMessage = dataIO.getAnswerMessage();
                execute(outMessage);
            }
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    public void start() {
        dataIO.write("Введите /help");
        while(true) {
            dataIO.read();
            String inputMessage = dataIO.getAnswer();
            dataIO.write(inputMessage);
        }
    }
}