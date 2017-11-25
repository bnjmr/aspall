package ir.jahanmir.araxx.events;

import java.util.List;

import ir.jahanmir.araxx.gson.NotifyResponse;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetNotifiesResponse {
    List<NotifyResponse> notifyResponse;
    public EventOnGetNotifiesResponse(List<NotifyResponse> notifyResponse) {
        this.notifyResponse = notifyResponse;
    }
    public List<NotifyResponse> getNotifyResponse() {
        return notifyResponse;
    }
}
