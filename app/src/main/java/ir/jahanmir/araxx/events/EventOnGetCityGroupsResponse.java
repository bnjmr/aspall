package ir.jahanmir.araxx.events;


import ir.jahanmir.araxx.gson.CityGroupResponse;

/**
 * Created by Microsoft on 4/24/2016.
 */
public class EventOnGetCityGroupsResponse {
    CityGroupResponse[] cityGroupResponses;
    public EventOnGetCityGroupsResponse(CityGroupResponse[] cityGroupResponses) {
        this.cityGroupResponses = cityGroupResponses;
    }
    public CityGroupResponse[] getCityGroupResponses() {
        return cityGroupResponses;
    }
}
