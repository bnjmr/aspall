package ir.jahanmir.aspa.events;


import ir.jahanmir.aspa.gson.SelectFactorResponse;

/**
 * Created by Microsoft on 3/29/2016.
 */
public class EventOnGetSelectFactorResponse {
    SelectFactorResponse selectFactorResponse;
    public EventOnGetSelectFactorResponse(SelectFactorResponse selectFactorResponse) {
        this.selectFactorResponse = selectFactorResponse;
    }
    public SelectFactorResponse getSelectFactorResponse() {
        return selectFactorResponse;
    }
}
