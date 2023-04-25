package Tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class TaskGenerator {

    private final HashMap<String, List<Question>> questions;
    private final List<Description> descriptions;
    private final HashMap<Long, Iterator<Question>> tasksQuestion;
    private final HashMap<Long, Iterator<Description>> tasksDescription;


    public TaskGenerator() {
        TaskStore taskStore = new TaskStore();
        questions = taskStore.questions;
        descriptions = taskStore.descriptions;
        tasksDescription = new HashMap<>();
        tasksQuestion = new HashMap<>();
    }

    public void setQuestions(Long id, String region){
        var permutation = setPermutation(10, questions.get(region).size());
        var newTasks = new ArrayList<Question>();
        for (var i = 0; i < 10; i++)
            newTasks.add(questions.get(region).get(permutation.get(i)));
        tasksQuestion.put(id, newTasks.iterator());
    }

    public void setDescriptions(Long id){
        var permutation = setPermutation(10, descriptions.size());
        var newTasks = new ArrayList<Description>();
        for (var i = 0; i < 10; i++)
            newTasks.add(descriptions.get(permutation.get(i)));
        tasksDescription.put(id, newTasks.iterator());
    }

    public Question getQuestion(Long id) {
        if (tasksQuestion.get(id).hasNext())
            return tasksQuestion.get(id).next();
        return null;
    }

    public Description getDescription(Long id) {
        if (tasksDescription.get(id).hasNext())
            return tasksDescription.get(id).next();
        return null;
    }

    private ArrayList<Integer> setPermutation(int size, int to) {
        var permutation = new ArrayList<Integer>();
        for (int i = 0; i < to; i++)
            permutation.add(i);
        java.util.Collections.shuffle(permutation);
        permutation.subList(size, permutation.size()).clear();
        return permutation;
    }
}
