package service;

import model.Operation;

import java.io.IOException;
import java.util.List;

public interface TransactionService {
    List<Operation> getOperations();
    void load() throws IOException;
    void handleOperations(Operation operation) throws IOException;
}
