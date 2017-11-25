package ir.jahanmir.aspa.events;

/**
 * Created by Microsoft on 3/7/2016.
 */
public class EventOnGetErrorGetTickets {
    int errorType;
    public EventOnGetErrorGetTickets(int errorType) {
        this.errorType = errorType;
    }
    public int getErrorType() {
        return errorType;
    }
}
