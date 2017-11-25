package ir.jahanmir.araxx.events;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetPayOnlineResponse {
    boolean status;
    String html;
    public EventOnGetPayOnlineResponse(String html) {
        this.html = html;
    }
    public String getHtml() {
        return html;
    }
}
