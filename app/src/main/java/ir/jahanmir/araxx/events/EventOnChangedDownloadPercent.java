package ir.jahanmir.araxx.events;

/**
 * Created by Microsoft on 4/5/2016.
 */
public class EventOnChangedDownloadPercent {
    int downloadId;
    float percent;
    public EventOnChangedDownloadPercent(int downloadId, float percent) {
        this.downloadId = downloadId;
        this.percent = percent;
    }
    public float getPercent() {
        return percent;
    }
    public int getDownloadId() {
        return downloadId;
    }
}
