package ir.jahanmir.araxx.events;

/**
 * Created by FCC on 11/11/2017.
 */

public class EventOnGetCountNotify {

    int Count;

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    public EventOnGetCountNotify(int count) {
        Count = count;
    }
}
