package DAO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import interfaces.StorageHandler;
import model.Operation;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class JsonStorageHandler implements StorageHandler {
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private String fileName;

    public JsonStorageHandler() {}

    public JsonStorageHandler(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Operation> getAllOperations() throws IOException {
        Type operationsFromJson = new TypeToken<List<Operation>>(){}.getType();
        try (Reader reader = new FileReader(fileName)) {
            return gson.fromJson(reader, operationsFromJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public void saveAllOperations(List<Operation> operations) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(operations, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
