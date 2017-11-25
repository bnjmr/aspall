package ir.jahanmir.aspa.events;

import java.util.List;

import ir.jahanmir.aspa.model.ModelCities;


/**
 * Created by FCC on 11/6/2017.
 */

public class EventOnSuccessGetCities {

    List<ModelCities> citiesList;

    public List<ModelCities> getCitiesList() {
        return citiesList;
    }

    public void setCitiesList(List<ModelCities> citiesList) {
        this.citiesList = citiesList;
    }

    public EventOnSuccessGetCities(List<ModelCities> citiesList) {

        this.citiesList = citiesList;
    }
}
