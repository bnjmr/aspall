package ir.jahanmir.aspa.events;


import ir.jahanmir.aspa.gson.PayFactorFromCreditResponse;

/**
 * Created by Microsoft on 5/23/2016.
 */
public class EventOnGetPayFactorFromCreditResponse {
    PayFactorFromCreditResponse response;

    public EventOnGetPayFactorFromCreditResponse(PayFactorFromCreditResponse response) {
        this.response = response;
    }

    public PayFactorFromCreditResponse getResponse() {
        return response;
    }
}
