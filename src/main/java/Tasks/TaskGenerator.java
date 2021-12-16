package Tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskGenerator {

    private final HashMap<String, List<Question>> questions;
    private final List<Description> descriptions;
    private Boolean isDescription;
    private ArrayList<Integer> permutation;
    public String region;

    public TaskGenerator() {
        TaskStore taskStore = new TaskStore();
        questions = taskStore.questions;
        descriptions = taskStore.descriptions;
        permutation = new ArrayList<>();
        isDescription = false;
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

    public Description getDescription(){
        isDescription = true;
        setPermutation();
        if (permutation.size() == 0) {
            isDescription = false;
            return null;
        }
        var index = permutation.get(permutation.size() - 1);
        permutation.remove(permutation.size() - 1);
        return descriptions.get(index);
    }

    private void setPermutation() {
        permutation = new ArrayList<>();

        var to = 0;
        if (isDescription)
            to = descriptions.size();
        else
            to = questions.get(region).size();

        for (int i = 0; i < to; i++)
            permutation.add(i);
        java.util.Collections.shuffle(permutation);
        permutation.subList(10, permutation.size()).clear();
    }
}
