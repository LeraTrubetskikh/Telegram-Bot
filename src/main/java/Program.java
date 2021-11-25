import BotLogic.BotLogic;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class Program {
    public static void main(String[] args) throws TelegramApiRequestException {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        TelegramBot bot = new TelegramBot("2090752243:AAF__7tTqJFtwc59sukdjAw0G6377l431Ko",
                "@matmex_geographer_bot", new BotLogic());
        botsApi.registerBot(bot);

//        ConsoleBot bot = new ConsoleBot(new BotLogic()); // для работы с консолью
//        bot.start();
    }
}