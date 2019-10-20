package service;

import model.Operation;
import model.Operations;
import service.interfaces.MainOperations;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class XmlService implements MainOperations {
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static Operations operationList = new Operations();

    public XmlService() {
        if (!isEmpty()) {
            operationList = new Operations(getFromXML());
        }
    }

    public void sendToXML(String str) {
        Operation operation = new Operation(str);
        operationList.getOperations().add(operation);

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Operations.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            try (FileWriter writer = new FileWriter("D:\\JProjects\\XMLAndJSON\\src\\main\\resources\\operations.xml")) {
                jaxbMarshaller.marshal(operationList, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public List<Operation> getFromXML() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Operations.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            operationList = (Operations) jaxbUnmarshaller.unmarshal(new FileReader("D:\\JProjects\\XMLAndJSON\\src\\main\\resources\\operations.xml"));

        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(operationList.getOperations());
    }

    public void moneyMovementInPeriod(String dateSinceStr, String dateToStr, String type) {
        LocalDate dateSince = LocalDate.parse(dateSinceStr, FORMATTER);
        LocalDate dateTo = LocalDate.parse(dateToStr, FORMATTER);

        int result = 0;

        for (Operation operation : getFromXML()) {
            if (operation.getOperationDate().isAfter(dateSince) && operation.getOperationDate().isBefore(dateTo)) {
                if (operation.getOperationType().intern() == type.intern()) {
                    result += operation.getSum();
                    System.out.println(operation);
                }
            }
        }

        System.out.println(type + " за период с " + dateSinceStr + " по " + dateToStr + ": " + result);
    }

    public boolean isEmpty() {
        return getFromXML().size() == 0;
    }
}
