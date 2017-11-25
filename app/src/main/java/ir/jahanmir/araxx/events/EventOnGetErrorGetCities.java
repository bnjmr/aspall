package ir.jahanmir.araxx.events;

/**
 * Created by Microsoft on 4/24/2016.
 */
public class EventOnGetErrorGetCities {
    int errorType;
    public EventOnGetErrorGetCities(int errorType) {
        this.errorType =errorType;
    }
    public int getErrorType() {
        return errorType;
    }
}
