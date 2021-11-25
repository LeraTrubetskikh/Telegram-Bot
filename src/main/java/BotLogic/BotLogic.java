package BotLogic;

import Questions.Question;
import Questions.QuestionGenerator;
import Users.User;

import java.util.HashMap;

public class BotLogic {

    private BotMessage message;
    private final HashMap<Long, User> users;
    private final QuestionGenerator questionGenerator;
    private Question question;

    public BotLogic(){
        message = new BotMessage("", 0L);
        users = new HashMap<>();
        questionGenerator = new QuestionGenerator();
        question = new Question("", "");
    }

    public void readUpdate(BotMessage message) {
        this.message = message;
    }

    public BotMessage getAnswer(){
        String text = message.getText();
        Long id = message.getUserId();

        if (!users.containsKey(id)) {
            users.put(id, new User(id));
        } else {
            question = users.get(id).lastQuestion;
        }

        if (!text.isEmpty() && text.charAt(0) == '/') {
            text = responseToCommand(text);
        }
        else if(users.get(id).gameMode){
            text = responseToMessage(text);
        }
        else {
            text = "Для начала введите /start";
        }
        users.get(id).setQuestion(question);
        BotMessage newMessage = new BotMessage("", 0L);
        newMessage.setUserId(message.getUserId());
        newMessage.setText(text);
        return newMessage;
    }

    private String responseToCommand(String text) {
        var outputMsg = "";
        switch (text) {
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
                users.get(message.getUserId()).gameMode = false;
                break;
            case ("/newgame"):
                users.get(message.getUserId()).gameMode = true;
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
}
