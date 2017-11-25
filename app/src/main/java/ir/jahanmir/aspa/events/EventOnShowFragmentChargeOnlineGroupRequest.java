package ir.jahanmir.aspa.events;

/**
 * Created by Microsoft on 3/30/2016.
 */
public class EventOnShowFragmentChargeOnlineGroupRequest {
    int isClub;
    int whichMenuItem;

    public EventOnShowFragmentChargeOnlineGroupRequest(int isClub, int whichMenuItem) {
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
