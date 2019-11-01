package DAO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Operation;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StorageHandlerJson implements StorageHandler {
    private static final Logger log = Logger.getLogger(StorageHandlerJson.class.getName());

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private String fileName;
    private Type operationsFromJson;

    public StorageHandlerJson() {}

    public StorageHandlerJson(String fileName) {
        this.fileName = fileName;
        this.operationsFromJson = new TypeToken<List<Operation>>(){}.getType();
    }

    @Override
    public List<Operation> getAllOperations() {
        try (Reader reader = new FileReader(fileName)) {
            log.log(Level.FINE, "Unload all operations from .json file.");
            return gson.fromJson(reader, operationsFromJson);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Failed unloading from .json file.", e);
        }
        return Collections.emptyList();
    }

    @Override
    public void saveAllOperations(List<Operation> operations) {
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(operations, writer);
            log.log(Level.FINE, "Upload all operations to .json file.");
        } catch (IOException e) {
            log.log(Level.SEVERE, "Failed writing to .json file.", e);
        }
    }
}
