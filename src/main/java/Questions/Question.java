package Questions;

public class Question {
    public String country;
    private String countryGenitive;
    public String capital;

    public String getQuestion(){
        return String.format("Столица %s?", countryGenitive);
    }

    public String getAnswer(){
        return capital;
    }
}
