package ir.jahanmir.araxx.events;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetAddTicketDetailsResponse {
    int status;
    String message;
    public EventOnGetAddTicketDetailsResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    public int getStatus(){
        return status;
    }
}
