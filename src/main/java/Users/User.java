package Users;

import Questions.Question;

public class User {

    private Long id;
    private int points;
    public Question lastQuestion;

    public User(Long id) {
        this.id = id;
        points = 0;
        lastQuestion = new Question("", "");
    }

    public Long getID() { return id; }
    public int getPoints() { return points; }
    public void setQuestion(Question question) { lastQuestion = question; }
    public void addPoints() { points++; }

}
