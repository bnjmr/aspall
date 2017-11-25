package ir.jahanmir.araxx.events;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetVisitMobileResponse {
    int status;
    public EventOnGetVisitMobileResponse(int status) {
        this.status = status;
    }
    public int getStatus(){
        return status;
    }
}
