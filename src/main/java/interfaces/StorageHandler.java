package interfaces;

import model.Operation;

import java.io.IOException;
import java.util.List;

public interface StorageHandler {
    List<Operation> getAllOperations() throws IOException;
    void saveAllOperations(List<Operation> operations) throws IOException;
}
