package ir.jahanmir.aspa.events;

import java.util.List;

import ir.jahanmir.aspa.gson.SearchISPResponse;


/**
 * Created by Microsoft on 3/2/2016.
 */
public class EventOnGetIspListResponse {
    List<SearchISPResponse> isps;
    public EventOnGetIspListResponse(List<SearchISPResponse> isps) {
        this.isps = isps;
    }
    public List<SearchISPResponse> getIsps() {
        return isps;
    }
}
