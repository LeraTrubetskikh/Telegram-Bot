import BotLogic.BotLogic;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class Program {
    public static void main(String[] args) throws TelegramApiRequestException {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        String token = "2090752243:AAH01lUcuDQpjRppT5PoB8zH2oMXNBg2ssg"; //для запуска бота вставить токен
        TelegramBot bot = new TelegramBot(token, "@matmex_geographer_bot", new BotLogic());
        botsApi.registerBot(bot);

//        ConsoleBot bot = new ConsoleBot(new BotLogic()); // для работы с консолью
//        bot.start();
    }
}