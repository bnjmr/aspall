package ir.jahanmir.araxx.events;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetChargeOnlineResponse {
    String html = "";
    public EventOnGetChargeOnlineResponse(String html) {
        this.html = html;
    }
    public String getHtml() {
        return html;
    }
}
