package ir.jahanmir.aspa.events;

import java.util.List;

import ir.jahanmir.aspa.gson.GetUnitsResponse;


/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetUnitResponse {
    List<GetUnitsResponse> units;
    public EventOnGetUnitResponse(List<GetUnitsResponse> units) {
        this.units = units;
    }
    public List<GetUnitsResponse> getUnits() {
        return units;
    }
}
