package BotLogic;

import Users.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.HashMap;

public class BotLogic {

    public final RegionStore regionStore;
    private final HashMap<Long, User> users;
    private final NameTheCapitalGame nameTheCapitalGame;
    private final GuessTheCountryGame guessTheCountryGame;
    private Long userId;
    private JSONObject json;


    public BotLogic() {
        users = new HashMap<>();
        regionStore = new RegionStore();
        nameTheCapitalGame = new NameTheCapitalGame();
        guessTheCountryGame = new GuessTheCountryGame();

        try (FileReader reader = new FileReader("src/main/resources/commands.json")) {
            Object obj = new JSONParser().parse(reader);
            json = (JSONObject)obj;
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public BotMessage getNewMessage(BotMessage message){
        String text = message.getText();
        userId = message.getUserId();
        BotMessage newMessage;

        if (!users.containsKey(userId))
            users.put(userId, new User(userId));

        if (!text.isEmpty() && text.charAt(0) == '/')
            newMessage = responseToCommand(text);
        else if(users.get(userId).gameMode)
            newMessage = responseToMessageInGameMode(text);
        else
            newMessage = new BotMessage("Для начала введите /start", userId);

        newMessage.setGameMode(users.get(userId).gameMode);
        return newMessage;
    }

    private BotMessage responseToCommand(String text) {
        switch (text) {
            case ("/start"):
                return new BotMessage((String)json.get("start"), userId);
            case ("/help"):
                return new BotMessage((String)json.get("help"), userId);
            case ("/info"):
                return new BotMessage((String)json.get("info"), userId);
            case ("/stop"):
                if (users.get(userId).gameMode){
                    users.get(userId).finishTheGame();
                    return new BotMessage(String.format("Ваш счёт: %d", users.get(userId).resetScore()),
                            userId);
                }
                else {
                    return new BotMessage("Для начала введите /start", userId);
                }
            case ("/newgame"):
                users.get(userId).gameMode = true;
                var bm = new  BotMessage("Выберите игру:", "Назови столицу или угадай страну", userId);
                bm.setButtons(new String[]{"Назови столицу", "Угадай страну"});
                return bm;
            case ("/stat"):
                return new BotMessage(users.get(userId).getStat(), userId);
            default:
                return new BotMessage("Это не команда", userId);
        }
    }

    private BotMessage responseToMessageInGameMode(String msg) {
        User user = users.get(userId);
        if (user.isNameTheCapitalGame) {
            if (user.isRegionChosen) {
                return nameTheCapitalGame.responseToMessage(user, msg);
            } else {
                return nameTheCapitalGame.startNewGame(user, msg);
            }
        }
        else if (user.isGuessTheCountryGame) {
            return guessTheCountryGame.responseToMessage(user, msg);
        }
        else {
            switch (msg.toLowerCase()) {
                case ("назови столицу"):
                    user.isNameTheCapitalGame = true;
                    var bm = new BotMessage("Выберите регион:",
                            "Европа\nАзия\nАмерика\nАфрика\nАвстралия и Океания\nВсе регионы", userId);
                    bm.setButtons(regionStore.regions);
                    return bm;
                case ("угадай страну"):
                    user.isGuessTheCountryGame = true;
                    return guessTheCountryGame.startNewGame(user);
                default:
                    return new BotMessage("Такой игры нет :(", userId);
            }
        }
    }
}