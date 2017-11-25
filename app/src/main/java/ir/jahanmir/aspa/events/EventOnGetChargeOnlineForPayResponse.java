package ir.jahanmir.aspa.events;

/**
 * Created by Microsoft on 4/11/2016.
 */
public class EventOnGetChargeOnlineForPayResponse {
    long orderId;
    String orderUssd = "";
    public EventOnGetChargeOnlineForPayResponse(long orderId, String orderUssd) {
        this.orderId = orderId;
        this.orderUssd = orderUssd;
    }
    public String getOrderUssd() {
        return orderUssd;
    }
    public long getOrderId() {
        return orderId;
    }
}
