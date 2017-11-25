package ir.jahanmir.araxx.events;

/**
 * Created by Microsoft on 5/23/2016.
 */
public class EventOnGetErrorCheckTaraz {
    int errorType;
    public EventOnGetErrorCheckTaraz(int errorType) {
        this.errorType = errorType;
    }
    public int getErrorType() {
        return errorType;
    }
}
