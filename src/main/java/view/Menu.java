package view;

import service.TransactionService;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class Menu {
    private FileInputStream fis;
    private TransactionService ts;
    public Menu() {
        try {
            fis = new FileInputStream("D:\\JProjects\\XMLAndJSON\\src\\main\\resources\\application.properties");
            Properties property = new Properties();
            property.load(fis);
            String str = property.getProperty("defaultFile");

            ts = new TransactionService(str);
            ts.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Scanner in = new Scanner(System.in);

    public void printMenu() {
        for (int i = 1; i <= 3; i++) {
            System.out.println(i + ". Menu item #" + i);
        }
        System.out.println("0. Quit");
    }

    public void handleUserInput() {
        int menuItem;
        do {
            System.out.print("Choose menu item: ");
            menuItem = in.nextInt();
            switch (menuItem) {
                case 1:
                    System.out.println("You've chosen item #1");
                    System.out.println(ts.getOperations());
                    break;
                case 2:
                    System.out.println("You've chosen item #2");
                    // do something...
                    break;
                case 3:
                    System.out.println("You've chosen item #3");
                    // do something...
                    break;
                case 0:
                    System.out.println("Close");
                    System.exit(1);
                default:
                    System.out.println("Invalid choice.");
            }
        } while(true);
    }
}
