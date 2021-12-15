package Questions;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskGenerator {

    private static TaskStore taskStore;
    private Gson gson;
    private HashMap<String, List<Question>> questions;
    private List<Description> descriptions;
    private Boolean description;
    private ArrayList<Integer> permutation;
    public String region;

    public TaskGenerator() {
        taskStore = new TaskStore();
        questions = taskStore.questions;
        descriptions = taskStore.descriptions;
        permutation = new ArrayList<>();
        gson = new Gson();
        description = false;
        region = "";
    }

    public Question getQuestion(String region){
        if (!region.equals(this.region)) {
            this.region = region;
            setPermutation();
        }
        if (permutation.size() == 0) {
            this.region = "";
            return null;
        }
        var index = permutation.get(permutation.size() - 1);
        permutation.remove(permutation.size() - 1);
        return questions.get(region).get(index);
    }

    public Description getQuestion(){
        if (permutation.size() == 0) {
            description = false;
            return null;
        }
        var index = permutation.get(permutation.size() - 1);
        permutation.remove(permutation.size() - 1);
        return descriptions.get(index);
    }

    private void setPermutation() {
        permutation = new ArrayList<>();

        var to = 0;
        if (description)
            to = descriptions.size();
        else
            to = questions.get(region).size();

        for (int i = 0; i < to; i++)
            permutation.add(i);
        java.util.Collections.shuffle(permutation);
        permutation.subList(15, permutation.size()).clear();
    }
}
