package Tasks;

public class Question implements IGameTask {

    public String capital;
    private String countryGenitive;

    @Override
    public String getTask() {
        return String.format("Столица %s?", countryGenitive);
    }

    @Override
    public String getAnswer(){
        return capital;
    }
}
