package ir.jahanmir.araxx.events;

/**
 * Created by Microsoft on 4/24/2016.
 */
public class EventOnGetErrorRegisterCustomer {
    int errorType;
    public EventOnGetErrorRegisterCustomer(int errorType) {
        this.errorType = errorType;
    }
    public int getErrorType() {
        return errorType;
    }
}
