package view;

import model.Operation;
import service.BusinessService;
import service.TransactionService;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class Menu {
    private TransactionService transactionService;
    private BusinessService businessService;
    private Scanner in = new Scanner(System.in);

    public Menu() {
        try {
            FileInputStream fis = new FileInputStream("D:\\JProjects\\XMLAndJSON\\src\\main\\resources\\application.properties");
            Properties property = new Properties();
            property.load(fis);
            String str = property.getProperty("defaultFile");

            transactionService = new TransactionService(str);
            transactionService.load();
            businessService = new BusinessService(transactionService);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printMenu() {
        System.out.println("1. Список всех операций");
        System.out.println("2. Добавить операцию");
        System.out.println("3. Узнать текущий баланс семьи.");
        System.out.println("4. Данные об операциях за определённый период времени.");
        System.out.println("0. Выход.");
    }

    public void handleUserInput() throws IOException {
        Scanner scanner = new Scanner(System.in);
        int menuItem;
        do {
            System.out.print("Выберите пункт меню:");
            menuItem = in.nextInt();
            switch (menuItem) {
                case 1:
                    System.out.println("Список всех оперций:");
                    for (Operation operation : transactionService.getOperations()) {
                        System.out.println(operation);
                    }
                    break;
                case 2:
                    System.out.println("Введите данные в формате <тип операции> <сумма> <наименование операции> <человек> <Дата (dd.mm.yyyy)>:");
                    String operationInfo = scanner.nextLine();
                    transactionService.handleOperations(new Operation(operationInfo));
                    break;
                case 3:
                    System.out.print("Текущий баланс семьи: " + businessService.balance() + "р");
                    break;
                case 4:
                    System.out.println("Вводите даты в формате dd.mm.yyyy!");
                    System.out.println("Введите дату с которой нужно начинать искать:");
                    String dateSince = scanner.nextLine();
                    System.out.println("Введите дату по которую нужно начинать искать:");
                    String dateTo = scanner.nextLine();
                    System.out.println("Введите тип операции (доход/расход):");
                    String type = scanner.nextLine();
                    businessService.getByPeriodAndType(dateSince, dateTo, type);
                    break;
                case 0:
                    System.out.println("Завершение работы...");
                    System.exit(1);
                default:
                    System.out.println("Неверный выбор.");
            }
        } while(true);
    }
}
