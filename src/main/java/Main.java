import service.TransactionService;
import view.Menu;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
//        TransactionService ts = new TransactionService("D:\\JProjects\\XMLAndJSON\\src\\main\\resources\\application.properties");
//        Properties property = new Properties();
//        ts.load();
//        ts.getOperations();
//        ts.moneyMovementInPeriod("31.12.2018", "31.12.2019", "доход");

//        FileInputStream fis;
//        Properties property = new Properties();
//        try {
//            fis = new FileInputStream("D:\\JProjects\\XMLAndJSON\\src\\main\\resources\\application.properties");
//            property.load(fis);
//            String str = property.getProperty("defaultFile");
//
//            TransactionService ts = new TransactionService(str);
//            ts.load();
////            System.out.println(ts.getOperations());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        Menu menu = new Menu();
        menu.printMenu();
        menu.handleUserInput();
    }
}