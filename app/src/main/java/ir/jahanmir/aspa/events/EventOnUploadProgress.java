package ir.jahanmir.aspa.events;

/**
 * Created by FCC on 11/7/2017.
 */

public class EventOnUploadProgress {

    int progress;

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public EventOnUploadProgress(int progress) {

        this.progress = progress;
    }
}
