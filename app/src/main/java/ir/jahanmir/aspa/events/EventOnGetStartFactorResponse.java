package ir.jahanmir.aspa.events;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetStartFactorResponse {
    int status;
    String Message = "";

    public EventOnGetStartFactorResponse(int status, String message) {
        this.status = status;
        Message = message;
    }
    public int getStatus(){
        return status;
    }
    public String getMessage() {
        return Message;
    }
}
