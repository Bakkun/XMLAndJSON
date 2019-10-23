package service;

import DAO.JsonStorageHandler;
import DAO.XmlStorageHandler;
import interfaces.BusinessOperations;
import interfaces.StorageHandler;
import model.Operation;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransactionService implements BusinessOperations {
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private StorageHandler storageHandler;
    private List<Operation> operations;

    public TransactionService(String fileName) {
        if (fileName.endsWith(".xml")) {
            storageHandler = new XmlStorageHandler(fileName);
        } else if (fileName.endsWith(".json")) {
            storageHandler = new JsonStorageHandler(fileName);
        } else {
            throw new RuntimeException("неподдерж файл");
        }
    }

    public void load() throws IOException {
        if (storageHandler.getAllOperations() == null) {
            this.operations = new ArrayList<>();
        } else {
            this.operations = storageHandler.getAllOperations();
        }
    }

    public void save() throws IOException {
        storageHandler.saveAllOperations(this.operations);
    }

    public List<Operation> getOperations() {
        return this.operations;
    }

    public void addOperation(Operation operation) throws IOException {
        this.operations.add(operation);
    }

    @Override
    public void moneyMovementInPeriod(String dateSinceStr, String dateToStr, String type) {
        LocalDate dateSince = LocalDate.parse(dateSinceStr, FORMATTER);
        LocalDate dateTo = LocalDate.parse(dateToStr, FORMATTER);

        String lowerType = type.toLowerCase();

        int money = 0;

        for (Operation operation : this.operations) {
            if (operation.getOperationDate().isAfter(dateSince) && operation.getOperationDate().isBefore(dateTo)) {
                if (operation.getOperationType().intern() == lowerType.intern()) {
                    money += operation.getSum();
                    System.out.println(operation);
                }
            }
        }

        System.out.println(lowerType + " за период с " + dateSinceStr + " по " + dateToStr + ": " + money + "р");
    }
}
