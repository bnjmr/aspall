package ir.jahanmir.aspa.events;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnSetAdsRepResponse {
    boolean status;
    public EventOnSetAdsRepResponse(boolean status) {
        this.status = status;
    }
    public boolean getStatus(){
        return status;
    }
}
