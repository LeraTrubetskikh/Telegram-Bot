import BotLogic.BotLogic;
import BotLogic.BotMessage;
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
                BotMessage newMessage = botLogic.handler(new BotMessage(inMessage.getText(), inMessage.getChatId()));
                SendMessage outMessage = new SendMessage();

                if (newMessage.getText().length() > 14 &&
                        Objects.equals(newMessage.getText().substring(0, 15), "Выберите регион")) {
                    outMessage.setText("Выберите регион:");
                    setInlineKeyBoardToMessage(outMessage);
                } else {
                    outMessage.setText(newMessage.getText());
                    setReplyKeyBoardToMessage(outMessage, newMessage.getGameMode());
                }
                outMessage.setChatId(newMessage.getUserId());
                execute(outMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (update.hasCallbackQuery()) {
            try {
                BotMessage botMessage = new BotMessage(update.getCallbackQuery().getData(),
                        update.getCallbackQuery().getMessage().getChatId());
                BotMessage newMessage = botLogic.handler(botMessage);

                SendMessage outMessage = new SendMessage();
                outMessage.setText(newMessage.getText());
                outMessage.setChatId(newMessage.getUserId());
                execute(outMessage);
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

            keyboard.add(keyboardFirstRow);
            keyboard.add(keyboardSecondRow);
        }
        else {
            keyboardFirstRow.add(new KeyboardButton("/stop"));
            keyboard.add(keyboardFirstRow);
        }
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    private static void setInlineKeyBoardToMessage(SendMessage outMessage) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Европа");
        inlineKeyboardButton1.setCallbackData("Европа");

        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText("Азия");
        inlineKeyboardButton2.setCallbackData("Азия");

        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        inlineKeyboardButton3.setText("Америка");
        inlineKeyboardButton3.setCallbackData("Америка");

        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
        inlineKeyboardButton4.setText("Африка");
        inlineKeyboardButton4.setCallbackData("Африка");

        InlineKeyboardButton inlineKeyboardButton5 = new InlineKeyboardButton();
        inlineKeyboardButton5.setText("Австралия и Океания");
        inlineKeyboardButton5.setCallbackData("Австралия и Океания");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();

        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow1.add(inlineKeyboardButton2);

        keyboardButtonsRow2.add(inlineKeyboardButton3);
        keyboardButtonsRow2.add(inlineKeyboardButton4);

        keyboardButtonsRow3.add(inlineKeyboardButton5);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        rowList.add(keyboardButtonsRow3);
        inlineKeyboardMarkup.setKeyboard(rowList);

        outMessage.setReplyMarkup(inlineKeyboardMarkup);
    }
}