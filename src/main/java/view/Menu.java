package view;

import DAO.StorageHandlerJson;
import DAO.StorageHandlerXml;
import service.OperationService;
import service.TransactionService;
import DAO.StorageHandler;
import model.Operation;
import service.OperationServiceImpl;
import service.TransactionServiceImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Properties;
import java.util.Scanner;

public class Menu {
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private TransactionService transactionServiceOperations;
    private OperationService operationService;
    private Scanner in = new Scanner(System.in);

    public Menu() {
        try {
            FileInputStream fis = new FileInputStream(".\\src\\main\\resources\\application.properties");
            Properties property = new Properties();
            property.load(fis);
            String fileName = property.getProperty("defaultFile");

            StorageHandler storageHandler;
            if (fileName.endsWith(".xml")) {
                storageHandler = new StorageHandlerXml(fileName);
            } else if (fileName.endsWith(".json")) {
                storageHandler = new StorageHandlerJson(fileName);
            } else {
                throw new RuntimeException("Неподдерживаемый тип файла.");
            }

            transactionServiceOperations = new TransactionServiceImpl(storageHandler);
            transactionServiceOperations.load();
            operationService = new OperationServiceImpl(transactionServiceOperations);
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
                    if (transactionServiceOperations.getOperations().isEmpty()) {
                        System.out.println("Пока что операций нет.");
                    } else {
                        for (Operation operation : transactionServiceOperations.getOperations()) {
                            System.out.println(operation);
                        }
                    }
                    break;
                case 2:
                    System.out.println("Введите данные в формате <тип операции> <сумма> <наименование операции> <человек> <Дата (dd.mm.yyyy)>:");
                    String operationInfo = scanner.nextLine();
                    transactionServiceOperations.handleOperations(new Operation(operationInfo));
                    break;
                case 3:
                    System.out.print("Текущий баланс семьи: " + operationService.balance() + "р\n");
                    break;
                case 4:
                    System.out.println("Вводите даты в формате dd.mm.yyyy!");
                    System.out.println("Введите дату, с которой нужно искать:");
                    String dateSinceStr = scanner.nextLine();
                    System.out.println("Введите дату, по которую нужно искать:");
                    String dateToStr = scanner.nextLine();
                    System.out.println("Введите тип операции (доход/расход):");
                    String type = scanner.nextLine();
                    LocalDate dateSince = LocalDate.parse(dateSinceStr, FORMATTER);
                    LocalDate dateTo = LocalDate.parse(dateToStr, FORMATTER);
                    if (operationService.getOperationsByPeriodAndType(dateSince, dateTo, type).isEmpty()) {
                        System.out.println("Записей, удовлетворяющих условиям нет в спике операций.");
                    } else {
                        for (Operation operation : operationService.getOperationsByPeriodAndType(dateSince, dateTo, type)) {
                            System.out.println(operation);
                        }
                    }
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
