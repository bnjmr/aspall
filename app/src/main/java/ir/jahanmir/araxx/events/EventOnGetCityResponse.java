package ir.jahanmir.araxx.events;


import ir.jahanmir.araxx.gson.CityResponse;

/**
 * Created by Microsoft on 4/24/2016.
 */
public class EventOnGetCityResponse {
    CityResponse[] cityResponses;
    public EventOnGetCityResponse(CityResponse[] cityResponses) {
        this.cityResponses = cityResponses;
    }
    public CityResponse[] getCityResponses() {
        return cityResponses;
    }
}
