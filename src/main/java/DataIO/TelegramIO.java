package DataIO;

import Questions.Question;
import Questions.QuestionGenerator;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class TelegramIO implements IDataIO {
    private String message;
    private final QuestionGenerator questionGenerator;
    private Question question;
    private final HashMap<String,String> answers;

    public TelegramIO(){
        questionGenerator = new QuestionGenerator();
        answers = new HashMap<>();
        getAnswers();
    }

    @Override
    public String read() {
        return null;
    }

    @Override
    public void write(String str){}

    @Override
    public void readUpdate(String message){
        this.message = message;
    }

    @Override
    public String getAnswer() {
        if (message.charAt(0) == '/') {
            message = responseToCommand(message);
        } else {
            message = responseToMessage(message);
        }
        return message;
    }


    public String responseToCommand(String message) {
        var outputMsg = "";
        switch (message) {
            case ("/start"):
                outputMsg = answers.get("start");
                break;
            case ("/help"):
                outputMsg = answers.get("help");
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

    public String responseToMessage(String msg){
        if (msg.equals(question.answer)) {
            question = questionGenerator.getQuestion();
            return "Правильно!" + ":" + question.question;
        } else {
            question = questionGenerator.getQuestion();
            return "Неправильно!" + ":" + question.question;
        }
    }

    private void getAnswers() {
        try {
            FileReader fr = new FileReader("src/main/resources/answers.txt");
            Scanner scan = new Scanner(fr);

            String[] s;
            while(scan.hasNextLine()){
                s = scan.nextLine().split(":");
                answers.put(s[0], s[1]);
            }
            fr.close();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
