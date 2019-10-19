import service.JsonService;

import java.text.ParseException;

public class Main {
    public static void main(String[] args) {
        JsonService jsonService = new JsonService();
        //System.out.println(jsonService.getFormJson());
        //jsonService.sendToJson("расход 358р кино сын 01.01.2019");
        jsonService.moneyMovementInPeriod("31.12.2018", "31.02.2019", "расход");
    }
}