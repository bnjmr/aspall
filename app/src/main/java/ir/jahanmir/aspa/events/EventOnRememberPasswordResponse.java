package ir.jahanmir.aspa.events;

/**
 * Created by Microsoft on 3/10/2016.
 */
public class EventOnRememberPasswordResponse {
    int status;
    String Message = "";

    public EventOnRememberPasswordResponse(int status, String message) {
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
