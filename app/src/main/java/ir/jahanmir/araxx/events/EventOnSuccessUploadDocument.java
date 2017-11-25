package ir.jahanmir.araxx.events;

/**
 * Created by FCC on 11/11/2017.
 */

public class EventOnSuccessUploadDocument {

    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public EventOnSuccessUploadDocument(String message) {

        this.message = message;
    }
}
