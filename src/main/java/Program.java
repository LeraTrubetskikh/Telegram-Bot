import DataIO.ConsoleIO;

public class Program {
    public static void main(String[] args) {
        Bot bot = new Bot(new ConsoleIO());
        bot.start();
    }
}