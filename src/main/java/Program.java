import DataIO.TelegramIO;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class Program {
    public static void main(String[] args) throws TelegramApiRequestException {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        botsApi.registerBot(new Bot("2090752243:AAF__7tTqJFtwc59sukdjAw0G6377l431Ko",
                "@matmex_geographer_bot", new TelegramIO(), false));

        //Bot bot = new Bot("", "", new ConsoleIO(), true); // для работы с консолью
    }
}