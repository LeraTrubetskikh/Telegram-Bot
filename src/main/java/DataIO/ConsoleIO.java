package DataIO;

import Questions.Question;
import Questions.QuestionGenerator;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Scanner;

public class ConsoleIO implements IDataIO {

    private final Scanner scanner;
    private String message;
    private final QuestionGenerator questionGenerator;
    private Question question;

    public ConsoleIO(){
        scanner = new Scanner(System.in);
        questionGenerator = new QuestionGenerator();
        question = new Question("","");
    }

    @Override
    public void read() {
        message = scanner.nextLine();
    }

    @Override
    public void write(String message) {
        System.out.println(message);
    }

    @Override
    public String getAnswer(){
        if (message.charAt(0) == '/'){
            return responseToCommand(message);
        } else{
            return responseToMessage(message);
        }
    }

    private String responseToCommand(String message){
        var outputMsg = "";
        switch (message) {
            case ("/help"):
                outputMsg = "Доступные команды: /help просмотр информации о ботe\n" +
                        "/newgame начать новую игру\n" +
                        "/stop закончить игру";
                break;
            case ("/stop"):
                break;
            case ("/newgame"):
                question = questionGenerator.getQuestion();
                outputMsg = question.question;
                break;
            default:
                outputMsg = "Это не команда";
                break;
        }
        return outputMsg;
    }

    private String responseToMessage(String message){
        if (message.equals(question.answer)) {
            question = questionGenerator.getQuestion();
            return "Правильно!" + "\n" + question.question;
        } else {
            question = questionGenerator.getQuestion();
            return "Неправильно!" + "\n" + question.question;
        }
    }

    @Override
    public void readUpdate(Message message) {}

    @Override
    public SendMessage getAnswerMessage() {
        return null;
    }
}
