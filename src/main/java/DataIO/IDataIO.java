package DataIO;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface IDataIO {
    String read();
    void write(String str);
    void readUpdate(Message message);
    SendMessage getAnswer();
}