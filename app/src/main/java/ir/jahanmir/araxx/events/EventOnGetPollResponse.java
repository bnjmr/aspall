package ir.jahanmir.araxx.events;


import ir.jahanmir.araxx.gson.GetPollResponse;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetPollResponse {
    GetPollResponse pollResponse;
    public EventOnGetPollResponse(GetPollResponse getPollResponse) {
        this.pollResponse = getPollResponse;
    }
    public GetPollResponse getPollResponse() {
        return pollResponse;
    }
}
