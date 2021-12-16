package Users;

import BotLogic.RegionStore;
import Tasks.IGameTask;
import Tasks.TaskGenerator;

import java.util.HashMap;

public class User {

    public IGameTask lastTask;
    public TaskGenerator taskGenerator;
    public Boolean gameMode;
    public Boolean isRegionChosen;
    public Boolean isNameTheCapitalGame;
    public Boolean isGuessTheCountryGame;
    public String region;
    public HashMap<String, Integer> bestScore;
    private final Long id;
    private int score;
    private final RegionStore regionStore;

    public User(Long id) {
        this.id = id;
        regionStore = new RegionStore();
        score = 0;
        taskGenerator = new TaskGenerator();
        isNameTheCapitalGame = false;
        isGuessTheCountryGame = false;
        gameMode = false;
        isRegionChosen = false;
        region = "";
        bestScore = new HashMap<>();
        initializeBestScoreHashMap();
    }

    public Long getID() { // нигде не используется но пусть будет?
        return id;
    }

    public int getScore() {
        return score;
    }

    public void setTask(IGameTask task) {
        lastTask = task;
    }

    public void addPoints() { // названия?
        score++;
    }

    public void resetScore() {
        score = 0;
    }

    public String getStat() {
        StringBuilder sb = new StringBuilder();

        for (String key : bestScore.keySet()) {
            sb.append(String.format("%s: %d\n", key, bestScore.get(key)));
        }
        return sb.toString();
    }

    public void finishTheGame(){
        if (bestScore.get(region) < score)
            bestScore.put(region, score);
        resetScore();
        gameMode = false;
        isRegionChosen = false;
        isNameTheCapitalGame = false;
        isGuessTheCountryGame = false;
    }

    private void initializeBestScoreHashMap(){
        for (String s : regionStore.regions) {
            bestScore.put(s, 0);
        }
    }
}
