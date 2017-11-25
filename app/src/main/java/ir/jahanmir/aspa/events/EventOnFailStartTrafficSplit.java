package ir.jahanmir.aspa.events;

/**
 * Created by FCC on 10/30/2017.
 */

public class EventOnFailStartTrafficSplit {

    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public EventOnFailStartTrafficSplit(String message) {

        this.message = message;
    }
}
