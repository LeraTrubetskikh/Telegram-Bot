package Questions;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class TaskStore {

    public HashMap<String, List<Question>> questions;
    public List<Description> descriptions;

    public TaskStore(){
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("src/main/resources/questions.json")) {
            Type type = new TypeToken<HashMap<String, List<Question>>>(){}.getType();
            questions = gson.fromJson(reader, type);
        }
        catch (Exception e) {
            System.out.println(e);
        }

        try (FileReader reader = new FileReader("src/main/resources/descriptions.json")) {
            Type type = new TypeToken<List<Question>>(){}.getType();
            descriptions = gson.fromJson(reader, type);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
