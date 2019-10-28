package service;

import interfaces.BusinessOperations;
import interfaces.OperationsHandler;
import model.Operation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BusinessService implements BusinessOperations {
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private List<Operation> operations;

    public BusinessService(OperationsHandler gt) {
        this.operations = new ArrayList<>(gt.getOperations());
    }

    @Override
    public void getByPeriodAndType(String dateSinceStr, String dateToStr, String type) {
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

    @Override
    public int balance() {
        int balance= 0;
        for (Operation operation : this.operations) {
            if (operation.getOperationType().intern() == "доход") {
                balance += operation.getSum();
            }
            if (operation.getOperationType().intern() == "расход") {
                balance -= operation.getSum();
            }
        }
        return balance;
    }

    @Override
    public void getByType(String type) {
        String lowerType = type.toLowerCase();
        for (Operation operation : this.operations) {
            if (operation.getOperationType().intern() == lowerType.intern()) {
                System.out.println(operation);
            }
        }
    }

    @Override
    public void getByPeriod(String dateSinceStr, String dateToStr) {
        LocalDate dateSince = LocalDate.parse(dateSinceStr, FORMATTER);
        LocalDate dateTo = LocalDate.parse(dateToStr, FORMATTER);

        for (Operation operation : this.operations) {
            if (operation.getOperationDate().isAfter(dateSince) && operation.getOperationDate().isBefore(dateTo)) {
                System.out.println(operation);
            }
        }
    }
}
