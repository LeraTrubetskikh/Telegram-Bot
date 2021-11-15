package DataIO;

import java.util.Scanner;

public class ConsoleIO implements IDataIO {

    private final Scanner scanner;
    public ConsoleIO(){
        scanner = new Scanner(System.in);
    }

    @Override
    public String read() {
        return scanner.nextLine();
    }

    @Override
    public void write(String message) {
        System.out.println(message);
    }

    @Override
    public void readUpdate(String str){}

    @Override
    public String getAnswer(){
        return null;
    }
}
