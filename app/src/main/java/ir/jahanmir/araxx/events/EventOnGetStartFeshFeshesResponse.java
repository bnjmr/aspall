package ir.jahanmir.araxx.events;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetStartFeshFeshesResponse {
    int status;
    String message = "";
    public EventOnGetStartFeshFeshesResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
    public int getStatus(){
        return status;
    }
    public String getMessage() {
        return message;
    }
}
