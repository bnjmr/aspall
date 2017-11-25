package ir.jahanmir.aspa.events;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnSetPollResponse {
    int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    String message;

    public void setStatus(int status) {
        this.status = status;
    }

    public EventOnSetPollResponse(int status, String message) {

        this.status = status;
        this.message = message;
    }

    public EventOnSetPollResponse(int status) {
        this.status = status;
    }
    public int getStatus(){
        return status;
    }
}
