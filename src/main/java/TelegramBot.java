import BotLogic.BotLogic;
import BotLogic.BotMessage;
import BotLogic.RegionStore;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
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
        if (update.hasMessage() && update.getMessage().hasText()) {
            try {
                Message inMessage = update.getMessage();
                BotMessage newMessage = botLogic.getNewMessage(new BotMessage(inMessage.getText(), inMessage.getChatId()));
                getTelegramOutMessage(newMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (update.hasCallbackQuery()) {
            try {
                BotMessage botMessage = new BotMessage(update.getCallbackQuery().getData(),
                        update.getCallbackQuery().getMessage().getChatId());
                BotMessage newMessage = botLogic.getNewMessage(botMessage);
                getTelegramOutMessage(newMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
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

    private void getTelegramOutMessage(BotMessage newMessage) throws TelegramApiException {
        SendMessage outMessage = new SendMessage();
        outMessage.setChatId(newMessage.getUserId());

        if (newMessage.buttonsFlag) {
            outMessage.setText(newMessage.getText());
            setInlineKeyBoardToMessage(outMessage, newMessage.getButtons());
            execute(outMessage);
        } else {
            outMessage.setText(newMessage.getText());
            setReplyKeyBoardToMessage(outMessage, newMessage.getGameMode());
            execute(outMessage);
            if(newMessage.multipleMessages){
                outMessage.setText(newMessage.getText2());
                execute(outMessage);
            }
        }
    }

    private static void setReplyKeyBoardToMessage(SendMessage outMessage, Boolean gameMode) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        outMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        if (!gameMode) {
            keyboardFirstRow.add(new KeyboardButton("/start"));
            keyboardFirstRow.add(new KeyboardButton("/newgame"));

            KeyboardRow keyboardSecondRow = new KeyboardRow();
            keyboardSecondRow.add(new KeyboardButton("/help"));
            keyboardSecondRow.add(new KeyboardButton("/stat"));
            keyboardSecondRow.add(new KeyboardButton("/info"));

            keyboard.add(keyboardFirstRow);
            keyboard.add(keyboardSecondRow);
        }
        else {
            keyboardFirstRow.add(new KeyboardButton("/stop"));
            keyboard.add(keyboardFirstRow);
        }
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    private static void setInlineKeyBoardToMessage(SendMessage outMessage, String[] buttons) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        for (var i = 0; i < buttons.length;) {
            List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();

            var to = 2;
            if (i > 3)
                to = 1;

            for (var j = 0; j < to; j++) {
                InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                inlineKeyboardButton.setText(buttons[i]);
                inlineKeyboardButton.setCallbackData(buttons[i]);
                keyboardButtonsRow.add(inlineKeyboardButton);
                i++;
            }
            rowList.add(keyboardButtonsRow);
        }
        inlineKeyboardMarkup.setKeyboard(rowList);
        outMessage.setReplyMarkup(inlineKeyboardMarkup);
    }
}