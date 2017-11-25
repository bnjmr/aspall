package ir.jahanmir.araxx.events;

/**
 * Created by Microsoft on 4/3/2016.
 */
public class EventOnShowFragmentChargeOnlinePackageRequest {
    int isClub;
    int whichMenuItem;

    public EventOnShowFragmentChargeOnlinePackageRequest(int isClub, int whichMenuItem) {
        this.isClub = isClub;
        this.whichMenuItem = whichMenuItem;
    }
    public int getIsClub() {
        return isClub;
    }
    public int getWhichMenuItem() {
        return whichMenuItem;
    }
}
