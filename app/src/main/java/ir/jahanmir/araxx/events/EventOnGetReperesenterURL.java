package ir.jahanmir.araxx.events;

/**
 * Created by FCC on 10/22/2017.
 */

public class EventOnGetReperesenterURL {

    String siteUrl;
    String RepresenterURL ;

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getRepresenterURL() {
        return RepresenterURL;
    }

    public void setRepresenterURL(String representerURL) {
        RepresenterURL = representerURL;
    }

    public EventOnGetReperesenterURL(String siteUrl, String representerURL) {

        this.siteUrl = siteUrl;
        RepresenterURL = representerURL;
    }
}
