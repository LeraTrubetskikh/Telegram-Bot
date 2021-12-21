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
        BotMessage message = new BotMessage(task.getTask(), user.getID());
        message.isHaveHint = true;
        return message;
    }

    public BotMessage responseToMessage(User user, String msg){
        task = user.lastTask;
        BotMessage message;
        if(msg.equalsIgnoreCase("подсказка!")){
            message = getHint(user);
        }
        else if (msg.equalsIgnoreCase(task.getAnswer())) {
            task = taskGenerator.getDescription(user.getID());
            user.setTask(task);
            user.addPoints();
            if (task == null) {
                user.finishTheGame();
                message = new BotMessage("Правильно!",
                        String.format("Ты угадал %d из 10 стран!", user.resetScore()),
                        user.getID());
            }
            else
                message = new BotMessage("Правильно!", task.getTask(), user.getID());
        } else {
            var lastTask = task;
            task = taskGenerator.getDescription(user.getID());
            user.setTask(task);
            if (task == null) {
                user.finishTheGame();
                message = new BotMessage(
                        String.format("Неправильно! Правильный ответ: %s", lastTask.getAnswer()),
                        String.format("Ты угадал %d из 10 стран!", user.resetScore()),
                        user.getID());
            }
            else
                message = new BotMessage(
                    String.format("Неправильно! Правильный ответ: %s", lastTask.getAnswer()),
                    task.getTask(), user.getID());
        }
        message.isHaveHint = true;
        return message;
    }

    private BotMessage getHint(User user){
        BotMessage message;
        if (user.hintCounter < 5){
            if (!task.getAnswer().equals(user.getAnswerToPreviousTask())){
                user.hintCounter += 1;
                user.updatePreviousAnswer();
            }
            String firstLetter = String.valueOf(task.getAnswer().charAt(0));
            message = new BotMessage(String.format("Первая буква %s", firstLetter), user.getID());
        }
        else {
            message = new BotMessage("Подсказки кончились!", user.getID());
        }
        message.isHaveHint = true;
        return message;
    }
}
