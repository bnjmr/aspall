package ir.jahanmir.araxx.events;

/**
 * Created by Microsoft on 4/11/2016.
 */
public class EventOnGetErrorChargeOnlineForPayRequest {
    int errorType;
    public EventOnGetErrorChargeOnlineForPayRequest(int errorType) {
        this.errorType = errorType;
    }
    public int getErrorType() {
        return errorType;
    }
}
