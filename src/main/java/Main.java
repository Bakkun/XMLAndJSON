import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.io.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) throws ParseException {
        Gson gson = new Gson();

        String str = "расход 20р батарейка муж 12.12.2019";

//        LocalDate date = LocalDate.parse("12.10.2019", DateTimeFormatter.BASIC_ISO_DATE);
//        System.out.println(date);

        Operation operation = new Operation(str);

        try (FileWriter writer = new FileWriter("D:\\JProjects\\XMLAndJSON\\src\\main\\resources\\operations.json")) {
            gson.toJson(operation, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Reader reader = new FileReader("D:\\JProjects\\XMLAndJSON\\src\\main\\resources\\operations.json")) {
            Operation operation1 = gson.fromJson(reader, Operation.class);
            System.out.println(operation1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}