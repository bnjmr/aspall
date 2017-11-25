package ir.jahanmir.aspa.events;


import ir.jahanmir.aspa.model.getUpdate;

/**
 * Created by FCC on 10/25/2017.
 */

public class EventOnShowUpdateDialog {

    getUpdate update;

    public getUpdate getUpdate() {
        return update;
    }

    public void setUpdate(getUpdate update) {
        this.update = update;
    }

    public EventOnShowUpdateDialog(getUpdate update) {

        this.update = update;
    }
}
