import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Scan {

    private static Scanner scanner;

    public Scan() {
        scanner = new Scanner(System.in);
    }

    public String getLine() {
        return scanner.nextLine();
    }

    public void printLine(String message) {
        System.out.println(message);
    }

    public void printHelp() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("README.md"));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            br.close();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
