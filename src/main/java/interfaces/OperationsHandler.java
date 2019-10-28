package interfaces;

import model.Operation;

import java.io.IOException;
import java.util.List;

public interface OperationsHandler {
    List<Operation> getOperations();
    void load() throws IOException;
    void handleOperations(Operation operation) throws IOException;
}
