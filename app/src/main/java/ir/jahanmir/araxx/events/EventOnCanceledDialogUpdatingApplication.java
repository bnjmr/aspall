package ir.jahanmir.araxx.events;

/**
 * Created by Microsoft on 4/4/2016.
 */
public class EventOnCanceledDialogUpdatingApplication {

    String newVersion = "";
    boolean isForce;
    String url = "";
    public EventOnCanceledDialogUpdatingApplication(String newVersion, boolean isForce, String url) {
        this.newVersion = newVersion;
        this.isForce = isForce;
        this.url = url;
    }
    public boolean isForce() {
        return isForce;
    }
    public String getNewVersion() {
        return newVersion;
    }
    public String getUrl() {
        return url;
    }
}
