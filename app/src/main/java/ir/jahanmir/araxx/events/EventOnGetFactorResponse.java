package ir.jahanmir.araxx.events;

import java.util.List;

import ir.jahanmir.araxx.model.Factor;


public class EventOnGetFactorResponse {
    List<Factor> factorResponses;
    public EventOnGetFactorResponse(List<Factor> factorResponses) {
        this.factorResponses = factorResponses;
    }
    public List<Factor> getFactorResponses() {
        return factorResponses;
    }
}
