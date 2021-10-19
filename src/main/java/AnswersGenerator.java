import Questions.Question;
import Questions.QuestionGenerator;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class AnswersGenerator {
    private QuestionGenerator questionGenerator;
    private Question question;
    private HashMap<String,String> answers;

    public AnswersGenerator(){
        questionGenerator = new QuestionGenerator();
        answers = new HashMap<>();
        getAnswer();
    }

    public String responseToCommand(String message) {
        var msg = message.split("/")[1];
        var outputMsg = "";
        switch (msg) {
            case ("start"):
                outputMsg = answers.get("start");
                break;
            case ("help"):
                outputMsg = answers.get("help");
                break;
            case ("stop"):
                break;
            case ("newgame"):
                question = questionGenerator.getQuestion();
                outputMsg = question.question;
                break;
            default:
                outputMsg = "Это не команда!";
                break;
        }
        return outputMsg;
    }

    public String responseToMessage(String message){
        if (message.equals(question.answer)) {
            question = questionGenerator.getQuestion();
            return "Правильно!" + ":" + question.question;
        } else {
            question = questionGenerator.getQuestion();
            return "Неправильно!" + ":" + question.question;
        }
    }

    private void getAnswer() {
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
