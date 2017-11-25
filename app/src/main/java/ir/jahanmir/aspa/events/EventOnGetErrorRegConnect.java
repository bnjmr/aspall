package ir.jahanmir.aspa.events;

/**
 * Created by Microsoft on 3/7/2016.
 */
public class EventOnGetErrorRegConnect {
    int errorType;
    public EventOnGetErrorRegConnect(int errorType) {
        this.errorType = errorType;
    }
    public int getErrorType() {
        return errorType;
    }
}
