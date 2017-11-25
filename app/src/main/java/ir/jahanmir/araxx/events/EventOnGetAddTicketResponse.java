package ir.jahanmir.araxx.events;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetAddTicketResponse {
    int status;
    long code;
    public EventOnGetAddTicketResponse(int status, long code) {
        this.status = status;
        this.code = code;
    }
    public long getCode() {
        return code;
    }
    public int getStatus(){
        return status;
    }
}
