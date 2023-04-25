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

    public BotMessage startNewGame(User user, String region){
        String capitalizedRegion = Character.toUpperCase(region.charAt(0)) + region.substring(1).toLowerCase();
        if (Arrays.asList(regionStore.regions).contains(capitalizedRegion)){
            user.isRegionChosen = true;
            user.setScoreRegion(capitalizedRegion);
            taskGenerator.setQuestions(user.getID(), capitalizedRegion);
            task = taskGenerator.getQuestion(user.getID());
            user.setTask(task);
            return new BotMessage(task.getTask(), user.getID());
        }
        else {
            return new BotMessage("Нет такого региона!", user.getID());
        }
    }

    public BotMessage responseToMessage(User user, String msg){
        task = user.lastTask;
        if (msg.equalsIgnoreCase(task.getAnswer())) {
            task = taskGenerator.getQuestion(user.getID());
            user.setTask(task);
            user.addPoints();
            if (task == null) {
                user.finishTheGame();
                return new BotMessage("Правильно!",
                        String.format("Ваш счёт: %d/10", user.resetScore()),
                        user.getID());
            }
            return new BotMessage("Правильно!", task.getTask(), user.getID());
        } else {
            var lastTask = task;
            task = taskGenerator.getQuestion(user.getID());
            user.setTask(task);
            if (task == null) {
                user.finishTheGame();
                return new BotMessage(
                        String.format("Неправильно! Правильный ответ: %s", lastTask.getAnswer()),
                        String.format("Ваш счёт: %d/10", user.resetScore()),
                        user.getID());
            }
            return new BotMessage(
                    String.format("Неправильно! Правильный ответ: %s", lastTask.getAnswer()),
                    task.getTask(), user.getID());
        }
    }
}
