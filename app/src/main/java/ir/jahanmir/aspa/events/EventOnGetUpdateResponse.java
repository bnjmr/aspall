package ir.jahanmir.aspa.events;


import ir.jahanmir.aspa.model.getUpdate;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetUpdateResponse {
    getUpdate updateResponse;
    public EventOnGetUpdateResponse(getUpdate getUpdateResponse) {
        this.updateResponse = getUpdateResponse;
    }
    public getUpdate getUpdateResponse() {
        return updateResponse;
    }
}
