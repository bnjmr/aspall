package ir.jahanmir.aspa.events;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetEndFeshFeshesResponse {
    int status;
    String message = "";
    public EventOnGetEndFeshFeshesResponse(int status, String message) {
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
