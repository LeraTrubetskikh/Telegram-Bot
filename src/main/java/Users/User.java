package Users;

import Tasks.IGameTask;

public class User {

    public IGameTask lastTask;
    public Boolean gameMode;
    public Boolean isRegionChosen;
    public Boolean isNameTheCapitalGame;
    public Boolean isGuessTheCountryGame;
    public Integer hintCounter;
    private String answerToPreviousTask;
    private final Long id;
    private final ScoreCounter score;

    public User(Long id) {
        this.id = id;
        score = new ScoreCounter();
        isNameTheCapitalGame = false;
        isGuessTheCountryGame = false;
        gameMode = false;
        isRegionChosen = false;
        hintCounter = 0;
    }

    public Long getID() { // нигде не используется но пусть будет?
        return id;
    }

    public void setTask(IGameTask task) {
        lastTask = task;
    }

    public void setScoreRegion(String region){
        score.setRegion(region);
    }

    public void addPoints() { // названия?
        score.add();
    }

    public int resetScore() {
        return score.resetScore();
    }

    public String getStat() {
        return score.getStat();
    }

    public String getAnswerToPreviousTask() { return answerToPreviousTask; }

    public void updatePreviousAnswer() { answerToPreviousTask = lastTask.getAnswer(); }

    public void finishTheGame(){
        score.updateStat();
        gameMode = false;
        isRegionChosen = false;
        isNameTheCapitalGame = false;
        isGuessTheCountryGame = false;
        hintCounter = 0;
    }
}
