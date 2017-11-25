package ir.jahanmir.araxx.events;


import ir.jahanmir.araxx.gson.ISPInfoLoginResponse;

/**
 * Created by Microsoft on 3/5/2016.
 */
public class EventOnGetIspInfoLoginResponse {
    ISPInfoLoginResponse ispInfo;

    public EventOnGetIspInfoLoginResponse(ISPInfoLoginResponse ispInfo) {
        this.ispInfo = ispInfo;
    }

    public ISPInfoLoginResponse getIspInfo() {
        return ispInfo;
    }
}
