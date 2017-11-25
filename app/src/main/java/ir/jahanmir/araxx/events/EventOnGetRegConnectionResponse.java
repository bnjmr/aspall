package ir.jahanmir.araxx.events;

import java.util.List;

import ir.jahanmir.araxx.gson.RegConnectResponse;


/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetRegConnectionResponse {
    List<RegConnectResponse> regConnectResponses;
    public EventOnGetRegConnectionResponse(List<RegConnectResponse> regConnectResponses) {
        this.regConnectResponses = regConnectResponses;
    }
    public List<RegConnectResponse> getRegConnectResponses() {
        return regConnectResponses;
    }
}
