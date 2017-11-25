package ir.jahanmir.araxx.events;


import ir.jahanmir.araxx.gson.CurrentFeshFesheResponse;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetCurrentFeshFesheResponse {
    CurrentFeshFesheResponse currentFeshFesheResponse;
    public EventOnGetCurrentFeshFesheResponse(CurrentFeshFesheResponse currentFeshFesheResponses) {
        this.currentFeshFesheResponse = currentFeshFesheResponses;
    }
    public CurrentFeshFesheResponse getCurrentFeshFesheResponse() {
        return currentFeshFesheResponse;
    }
}
