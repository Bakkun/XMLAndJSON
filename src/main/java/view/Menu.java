package view;

import DAO.StorageHandler;
import DAO.StorageHandlerJson;
import DAO.StorageHandlerXml;
import model.Operation;
import service.OperationService;
import service.OperationServiceImpl;
import service.TransactionService;
import service.TransactionServiceImpl;

import javax.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Menu {
    private static Logger log = Logger.getLogger(Menu.class.getName());
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private TransactionService transactionServiceOperations;
    private OperationService operationService;
    private Scanner in = new Scanner(System.in);

    public Menu() {
        try {
            FileInputStream fileInputStream = new FileInputStream(".\\src\\main\\resources\\application.properties");
            Properties properties = new Properties();
            properties.load(fileInputStream);
            String fileName = properties.getProperty("defaultFile");

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
            log.log(Level.SEVERE, "Error working with file.", e);
        }
        catch (JAXBException e) {
            log.log(Level.SEVERE, "Error working with JAXB.", e);
        }
    }

    public void printMenu() {
        System.out.println("1. Список всех операций");
        System.out.println("2. Добавить операцию");
        System.out.println("3. Узнать текущий баланс семьи.");
        System.out.println("4. Данные об операциях за определённый период времени.");
        System.out.println("0. Выход.");
        log.fine("print menu method started");
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
                    log.log(Level.FINE, "A list of all operations was requested.");
                    break;
                case 2:
                    System.out.println("Введите данные в формате <тип операции> <сумма> <наименование операции> <человек> <Дата (dd.mm.yyyy)>:");
                    String operationInfo = scanner.nextLine();
                    Operation operationToSave = new Operation(operationInfo);
                    transactionServiceOperations.handleOperations(operationToSave);
                    log.log(Level.FINE, "New operation was added: " + operationToSave);
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
                        log.log(Level.FINE, "Operations under specified conditions not found.");
                    } else {
                        StringBuilder str = new StringBuilder();
                        for (Operation operation : operationService.getOperationsByPeriodAndType(dateSince, dateTo, type)) {
                            str.append(operation).append(";\n");
                            System.out.println(operation);                        }
                        log.log(Level.FINE, "List of operations under specified conditions: \n" + str);
                    }
                    break;
                case 0:
                    System.out.println("Завершение работы...");
                    log.log(Level.FINE, "Shutdown...");
                    System.exit(1);
                default:
                    System.out.println("Неверный выбор.");
                    log.log(Level.WARNING, "Wrong choice was made.");
            }
        } while(true);
    }
}
