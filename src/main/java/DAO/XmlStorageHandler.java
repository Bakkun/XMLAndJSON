package DAO;

import interfaces.StorageHandler;
import model.Operation;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class XmlStorageHandler implements StorageHandler {
    private String fileName;

    public XmlStorageHandler() {}

    public XmlStorageHandler(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Operation> getAllOperations() throws IOException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Operations.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Operations operations = (Operations) jaxbUnmarshaller.unmarshal(new FileReader(fileName));

            return operations.operations;
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
            try {
                throw e;
            } catch (JAXBException ex) {
                ex.printStackTrace();
            }
        }
        return Collections.emptyList();
    }

    @Override
    public void saveAllOperations(List<Operation> operations) throws IOException{
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(DAO.XmlStorageHandler.Operations.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            Operations operations1 = new Operations();
            operations1.setOperations(operations);

            try (FileWriter writer = new FileWriter(fileName)) {
                jaxbMarshaller.marshal(operations1, writer);
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
        } catch (JAXBException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
    }

    @XmlRootElement(name = "operations")
    @XmlAccessorType(XmlAccessType.FIELD)
    static class Operations {
        @XmlElement(name = "operation")
        private List<Operation> operations = null;

        List<Operation> getOperations() {
            return new ArrayList<>(operations);
        }

        void setOperations(List<Operation> operations) {
            this.operations = new ArrayList<>(operations);
        }
    }
}
