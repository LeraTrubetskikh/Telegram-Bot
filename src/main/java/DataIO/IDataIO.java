package DataIO;

public interface IDataIO {
    String read();
    void write(String str);
    void readUpdate(String str);
    String getAnswer();
}