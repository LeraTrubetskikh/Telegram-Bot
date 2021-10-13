import Questions.Question;
import Questions.QuestionGenerator;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

    private QuestionGenerator questionGenerator;
    private String token;
    private String username;
    private boolean firstQuestionFlag = true;
    private Question question;

    public Bot(String token, String username) {
        this.token = token;
        this.username = username;
        questionGenerator = new QuestionGenerator();
    }

    public String getNewMessage(String message) {
        if (firstQuestionFlag) {
            question = questionGenerator.getQuestion();
            firstQuestionFlag = false;
            return question.question;
        }
        else {
            if (message.equals(question.answer)) {
                question = questionGenerator.getQuestion();
                return "Правильно!" + ":" + question.question;
            }
            else {
                question = questionGenerator.getQuestion();
                return "Неправильно!" + ":" + question.question;
            }
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                Message inMessage = update.getMessage();

                String s = getNewMessage(inMessage.getText());
                String[] list = s.split(":"); // строка - Правильно/Неправильно:Следующий вопрос
                for (String value : list) {
                    SendMessage outMessage = new SendMessage();
                    outMessage.setChatId(inMessage.getChatId().toString());
                    outMessage.setText(value);
                    execute(outMessage);
                }
            }
        } catch (TelegramApiException e) {
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

}