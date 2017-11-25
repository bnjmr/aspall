package ir.jahanmir.araxx.events;

/**
 * Created by Microsoft on 4/4/2016.
 */
public class EventOnGetErrorOnDownloadFile {
    int downloadId;
    int errorCode = 0;
    public EventOnGetErrorOnDownloadFile(int downloadId, int errorCode) {
        this.downloadId = downloadId;
        this.errorCode = errorCode;
    }
    public int getUrl() {
        return downloadId;
    }
    public int getErrorCode() {
        return errorCode;
    }
}
