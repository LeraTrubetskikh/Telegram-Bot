package BotLogic;

import Questions.Question;
import Questions.QuestionGenerator;
import Users.User;

import java.util.Arrays;
import java.util.HashMap;

public class BotLogic {

    private BotMessage message;
    private final HashMap<Long, User> users;
    private Long userId;
    private final QuestionGenerator questionGenerator;
    private Question question;
    private final String[] regions;

    public BotLogic(){
        message = new BotMessage("", 0L);
        users = new HashMap<>();
        questionGenerator = new QuestionGenerator();
        question = new Question();
        regions = new String[]{"Европа", "Азия", "Америка", "Африка", "Австралия и Океания"};
    }

    public void readUpdate(BotMessage message) {
        this.message = message; // а этот метод вообще нужен? типа можно же просто сразу передовать это сообщение в getanswer
    }

    public BotMessage getAnswer(){
        String text = message.getText();
        userId = message.getUserId();

        if (!users.containsKey(userId)) {
            users.put(userId, new User(userId));
        } else {
            question = users.get(userId).lastQuestion;
        }

        if (!text.isEmpty() && text.charAt(0) == '/') {
            text = responseToCommand(text);
        }
        else if(users.get(userId).gameMode){
            text = responseToMessage(text);
        }
        else {
            text = "Для начала введите /start";
        }
        users.get(userId).setQuestion(question);
        BotMessage newMessage = new BotMessage("", 0L);
        newMessage.setUserId(message.getUserId());
        newMessage.setText(text);
        return newMessage;
    }

    private String responseToCommand(String text) {
        switch (text) {
            case ("/start"):
                return "Привет! Я бот для изучения столиц стран мира.\n" +
                        "Чтобы посмотреть список моих комманд, введите /help";
            case ("/help"):
                return "/start - информация о боте,\n" +
                        "/newgame - начать новую игру,\n" +
                        "/stop - остановить игру\n" +
                        "/stat - статистика по всем регионам";
            case ("/stop"):
                users.get(userId).gameMode = false;
                return String.format("Ваш счёт: %d", users.get(userId).getScore());
            case ("/newgame"):
                users.get(userId).gameMode = true;
                return "Выберите регион: \nЕвропа,\nАзия,\nАмерика,\nАфрика,\nАвстралия и Океания";
                // тут все вылетает когда неправильно вводишь название региона
            case ("/stat"):
                return users.get(userId).getStat();
            default:
                return "Это не команда";
        }
    }

    private String responseToMessage(String msg){
        if (Arrays.asList(regions).contains(msg)){
            users.get(userId).resetScore();
            users.get(userId).region = msg;
            questionGenerator.getPermutation(msg);
            question = questionGenerator.getQuestion(msg);
            return question.getQuestion();
        }
        if (msg.equals(question.getAnswer())) {
            question = questionGenerator.getQuestion(users.get(userId).region);
            users.get(userId).addPoints();
            users.get(userId).updateScore();
            return "Правильно!" + "\n" + question.getQuestion();
        } else {
            question = questionGenerator.getQuestion(users.get(userId).region);
            return "Неправильно!" + "\n" + question.getQuestion();
        }
    }
}
