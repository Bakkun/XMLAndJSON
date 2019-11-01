package DAO;

import model.Operation;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StorageHandlerXml implements StorageHandler {
    private static Logger log = Logger.getLogger(StorageHandlerXml.class.getName());

    private String fileName;
    private Marshaller jaxbMarshaller;
    private Unmarshaller jaxbUnmarshaller;
    private Operations operations;

    public StorageHandlerXml() {}

    public StorageHandlerXml(String fileName) throws JAXBException {
        this.fileName = fileName;
        JAXBContext jaxbContext = JAXBContext.newInstance(Operations.class);
        jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        this.operations = new Operations();
    }

    @Override
    public List<Operation> getAllOperations() {
        try {
            operations = (Operations) jaxbUnmarshaller.unmarshal(new FileReader(fileName));
            log.log(Level.FINE, "Unload operations from .xml file.");
            return operations.operations;
        } catch (JAXBException e) {
            log.log(Level.SEVERE, "Failed unload operations from .xml file.", e);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Failed read the .xml file.");
        }
        return Collections.emptyList();
    }

    @Override
    public void saveAllOperations(List<Operation> operationsFromApp) {
        this.operations.setOperations(operationsFromApp);
        persistOperations(this.operations);
        log.log(Level.FINE, "Operations was saved to the .xml file.");
    }

    private void persistOperations(Operations operations) {
        try (FileWriter writer = new FileWriter(fileName)) {
            jaxbMarshaller.marshal(operations, writer);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Failed write to the .xml file.", e);
        } catch (JAXBException e) {
            log.log(Level.SEVERE, "Failed to persist operations to the .xml file.", e);
        }
    }

    @XmlRootElement(name = "operations")
    @XmlAccessorType(XmlAccessType.FIELD)
    static class Operations {
        @XmlElement(name = "operation")
        private List<Operation> operations = null;

        void setOperations(List<Operation> operations) {
            this.operations = new ArrayList<>(operations);
        }
    }
}
