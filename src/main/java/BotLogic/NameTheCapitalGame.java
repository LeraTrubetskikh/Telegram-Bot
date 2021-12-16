package BotLogic;

import Tasks.IGameTask;
import Tasks.Question;
import Tasks.TaskGenerator;
import Users.User;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

public class NameTheCapitalGame {
    private IGameTask task;
    private final RegionStore regionStore;
    private final TaskGenerator taskGenerator;

    public NameTheCapitalGame(){
        taskGenerator = new TaskGenerator();
        task = new Question();
        regionStore = new RegionStore();
    }

    public String responseToMessage(User user, String msg){
        task = user.lastTask;
        if (msg.equalsIgnoreCase(task.getAnswer())) {
            task = taskGenerator.getQuestion(user.getID());
            user.setTask(task);
            user.addPoints();
            if (task == null) {
                user.finishTheGame();
                return String.format("Правильно!\nВаш счёт: %d/10", user.resetScore());
            }
            return "Правильно!" + "\n" + task.getTask();
        } else {
            task = taskGenerator.getQuestion(user.getID());
            user.setTask(task);
            if (task == null) {
                user.finishTheGame();
                return String.format("Неправильно!\nВаш счёт: %d/10", user.resetScore());
            }
            return "Неправильно!" + "\n" + task.getTask();
        }
    }

    public String startNewGame(User user, String region){
        String capitalizedRegion = Character.toUpperCase(region.charAt(0)) + region.substring(1).toLowerCase();
        if (Arrays.asList(regionStore.regions).contains(capitalizedRegion)){
            user.isRegionChosen = true;
            user.setScoreRegion(capitalizedRegion);
            taskGenerator.setQuestions(user.getID(), capitalizedRegion);
            task = taskGenerator.getQuestion(user.getID());
            user.setTask(task);
            return task.getTask();
        }
        else {
            return "Нет такого региона!";
        }
    }
}
