package ir.jahanmir.araxx.events;

/**
 * Created by Microsoft on 4/4/2016.
 */
public class EventOnShowDialogUpdatingApplicationRequest {
    String url = "";
    boolean isForce;
    String newVersion = "";

    public EventOnShowDialogUpdatingApplicationRequest(String newVersion, boolean isForce , String url) {
        this.url = url;
        this.isForce = isForce;
        this.newVersion = newVersion;
    }
    public String getUrl() {
        return url;
    }

    public boolean isForce() {
        return isForce;
    }

    public String getNewVersion() {
        return newVersion;
    }
}
