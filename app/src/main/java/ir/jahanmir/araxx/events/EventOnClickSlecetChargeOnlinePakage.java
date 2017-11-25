package ir.jahanmir.araxx.events;

/**
 * Created by HaMiD on 3/16/2017.
 */

public class EventOnClickSlecetChargeOnlinePakage {

    long packageCode;
    long groupCode;

    public EventOnClickSlecetChargeOnlinePakage(long packageCode, long groupCode) {
        this.packageCode = packageCode;
        this.groupCode = groupCode;
    }

    public long getPackageCode() {
        return packageCode;
    }

    public long getGroupCode() {
        return groupCode;
    }
}
