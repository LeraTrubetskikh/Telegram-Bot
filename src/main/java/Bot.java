import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {


    private final String token;
    private final String username;
    private final AnswersGenerator answersGenerator;


    public Bot(String token, String username) {
        this.token = token;
        this.username = username;
        answersGenerator = new AnswersGenerator();
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                Message inMessage = update.getMessage();

                String answer = getNewAnswer(inMessage.getText());
                SendMessage outMessage = new SendMessage();
                outMessage.setChatId(inMessage.getChatId().toString());

                if (answer.contains(":")) {
                    String[] list = answer.split(":"); // строка - Правильно/Неправильно:Следующий вопрос
                    for (String value : list) {
                        outMessage.setText(value);
                        execute(outMessage);
                    }
                }
                else if (answer.isEmpty()){
                    outMessage.setText("");
                }
                else {
                    outMessage.setText(answer);
                    execute(outMessage);
                }
            }
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String getNewAnswer(String message) {
        if (message.charAt(0) == '/') {
            return answersGenerator.responseToCommand(message);
        }
        else {
            return answersGenerator.responseToMessage(message);
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

}