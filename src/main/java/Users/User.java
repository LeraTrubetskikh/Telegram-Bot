package Users;

import Questions.Question;

public class User {

    private final Long id;
    private int points;
    public Question lastQuestion;
    public Boolean gameMode;

    public User(Long id) {
        this.id = id;
        points = 0;
        lastQuestion = new Question("", "");
        gameMode = false;
    }

    public Long getID() { return id; }
    public int getPoints() { return points; }
    public void setQuestion(Question question) { lastQuestion = question; }
    public void addPoints() { points++; }

}
