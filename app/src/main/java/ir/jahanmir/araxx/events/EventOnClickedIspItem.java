package ir.jahanmir.araxx.events;

/**
 * Created by Microsoft on 3/3/2016.
 */
public class EventOnClickedIspItem {
    String ispUrl;
    long ispId;
    public EventOnClickedIspItem(String ispUrl, long ispId) {
        this.ispUrl = ispUrl;
        this.ispId = ispId;
    }
    public String getIspUrl() {
        return ispUrl;
    }
    public long getIspId() {
        return ispId;
    }
}
