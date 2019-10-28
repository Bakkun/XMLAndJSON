package service;

import DAO.JsonStorageHandler;
import DAO.XmlStorageHandler;
import interfaces.OperationsHandler;
import interfaces.StorageHandler;
import model.Operation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TransactionService implements OperationsHandler {
    private StorageHandler storageHandler;
    private List<Operation> operations;

    @Override
    public List<Operation> getOperations() {
        return new ArrayList<>(this.operations);
    }

    public TransactionService(String fileName) {
        if (fileName.endsWith(".xml")) {
            storageHandler = new XmlStorageHandler(fileName);
        } else if (fileName.endsWith(".json")) {
            storageHandler = new JsonStorageHandler(fileName);
        } else {
            throw new RuntimeException("неподдерж файл");
        }
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
