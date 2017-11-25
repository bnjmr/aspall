package ir.jahanmir.aspa.events;

/**
 * Created by Microsoft on 3/28/2016.
 */
public class EventOnGetCheckChargeOnlineClub {
    int status;
    boolean isClub;
    int whichMenuItem;
    public EventOnGetCheckChargeOnlineClub(int status, boolean isClub,int whichMenuItem) {
        this.status = status;
        this.isClub = isClub;
        this.whichMenuItem = whichMenuItem;
    }
    public int getStatus(){
        return status;
    }
    public boolean getIsClub(){
        return isClub;
    }
    public int getWhichMenuItem() {
        return whichMenuItem;
    }
}
