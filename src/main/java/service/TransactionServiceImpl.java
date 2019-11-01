package service;

import DAO.StorageHandler;
import model.Operation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransactionServiceImpl implements TransactionService {
    private static final Logger log = Logger.getLogger(OperationServiceImpl.class.getName());
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
            log.log(Level.FINE, "List of operations was initialized as empty List.");
        } else {
            this.operations = storageHandler.getAllOperations();
            log.log(Level.FINE, "List of operations was initialized from file.");
        }
    }

    @Override
    public void handleOperations(Operation operation) throws IOException {
        this.operations.add(operation);
        storageHandler.saveAllOperations(this.operations);
        log.log(Level.FINE, "Operations was saved to the file.");
    }
}
