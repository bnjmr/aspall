package ir.jahanmir.aspa.events;


import ir.jahanmir.aspa.gson.GetIspInfoResponse;

/**
 * Created by Microsoft on 3/5/2016.
 */
public class EventOnGetIspInfoResponse {
    GetIspInfoResponse ispInfo;
    public EventOnGetIspInfoResponse(GetIspInfoResponse ispInfo) {
        this.ispInfo = ispInfo;
    }
    public GetIspInfoResponse getIspInfo() {
        return ispInfo;
    }
}
