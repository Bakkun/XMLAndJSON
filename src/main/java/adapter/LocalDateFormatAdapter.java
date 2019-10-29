package adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

public class LocalDateFormatAdapter extends XmlAdapter<String, LocalDate> {

    @Override
    public LocalDate unmarshal(String localDate) {
        return LocalDate.parse(localDate);
    }

    @Override
    public String marshal(LocalDate localDate) {
        return localDate.toString();
    }
}
