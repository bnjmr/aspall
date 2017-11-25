package ir.jahanmir.araxx.events;

/**
 * Created by Microsoft on 4/11/2016.
 */
public class EventOnGetPayOnlineForPayResponse {
    long orderId;
    String orderUssd;
    public EventOnGetPayOnlineForPayResponse(long orderId, String orderUssd) {
        this.orderId = orderId;
        this.orderUssd = orderUssd;
    }
    public long getOrderId() {
        return orderId;
    }
    public String getOrderUssd() {
        return orderUssd;
    }
}
