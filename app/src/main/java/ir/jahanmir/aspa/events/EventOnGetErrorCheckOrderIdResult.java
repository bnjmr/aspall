package ir.jahanmir.aspa.events;

/**
 * Created by Microsoft on 4/11/2016.
 */
public class EventOnGetErrorCheckOrderIdResult {
    int errorType;
    public EventOnGetErrorCheckOrderIdResult(int errorType) {
        this.errorType = errorType;
    }
    public int getErrorType() {
        return errorType;
    }
}
