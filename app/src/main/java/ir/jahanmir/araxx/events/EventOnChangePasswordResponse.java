package ir.jahanmir.araxx.events;

/**
 * Created by Microsoft on 3/10/2016.
 */
public class EventOnChangePasswordResponse {
    int status;
    String Message = "";

    public EventOnChangePasswordResponse(int status, String message) {
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
