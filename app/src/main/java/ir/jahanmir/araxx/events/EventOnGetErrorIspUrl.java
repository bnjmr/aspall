package ir.jahanmir.araxx.events;

/**
 * Created by Microsoft on 4/6/2016.
 */
public class EventOnGetErrorIspUrl {
    int errorType;
    public EventOnGetErrorIspUrl(int errorType) {
        this.errorType = errorType;
    }
    public int getErrorType() {
        return errorType;
    }
}
