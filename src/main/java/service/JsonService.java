package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Operation;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class JsonService {
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private List<Operation> operationsToSend = new ArrayList<>();
    private Type operationsFromJson = new TypeToken<List<Operation>>(){}.getType();
    private List<Operation> operations;

    public JsonService() {
        if (!isEmpty()) {
            operationsToSend = getFormJson();
        }
    }

    public void sendToJson(String str) {
        Operation operation = new Operation(str);
        operationsToSend.add(operation);

        try (FileWriter writer = new FileWriter("D:\\JProjects\\XMLAndJSON\\src\\main\\resources\\operations.json")) {
            gson.toJson(operationsToSend, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Operation> getFormJson() {
        try (Reader reader = new FileReader("D:\\JProjects\\XMLAndJSON\\src\\main\\resources\\operations.json")) {
            operations = gson.fromJson(reader, operationsFromJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return operations;
    }

    // Проверка файла на пустоту
    private boolean isEmpty() {
        if (getFormJson().size() != 0) {
            System.out.println("Not Empty");
            return false;
        }
        System.out.println("Empty");
        return true;
    }

    // Доход/Расход за указанный период
    // TODO: сделать Enum с типами операций; Регистронезависимую проверку
    public void moneyMovementInPeriod(String dateSinceStr, String dateToStr, String type) {
        LocalDate dateSince = LocalDate.parse(dateSinceStr, FORMATTER);
        LocalDate dateTo = LocalDate.parse(dateToStr, FORMATTER);

        int money = 0;

        for (Operation operation : getFormJson()) {
            if (operation.getOperationDate().isAfter(dateSince) && operation.getOperationDate().isBefore(dateTo)) {
                if (operation.getOperationType().intern() == type.intern()) {
                    money += operation.getSum();
                    System.out.println(operation);
                }
            }
        }

        System.out.println(type + " за период с " + dateSinceStr + " по " + dateToStr + ": " + money);
    }
}
