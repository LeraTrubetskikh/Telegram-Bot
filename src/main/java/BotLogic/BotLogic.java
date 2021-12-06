package BotLogic;

import Questions.Question;
import Questions.QuestionGenerator;
import Users.User;

import java.util.Arrays;
import java.util.HashMap;

public class BotLogic {

    public final String[] regions;
    private final HashMap<Long, User> users;
    private Long userId;
    private final QuestionGenerator questionGenerator;
    private Question question;

    public BotLogic(){
        users = new HashMap<>();
        questionGenerator = new QuestionGenerator();
        question = new Question();
        regions = new String[]{"Европа", "Азия", "Америка", "Африка", "Австралия и Океания"};
    }

    public BotMessage handler(BotMessage message){
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
            if (users.get(userId).isRegionChosen){
                text = responseToMessage(text);
            }
            else{
                text = responseToRegionChoice(text);
            }
        }
        else {
            text = "Для начала введите /start";
        }
        users.get(userId).setQuestion(question);
        BotMessage newMessage = new BotMessage(text, message.getUserId());
        newMessage.setGameMode(users.get(userId).gameMode);
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
                if (users.get(userId).gameMode){
                    users.get(userId).gameMode = false;
                    users.get(userId).isRegionChosen = false;
                    return String.format("Ваш счёт: %d", users.get(userId).getScore());
                }
                else {
                    return "Для начала введите /start";
                }
            case ("/newgame"):{
                users.get(userId).gameMode = true;
                return "Выберите регион: \nЕвропа,\nАзия,\nАмерика,\nАфрика,\nАвстралия и Океания";}
            case ("/stat"):
                return users.get(userId).getStat();
            default:
                return "Это не команда";
        }
    }

    private String responseToMessage(String msg){
        if (msg.equalsIgnoreCase(question.getAnswer())) {
            question = questionGenerator.getQuestion(users.get(userId).region);
            if (question == null)
                return responseToCommand("/stop");
            users.get(userId).addPoints();
            users.get(userId).updateScore();
            return "Правильно!" + "\n" + question.getQuestion();
        } else {
            question = questionGenerator.getQuestion(users.get(userId).region);
            if (question == null)
                return responseToCommand("/stop");
            return "Неправильно!" + "\n" + question.getQuestion();
        }
    }

    private String responseToRegionChoice(String region){
        String capitalizedRegion = Character.toUpperCase(region.charAt(0)) + region.substring(1).toLowerCase();
        if (Arrays.asList(regions).contains(capitalizedRegion)){
            users.get(userId).isRegionChosen = true;
            users.get(userId).resetScore();
            users.get(userId).region = capitalizedRegion;
            question = questionGenerator.getQuestion(capitalizedRegion);
            return question.getQuestion();
        }
        else {
            return "Нет такого региона!";
        }
    }
}
