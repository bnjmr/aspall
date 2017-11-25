package ir.jahanmir.aspa.events;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetAlertResponse {
    boolean status;
    String message = "";
    public EventOnGetAlertResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
    }
    public boolean getStatus(){
        return status;
    }
    public String getMessage() {
        return message;
    }
}
