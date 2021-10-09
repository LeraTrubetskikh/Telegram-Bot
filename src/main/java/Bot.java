import Questions.Question;
import Questions.QuestionGenerator;

public class Bot {

    private Scan scanner;
    private boolean runTheProgramFlag;
    private QuestionGenerator questionGenerator;

    public Bot() {
        scanner = new Scan();
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
        Question question = new Question("", "");

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
            scanner.printHelp();
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
        return scanner.getLine();
    }

    public void sendMessage(String str) {
        scanner.printLine(str);
    }
}