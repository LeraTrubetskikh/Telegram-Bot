package BotLogic;

import Tasks.Description;
import Tasks.IGameTask;
import Tasks.TaskGenerator;
import Users.User;

public class GuessTheCountryGame{

    private IGameTask task;
    private final TaskGenerator taskGenerator;

    public GuessTheCountryGame(){
        taskGenerator = new TaskGenerator();
        task = new Description();
    }

    public String startNewGame(User user){
        taskGenerator.setDescriptions(user.getID());
        task = taskGenerator.getDescription(user.getID());
        user.region = "Описание страны";
        user.setTask(task);
        return task.getTask();
    }

    public String responseToMessage(User user, String msg){
        task = user.lastTask;
        if (msg.equalsIgnoreCase(task.getAnswer())) {
            task = taskGenerator.getDescription(user.getID());
            user.setTask(task);
            user.addPoints();
            if (task == null) {
                var score = user.getScore();
                user.finishTheGame();
                return String.format("Правильно!\nТы угадал %d из 10 стран!", score);
            }
            return "Правильно!" + "\n" + task.getTask();
        } else {
            task = taskGenerator.getDescription(user.getID());
            user.setTask(task);
            if (task == null) {
                var score = user.getScore();
                user.finishTheGame();
                return String.format("Неправильно!\nТы угадал %d из 10 стран!", score);
            }
            return "Неправильно!" + "\n" + task.getTask();
        }
    }
}
