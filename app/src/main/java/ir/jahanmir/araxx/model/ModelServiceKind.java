package ir.jahanmir.araxx.model;

/**
 * Created by FCC on 11/6/2017.
 */

public class ModelServiceKind {
    String name;

    int serviceKind;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServiceKind() {
        return serviceKind;
    }

    public void setServiceKind(int serviceKind) {
        this.serviceKind = serviceKind;
    }

    public ModelServiceKind(String name, int serviceKind) {

        this.name = name;
        this.serviceKind = serviceKind;
    }
}
