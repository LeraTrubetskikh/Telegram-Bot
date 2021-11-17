package DataIO;

import Questions.Question;
import Questions.QuestionGenerator;
import Users.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class TelegramIO implements IDataIO {
    private Message message;
    private HashMap<Long, User> users;
    private QuestionGenerator questionGenerator;
    private Question question;
    private HashMap<String,String> answers;

    @Override
    public String read() {
        return null;
    }

    @Override
    public void write(String message) {
    }

    @Override
    public void readUpdate(Message message) {
        this.message = message;
    }

    @Override
    public SendMessage getAnswer() {
        String text = message.getText();
        Long id = message.getChatId();

        if (!users.containsKey(id))
            users.put(id, new User(id));
        else
            question = users.get(id).lastQuestion;

        if (text.charAt(0) == '/') {
            text = responseToCommand(text);
            users.get(id).setQuestion(question);
        } else {
            text = responseToMessage(text);
            users.get(id).setQuestion(question);
        }
        SendMessage newMessage = new SendMessage();
        newMessage.setChatId(message.getChatId().toString());
        newMessage.setText(text);
        return newMessage;
    }

    public TelegramIO(){
        questionGenerator = new QuestionGenerator();
        answers = new HashMap<>();
        users = new HashMap<>();
        question = new Question("","");
        getAnswers();
    }

    public String responseToCommand(String message) {
        var outputMsg = "";
        switch (message) {
            case ("/start"):
                outputMsg = answers.get("start");
                break;
            case ("/help"):
                outputMsg = answers.get("help");
                break;
            case ("/stop"):
                break;
            case ("/newgame"):
                question = questionGenerator.getQuestion();
                outputMsg = question.question;
                break;
            default:
                outputMsg = "Это не команда";
                break;
        }
        return outputMsg;
    }

    public String responseToMessage(String msg){
        if (msg.equals(question.answer)) {
            question = questionGenerator.getQuestion();
            return "Правильно!" + "\n" + question.question;
        } else {
            question = questionGenerator.getQuestion();
            return "Неправильно!" + "\n" + question.question;
        }
    }

    private void getAnswers() {
        try {
            FileReader fr = new FileReader("src/main/resources/answers.txt");
            Scanner scan = new Scanner(fr);

            String[] s;
            while(scan.hasNextLine()){
                s = scan.nextLine().split(":");
                answers.put(s[0], s[1]);
            }
            fr.close();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
