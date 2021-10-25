import DataIO.IDataIO;
import Questions.Question;
import Questions.QuestionGenerator;

public class Bot {

    private IDataIO dataIO;
    private boolean runTheProgramFlag;
    private QuestionGenerator questionGenerator;

    public Bot(IDataIO dataIO) {
        this.dataIO = dataIO;
        runTheProgramFlag = true;
        questionGenerator = new QuestionGenerator();
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