package BotLogic;

public class BotMessage {
    private String text;
    private String text2;
    public boolean multipleMessages;
    private Long userId;
    private Boolean gameMode;
    private String[] buttons;
    public boolean buttonsFlag;

    public BotMessage(String text, Long userId){
        this.text = text;
        this.userId = userId;
        multipleMessages = false;
        buttonsFlag = false;
        gameMode = false;
    }

    public BotMessage(String text, String text2, Long userId){
        this.text = text;
        this.text2 = text2;
        this.userId = userId;
        multipleMessages = true;
        buttonsFlag = false;
        gameMode = false;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getText2() {
        return text2;
    }

    public void setGameMode(Boolean gameMode) {
        this.gameMode = gameMode;
    }

    public Boolean getGameMode() {
        return gameMode;
    }

    public void setButtons(String[] buttons) {
        this.buttons = buttons;
        buttonsFlag = true;
    }

    public String[] getButtons() {
        return buttons;
    }
}
