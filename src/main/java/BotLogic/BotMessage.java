package BotLogic;

public class BotMessage {
    private String text;
    private Long userId;
    private Boolean gameMode;

    public BotMessage(String text, Long userId){
        this.text = text;
        this.userId = userId;
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

    public void setGameMode(Boolean gameMode) {
        this.gameMode = gameMode;
    }

    public Boolean getGameMode() {
        return gameMode;
    }
}
