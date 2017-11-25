package ir.jahanmir.aspa.events;

public class EventOnClickedChargeOnlineGroup {
    int groupCode;
    int isClub;
    int whichMenuItem;
    public EventOnClickedChargeOnlineGroup(int groupCode, int isClub, int whichMenuItem) {
        this.groupCode = groupCode;
        this.isClub = isClub;
        this.whichMenuItem = whichMenuItem;
    }
    public int getWhichMenuItem() {
        return whichMenuItem;
    }
    public int getIsClub() {
        return isClub;
    }
    public int getGroupCode() {
        return groupCode;
    }
}
