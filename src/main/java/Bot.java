import DataIO.IDataIO;
import Questions.Question;
import Questions.QuestionGenerator;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

    private final String token;
    private final String username;
    private final IDataIO dataIO;
    private boolean runTheProgramFlag;
    private QuestionGenerator questionGenerator;

    public Bot(String token, String username, IDataIO dataIO) {
        this.token = token;
        this.username = username;
        this.dataIO = dataIO;
        questionGenerator = new QuestionGenerator();
    }

    public Bot(IDataIO dataIO, boolean consoleFlag){
        this.token = null;
        this.username = null;
        this.dataIO = dataIO;
        this.runTheProgramFlag = consoleFlag;
        if (runTheProgramFlag)
            start();
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                Message inMessage = update.getMessage();

                dataIO.readUpdate(inMessage.getText());
                String answer = dataIO.getAnswer();

                SendMessage outMessage = new SendMessage();
                outMessage.setChatId(inMessage.getChatId().toString());

                if (answer.contains(":")) {
                    String[] list = answer.split(":"); // строка - Правильно/Неправильно:Следующий вопрос
                    for (String value : list) {
                        outMessage.setText(value);
                        execute(outMessage);
                    }
                }
                else if (answer.isEmpty()){
                    outMessage.setText("");
                }
                else {
                    outMessage.setText(answer);
                    execute(outMessage);
                }
            }
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public String getBotUsername() {
        return username;
    }


    public void start() {
        sendMessage("Введите /help");
        while(runTheProgramFlag) {
            checkCommand(getMessage());
        }
    }

    public void startNewGame() {
        String message;
        Question question;

        while(runTheProgramFlag) {
            question = questionGenerator.getQuestion();
            sendMessage(question.question);
            message = getMessage();
            if(!checkCommand(message)) {
                if (message.equals(question.answer))
                    sendMessage("Правильно!");
                else
                    sendMessage("Не правильно!");
            }
        }
    }

    public boolean checkCommand(String str){
        if ("/help".equals(str)) {
            dataIO.write("Доступные команды: /help просмотр информации о ботe\n" +
                    "/newgame начать новую игру\n" +
                    "/stop закончить игру");
            return true;
        } else if ("/newgame".equals(str)) {
            startNewGame();
            return true;
        } else if ("/stop".equals(str)) {
            runTheProgramFlag = false;
            return true;
        }
        return false;
    }

    public String getMessage() {
        return dataIO.read();
    }

    public void sendMessage(String str) {
        dataIO.write(str);
    }
}