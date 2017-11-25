package ir.jahanmir.araxx.events;

/**
 * Created by Microsoft on 3/17/2016.
 */
public class EventOnClickedTicketItem {
    long code;
    int open;

    public void setCode(long code) {
        this.code = code;
    }

    public int getOpen() {
        return open;
    }

    public void setOpen(int open) {
        this.open = open;
    }

    public EventOnClickedTicketItem(int open, long code) {
        this.code = code;
        this.open = open;
    }
    public long getCode() {
        return code;
    }
}
