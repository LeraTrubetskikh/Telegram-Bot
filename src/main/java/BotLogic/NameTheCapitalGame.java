package BotLogic;

import Tasks.IGameTask;
import Tasks.Question;
import Users.User;
import java.util.Arrays;

public class NameTheCapitalGame {
    private IGameTask task;
    private final RegionStore regionStore;

    public NameTheCapitalGame(){
        task = new Question();
        regionStore = new RegionStore();
    }

    public String responseToMessage(User user, String msg){
        task = user.lastTask;
        if (msg.equalsIgnoreCase(task.getAnswer())) {
            task = user.taskGenerator.getQuestion(user.region);
            user.setTask(task);
            user.addPoints();
            if (task == null) {
                var score = user.getScore();
                user.finishTheGame();
                return String.format("Правильно!\nВаш счёт: %d/10", score);
            }
            return "Правильно!" + "\n" + task.getTask();
        } else {
            task = user.taskGenerator.getQuestion(user.region);
            user.setTask(task);
            if (task == null) {
                var score = user.getScore();
                user.finishTheGame();
                return String.format("Неправильно!\nВаш счёт: %d/10", score);
            }
            return "Неправильно!" + "\n" + task.getTask();
        }
    }

    public String responseToRegionChoice(User user, String region){
        String capitalizedRegion = Character.toUpperCase(region.charAt(0)) + region.substring(1).toLowerCase();
        if (Arrays.asList(regionStore.regions).contains(capitalizedRegion)){
            user.isRegionChosen = true;
            user.region = capitalizedRegion;
            task = user.taskGenerator.getQuestion(capitalizedRegion);
            user.setTask(task);
            return task.getTask();
        }
        else {
            return "Нет такого региона!";
        }
    }
}
