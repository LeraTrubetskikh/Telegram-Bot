package Tasks;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskStore {

    public HashMap<String, List<Question>> questions;
    public List<Description> descriptions;

    public TaskStore(){
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("src/main/resources/questions.json")) {
            Type type = new TypeToken<HashMap<String, List<Question>>>(){}.getType();
            questions = gson.fromJson(reader, type);
            questions.put("Все регионы", new ArrayList<>());
            for(Map.Entry<String, List<Question>> entry: questions.entrySet())
                questions.get("Все регионы").addAll(entry.getValue());
        }
        catch (Exception e) {
            System.out.println(e);
        }

        try (FileReader reader = new FileReader("src/main/resources/descriptions.json")) {
            Type type = new TypeToken<List<Description>>(){}.getType();
            descriptions = gson.fromJson(reader, type);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
