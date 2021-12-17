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

    public BotMessage startNewGame(User user){
        taskGenerator.setDescriptions(user.getID());
        task = taskGenerator.getDescription(user.getID());
        user.setScoreRegion("");
        user.setTask(task);
        return new BotMessage(task.getTask(), user.getID());
    }

    public BotMessage responseToMessage(User user, String msg){
        task = user.lastTask;
        if (msg.equalsIgnoreCase(task.getAnswer())) {
            task = taskGenerator.getDescription(user.getID());
            user.setTask(task);
            user.addPoints();
            if (task == null) {
                user.finishTheGame();
                return new BotMessage("Правильно!",
                        String.format("Ты угадал %d из 10 стран!", user.resetScore()),
                        user.getID());
            }
            return new BotMessage("Правильно!", task.getTask(), user.getID());
        } else {
            var lastTask = task;
            task = taskGenerator.getDescription(user.getID());
            user.setTask(task);
            if (task == null) {
                user.finishTheGame();
                return new BotMessage(
                        String.format("Неправильно! Правильный ответ: %s", lastTask.getAnswer()),
                        String.format("Ты угадал %d из 10 стран!", user.resetScore()),
                        user.getID());
            }
            return new BotMessage(
                    String.format("Неправильно! Правильный ответ: %s", lastTask.getAnswer()),
                    task.getTask(), user.getID());
        }
    }
}
