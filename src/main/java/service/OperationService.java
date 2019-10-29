package service;

import model.Operation;

import java.time.LocalDate;
import java.util.List;

public interface OperationService {
    int balance();
    List<Operation> getOperationsByPeriodAndType(LocalDate dateSinceStr, LocalDate dateToStr, String type);
    List<Operation> getByType(String type);
    List<Operation> getByPeriod(LocalDate dateSince, LocalDate dateToStr);
}
