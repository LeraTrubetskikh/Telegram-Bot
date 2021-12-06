package Users;

import Questions.Question;

import java.util.HashMap;

public class User {

    private final Long id;
    private int score;
    public Question lastQuestion;
    public Boolean gameMode;
    public Boolean isRegionChosen;
    public String region;
    public HashMap<String, Integer> bestScore;

    public User(Long id) {
        this.id = id;
        score = 0;
        lastQuestion = new Question();
        gameMode = false;
        isRegionChosen = false;
        region = "";
        bestScore = new HashMap<>();
        bestScore.put("Европа", 0);
        bestScore.put("Азия", 0);
        bestScore.put("Америка", 0);
        bestScore.put("Африка", 0);
        bestScore.put("Австралия и Океания", 0);
    }

    public Long getID() { // ничгде не используется но пусть будет?
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
}
