import model.Operation;
import service.TransactionService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        TransactionService ts = new TransactionService("D:\\JProjects\\XMLAndJSON\\src\\main\\resources\\operations.json");
        ts.load();
//        System.out.println(ts.getOperations());
        ts.addOperation(new Operation("расход 300р маникюр мама 01.05.2019"));
        ts.addOperation(new Operation("расход 30990р смартфон сын 23.10.2019"));
        ts.addOperation(new Operation("доход 60000р зарплата отец 25.10.2019"));
//        System.out.println(ts.getOperations());
        ts.save();
        ts.moneyMovementInPeriod("31.12.2018", "31.12.2019", "Доход");
    }
}