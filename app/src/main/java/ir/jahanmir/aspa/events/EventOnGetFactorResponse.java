package ir.jahanmir.aspa.events;

import java.util.List;

import ir.jahanmir.aspa.model.Factor;


public class EventOnGetFactorResponse {
    List<Factor> factorResponses;
    public EventOnGetFactorResponse(List<Factor> factorResponses) {
        this.factorResponses = factorResponses;
    }
    public List<Factor> getFactorResponses() {
        return factorResponses;
    }
}
