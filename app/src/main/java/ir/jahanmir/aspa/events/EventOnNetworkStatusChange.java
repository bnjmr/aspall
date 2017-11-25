package ir.jahanmir.aspa.events;

/**
 * Created by Microsoft on 5/3/2016.
 */
public class EventOnNetworkStatusChange {
    int status;
    public EventOnNetworkStatusChange(int status) {
        this.status = status;
    }
    public int getStatus() {
        return status;
    }
}
