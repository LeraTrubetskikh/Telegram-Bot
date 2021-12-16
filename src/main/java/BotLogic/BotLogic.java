package BotLogic;

import Users.User;
import java.util.HashMap;

public class BotLogic {

    public final RegionStore regionStore;
    private final HashMap<Long, User> users;
    private final NameTheCapitalGame nameTheCapitalGame;
    private final GuessTheCountryGame guessTheCountryGame;
    private Long userId;

    public BotLogic(){
        users = new HashMap<>();
        regionStore = new RegionStore();
        nameTheCapitalGame = new NameTheCapitalGame();
        guessTheCountryGame = new GuessTheCountryGame();
    }

    public BotMessage getNewMessage(BotMessage message){
        String text = message.getText();
        userId = message.getUserId();

        if (!users.containsKey(userId))
            users.put(userId, new User(userId));

        if (!text.isEmpty() && text.charAt(0) == '/')
            text = responseToCommand(text);
        else if(users.get(userId).gameMode)
            text = responseToMessageInGameMode(text);
        else
            text = "Для начала введите /start";

        BotMessage newMessage = new BotMessage(text, message.getUserId());
        newMessage.setGameMode(users.get(userId).gameMode);
        return newMessage;
    }

    private String responseToCommand(String text) {
        switch (text) {
            case ("/start"):
                return "Привет! Я бот-географ.\n" +
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
                    users.get(userId).isNameTheCapitalGame = false;
                    users.get(userId).isGuessTheCountryGame = false;
                    var score = users.get(userId).getScore();
                    users.get(userId).taskGenerator.region = "";
                    users.get(userId).resetScore();
                    return String.format("Ваш счёт: %d", score);
                }
                else {
                    return "Для начала введите /start";
                }
            case ("/newgame"):
                users.get(userId).gameMode = true;
                return "Выберите игру: \nНазови столицу\nУгадай страну";
            case ("/stat"):
                return users.get(userId).getStat();
            default:
                return "Это не команда";
        }
    }

    private String responseToMessageInGameMode(String msg) {
        User user = users.get(userId);
        if (user.isNameTheCapitalGame) {
            if (user.isRegionChosen) {
                return nameTheCapitalGame.responseToMessage(user, msg);
            } else {
                return nameTheCapitalGame.responseToRegionChoice(user, msg);
            }
        }
        else if (user.isGuessTheCountryGame) {
            return guessTheCountryGame.responseToMessage(user, msg);
        }
        else {
            switch (msg.toLowerCase()) {
                case ("назови столицу"):
                    user.isNameTheCapitalGame = true;
                    return "Выберите регион: \nЕвропа\nАзия\nАмерика\nАфрика\nАвстралия и Океания";
                case ("угадай страну"):
                    user.isGuessTheCountryGame = true;
                    var task = user.taskGenerator.getDescription();
                    user.setTask(task);
                    return task.getTask();
                default:
                    return "Такой игры нет :(";
            }
        }
    }
}