package DataIO;

import Questions.Question;
import Questions.QuestionGenerator;
import Users.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import java.util.HashMap;


public class TelegramIO implements IDataIO {
    private Message message;
    private final HashMap<Long, User> users;
    private final QuestionGenerator questionGenerator;
    private Question question;
    private boolean gameMode;

    public TelegramIO(){
        questionGenerator = new QuestionGenerator();
        users = new HashMap<>();
        question = new Question("","");
        gameMode = false;
    }

    @Override
    public void readUpdate(Message message) {
        this.message = message;
    }

    @Override
    public SendMessage getAnswerMessage() {
        String text = message.getText();
        Long id = message.getChatId();

        if (!users.containsKey(id)) {
            users.put(id, new User(id));
        } else {
            question = users.get(id).lastQuestion;
        }

        if (text.charAt(0) == '/') {
            text = responseToCommand(text);
        }
        else if (gameMode){
            text = responseToMessage(text);
        }
        else {
            text = "Для начала введите /start";
        }
        users.get(id).setQuestion(question);
        SendMessage newMessage = new SendMessage();
        newMessage.setChatId(message.getChatId().toString());
        newMessage.setText(text);
        return newMessage;
    }


    private String responseToCommand(String message) {
        var outputMsg = "";
        switch (message) {
            case ("/start"):
                outputMsg = "Привет! Я бот для изучения столиц стран мира.\n" +
                        "Чтобы посмотреть список моих комманд, введите /help";
                break;
            case ("/help"):
                outputMsg = "/start - информация о боте,\n" +
                        "/newgame - начать новую игру,\n" +
                        "/stop - остановить игру";
                break;
            case ("/stop"):
                gameMode = false;
                break;
            case ("/newgame"):
                gameMode = true;
                question = questionGenerator.getQuestion();
                outputMsg = question.question;
                break;
            default:
                outputMsg = "Это не команда";
                break;
        }
        return outputMsg;
    }

    private String responseToMessage(String msg){
        if (msg.equals(question.answer)) {
            question = questionGenerator.getQuestion();
            return "Правильно!" + "\n" + question.question;
        } else {
            question = questionGenerator.getQuestion();
            return "Неправильно!" + "\n" + question.question;
        }
    }

    @Override
    public void read() {
    }

    @Override
    public void write(String message) {
    }

    @Override
    public String getAnswer(){
        return null;
    }
}
