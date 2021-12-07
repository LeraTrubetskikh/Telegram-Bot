package Users;

import BotLogic.BotLogic;
import Questions.Question;

import java.util.HashMap;

public class User {

    public Question lastQuestion;
    public Boolean gameMode;
    public Boolean isRegionChosen;
    public String region;
    public HashMap<String, Integer> bestScore;
    private final Long id;
    private int score;
    private final String[] regions;

    public User(Long id) {
        BotLogic botLogic = new BotLogic();
        this.id = id;
        this.regions = botLogic.regions;
        score = 0;
        lastQuestion = new Question();
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

    public void setQuestion(Question question) {
        lastQuestion = question;
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
    }

    private void initializeBestScoreHashMap(){
        for (String s : regions) {
            bestScore.put(s, 0);
        }
    }
}
