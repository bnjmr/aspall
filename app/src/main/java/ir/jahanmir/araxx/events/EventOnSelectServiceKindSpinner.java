package ir.jahanmir.araxx.events;

/**
 * Created by FCC on 11/6/2017.
 */

public class EventOnSelectServiceKindSpinner {

    int serviceKind;

    public int getServiceKind() {
        return serviceKind;
    }

    public void setServiceKind(int serviceKind) {
        this.serviceKind = serviceKind;
    }

    public EventOnSelectServiceKindSpinner(int serviceKind) {

        this.serviceKind = serviceKind;
    }
}
