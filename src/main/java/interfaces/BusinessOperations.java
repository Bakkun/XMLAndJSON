package interfaces;

public interface BusinessOperations {
    void getByPeriodAndType(String dateSinceStr, String dateToStr, String type);
    int balance();
    void getByType(String type);
    void getByPeriod(String dateSinceStr, String dateToStr);
}
