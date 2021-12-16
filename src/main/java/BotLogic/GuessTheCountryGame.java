package BotLogic;

import Tasks.Description;
import Tasks.IGameTask;
import Users.User;

public class GuessTheCountryGame{
    private IGameTask task;

    public GuessTheCountryGame(){
        task = new Description();
    }

    public String responseToMessage(User user, String msg){
        task = user.lastTask;
        if (msg.equalsIgnoreCase(task.getAnswer())) {
            task = user.taskGenerator.getDescription();
            user.setTask(task);
            user.addPoints();
            if (task == null) {
                var score = user.getScore();
                user.finishTheGame();
                return String.format("Правильно!\nТы угадал %d из 10 стран!", score);
            }
            return "Правильно!" + "\n" + task.getTask();
        } else {
            task = user.taskGenerator.getDescription();
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
