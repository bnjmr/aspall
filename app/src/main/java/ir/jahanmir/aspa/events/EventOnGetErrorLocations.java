package ir.jahanmir.aspa.events;

/**
 * Created by HaMiD on 3/5/2017.
 */

public class EventOnGetErrorLocations {
    int errorType;

    public EventOnGetErrorLocations(int errorType) {
        this.errorType = errorType;
    }

    public int getErrorType() {
        return errorType;
    }

    public void setErrorType(int errorType) {
        this.errorType = errorType;
    }
}
