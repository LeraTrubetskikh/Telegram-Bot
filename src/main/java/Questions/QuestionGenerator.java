package Questions;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class QuestionGenerator {
    public Gson gson;
    public HashMap<String, List<Question>> questions;


    public QuestionGenerator() {
        gson = new Gson();
        questions = new HashMap<>();
        try (FileReader reader = new FileReader("src/main/resources/q.json")) {
            Type type = new TypeToken<HashMap<String, List<Question>>>(){}.getType();
            questions = gson.fromJson(reader, type);
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private int getRandomNumber(int to) {
        return (int)(Math.random() * to);
    }

    public Question getQuestion(String region){
        return questions.get(region).get(getRandomNumber(questions.get(region).size() - 1));
    }
}
