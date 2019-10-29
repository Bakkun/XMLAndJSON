package service;

import model.Operation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class OperationServiceImpl implements OperationService {
    private List<Operation> operations;

    public OperationServiceImpl(TransactionService gt) {
        this.operations = new ArrayList<>(gt.getOperations());
    }

    @Override
    public List<Operation> getOperationsByPeriodAndType(LocalDate dateSince, LocalDate dateTo, String type) {
        if(type == null) throw new IllegalArgumentException("Тип не может быть null");

        String lowerType = type.toLowerCase();

        return this.operations.stream()
                .filter(filterByType(lowerType))
                .filter(filterByDate(dateSince, dateTo))
                .collect(Collectors.toList());
    }

    @Override
    public int balance() {
        int balance= 0;
        for (Operation operation : this.operations) {
            if ("доход".equals(operation.getOperationType())) {
                balance += operation.getSum();
            }
            if ("расход".equals(operation.getOperationType())) {
                balance -= operation.getSum();
            }
        }
        return balance;
    }

    @Override
    public List<Operation> getByType(String type) {
        if(type == null) throw new IllegalArgumentException("Тип не может быть null");

        String lowerType = type.toLowerCase();
        return this.operations.stream().filter(filterByType(lowerType)).collect(Collectors.toList());
    }

    @Override
    public List<Operation> getByPeriod(LocalDate dateSince, LocalDate dateTo) {
        return this.operations.stream().filter(filterByDate(dateSince, dateTo)).collect(Collectors.toList());
    }

    private Predicate<Operation> filterByType(String type) {
        return operation -> type.equals(operation.getOperationType());
    }

    private Predicate<Operation> filterByDate(LocalDate dateSince, LocalDate dateTo) {
        return operation -> operation.getOperationDate().isAfter(dateSince) && operation.getOperationDate().isBefore(dateTo);
    }
}
