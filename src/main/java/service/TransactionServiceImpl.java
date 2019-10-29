package service;

import DAO.StorageHandler;
import model.Operation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {
    private StorageHandler storageHandler;
    private List<Operation> operations;

    @Override
    public List<Operation> getOperations() {
        return new ArrayList<>(this.operations);
    }

    public TransactionServiceImpl(StorageHandler storageHandler) {
        this.storageHandler = storageHandler;
    }

    @Override
    public void load() throws IOException {
        if (storageHandler.getAllOperations() == null) {
            this.operations = new ArrayList<>();
        } else {
            this.operations = storageHandler.getAllOperations();
        }
    }

    @Override
    public void handleOperations(Operation operation) throws IOException {
        this.operations.add(operation);
        storageHandler.saveAllOperations(this.operations);
    }
}
