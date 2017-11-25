package ir.jahanmir.aspa.events;

import java.util.ArrayList;

import ir.jahanmir.aspa.gson.FactorDetailResponse;


/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetFactorDetailsResponse {
    ArrayList<FactorDetailResponse> factorDetailsResponse;
    public EventOnGetFactorDetailsResponse(ArrayList<FactorDetailResponse> factorDetailsResponse) {
        this.factorDetailsResponse = factorDetailsResponse;
    }
    public ArrayList<FactorDetailResponse> getFactorDetailResponse() {
        return factorDetailsResponse;
    }
}
