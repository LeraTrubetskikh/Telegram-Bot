package BotLogic;

import Questions.Question;
import Users.User;

import java.util.Arrays;
import java.util.HashMap;

public class BotLogic {

    public final RegionStore regionStore;
    private final HashMap<Long, User> users;
    private Long userId;
    private Question task;

    public BotLogic(){
        users = new HashMap<>();
        task = new Question();
        regionStore = new RegionStore();
    }

    public BotMessage getNewMessage(BotMessage message){
        String text = message.getText();
        userId = message.getUserId();

        if (!users.containsKey(userId))
            users.put(userId, new User(userId));
        else
            task = users.get(userId).lastQuestion;

        if (!text.isEmpty() && text.charAt(0) == '/')
            text = responseToCommand(text);
        else if(users.get(userId).gameMode)
            if (users.get(userId).isRegionChosen)
                text = responseToMessage(text);
            else
                text = responseToRegionChoice(text);
        else
            text = "Для начала введите /start";

        users.get(userId).setQuestion(task);
        BotMessage newMessage = new BotMessage(text, message.getUserId());
        newMessage.setGameMode(users.get(userId).gameMode);
        return newMessage;
    }

    private String responseToCommand(String text) {
        switch (text) {
            case ("/start"):
                return "Привет! Я бот для изучения столиц стран мира.\n" +
                        "Чтобы посмотреть список моих команд, введите /help";
            case ("/help"):
                return "/start - информация о боте,\n" +
                        "/newgame - начать новую игру,\n" +
                        "/stop - остановить игру\n" +
                        "/stat - статистика по всем регионам";
            case ("/stop"):
                if (users.get(userId).gameMode){
                    users.get(userId).gameMode = false;
                    users.get(userId).isRegionChosen = false;
                    var score = users.get(userId).getScore();
                    users.get(userId).taskGenerator.region = "";
                    users.get(userId).resetScore();
                    return String.format("Ваш счёт: %d", score);
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
        if (msg.equalsIgnoreCase(task.getAnswer())) {
            task = users.get(userId).taskGenerator.getQuestion(users.get(userId).region);
            users.get(userId).addPoints();
            if (task == null) {
                var score = users.get(userId).getScore();
                users.get(userId).finishTheGame();
                return String.format("Правильно!\nВаш счёт: %d", score);
            }
            return "Правильно!" + "\n" + task.getTask();
        } else {
            task = users.get(userId).taskGenerator.getQuestion(users.get(userId).region);
            if (task == null) {
                var score = users.get(userId).getScore();
                users.get(userId).finishTheGame();
                return String.format("Неправильно!\nВаш счёт: %d", score);
            }
            return "Неправильно!" + "\n" + task.getTask();
        }
    }

    private String responseToRegionChoice(String region){
        String capitalizedRegion = Character.toUpperCase(region.charAt(0)) + region.substring(1).toLowerCase();
        if (Arrays.asList(regionStore.regions).contains(capitalizedRegion)){
            users.get(userId).isRegionChosen = true;
            users.get(userId).region = capitalizedRegion;
            task = users.get(userId).taskGenerator.getQuestion(capitalizedRegion);
            return task.getTask();
        }
        else {
            return "Нет такого региона!";
        }
    }
}
