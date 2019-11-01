import view.Menu;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
    private static final LogManager logManager = LogManager.getLogManager();
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    static {
        try {
            logManager.readConfiguration(new FileInputStream("D:\\JProjects\\XMLAndJSON\\src\\main\\resources\\logging.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
        Menu menu = new Menu();
        menu.printMenu();
        menu.handleUserInput();
    }
}