package ir.jahanmir.araxx.events;


import ir.jahanmir.araxx.gson.ChargeOnlineMainItemResponse;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetChargeOnlineMainItem {
    ChargeOnlineMainItemResponse chargeOnlineMainItemResponse;

    public EventOnGetChargeOnlineMainItem(ChargeOnlineMainItemResponse chargeOnlineMainItemResponse) {
        this.chargeOnlineMainItemResponse = chargeOnlineMainItemResponse;
    }

    public ChargeOnlineMainItemResponse getChargeOnlineMainItemResponse() {
        return chargeOnlineMainItemResponse;
    }
}
