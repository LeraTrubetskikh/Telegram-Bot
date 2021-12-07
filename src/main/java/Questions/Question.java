package Questions;

public class Question {
    public String country;
    public String capital;
    private String countryGenitive;

    public String getQuestion(){
        return String.format("Столица %s?", countryGenitive);
    }

    public String getAnswer(){
        return capital;
    }
}
