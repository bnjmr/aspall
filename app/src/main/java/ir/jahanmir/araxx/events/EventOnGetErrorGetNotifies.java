package ir.jahanmir.araxx.events;

/**
 * Created by Microsoft on 3/3/2016.
 */
public class EventOnGetErrorGetNotifies {
    int errorType;
    public EventOnGetErrorGetNotifies(int errorType) {
        this.errorType = errorType;
    }
    public int getErrorType() {
        return errorType;
    }
}
