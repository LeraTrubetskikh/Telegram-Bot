import BotLogic.BotLogic;
import BotLogic.BotMessage;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Objects;

public class TelegramBot extends TelegramLongPollingBot {

    private final String token;
    private final String username;
    private final BotLogic botLogic;

    public TelegramBot(String token, String username, BotLogic botLogic) {
        this.token = token;
        this.username = username;
        this.botLogic = botLogic;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                Message inMessage = update.getMessage();

                botLogic.readUpdate(new BotMessage(inMessage.getText(), inMessage.getChatId()));
                BotMessage newMessage = botLogic.getAnswer();

                SendMessage outMessage = new SendMessage();
                outMessage.setText(newMessage.getText());
                outMessage.setChatId(newMessage.getUserId());
                if (!Objects.equals(newMessage.getText(), ""))
                    execute(outMessage);
            }
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public String getBotUsername() {
        return username;
    }
}