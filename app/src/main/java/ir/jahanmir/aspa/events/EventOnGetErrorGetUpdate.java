package ir.jahanmir.aspa.events;

/**
 * Created by Microsoft on 3/3/2016.
 */
public class EventOnGetErrorGetUpdate {
    int errorType;
    public EventOnGetErrorGetUpdate(int errorType) {
        this.errorType = errorType;
    }
    public int getErrorType() {
        return errorType;
    }
}
