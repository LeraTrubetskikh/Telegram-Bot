package Questions;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestionGenerator {
    private Gson gson;
    private HashMap<String, List<Question>> questions;
    private ArrayList<Integer> permutation;
    private String region;


    public QuestionGenerator() {
        gson = new Gson();
        questions = new HashMap<>();
        permutation = new ArrayList<>();
        region = "";
        try (FileReader reader = new FileReader("src/main/resources/q.json")) {
            Type type = new TypeToken<HashMap<String, List<Question>>>(){}.getType();
            questions = gson.fromJson(reader, type);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    private void setPermutation() {
        permutation = new ArrayList<>();
        for (int i = 0; i < 15; i++)
            permutation.add(i);
        java.util.Collections.shuffle(permutation);
    }

    public Question getQuestion(String region){
        if (!region.equals(this.region)) {
            this.region = region;
            setPermutation();
        }
        if (permutation.size() == 0)
            return null;
        var index = permutation.get(permutation.size() - 1);
        permutation.remove(permutation.size() - 1);
        return questions.get(region).get(index);
    }
}
