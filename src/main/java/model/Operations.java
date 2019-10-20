package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "operations")
@XmlAccessorType(XmlAccessType.FIELD)
public class Operations {
    @XmlElement(name = "operation")
    private List<Operation> operations = new ArrayList<>();

    public Operations() {}

    public Operations(List<Operation> fromXML) {
        this.operations = new ArrayList<>(fromXML);
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

//    @Override
//    public String toString() {
//        return "Operations{" +
//                "operations=" + operations +
//                '}';
//    }
}
