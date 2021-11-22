package Questions;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class QuestionGenerator {

    private final ArrayList<Question> questions;

    public QuestionGenerator() {
        questions = new ArrayList<>();
        getQuestions();
    }

    private int getRandomNumber(int to) {
        return (int)(Math.random() * to);
    }

    public Question getQuestion(){
        return questions.get(getRandomNumber(questions.size() - 1));
    }

    private void getQuestions() {
        try {
            FileReader fr = new FileReader("src/main/resources/questions.txt");
            Scanner scan = new Scanner(fr);

            String[] s;
            while(scan.hasNextLine()){
                s = scan.nextLine().split(":");
                questions.add(new Question(s[0], s[1]));
            }
            fr.close();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
