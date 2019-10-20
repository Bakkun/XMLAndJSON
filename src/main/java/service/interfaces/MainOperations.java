package service.interfaces;

import java.time.format.DateTimeFormatter;

public interface MainOperations {
    void moneyMovementInPeriod(String dateSinceStr, String dateToStr, String type);
    boolean isEmpty();
}
