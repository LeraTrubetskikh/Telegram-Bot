package Questions;

public class Description implements IGameTask {

    private String description;
    private String answer;

    @Override
    public String getTask() {
        return String.format("Назовите страну по описанию:\n%s?", description);
    }

    @Override
    public String getAnswer() {
        return answer;
    }
}
