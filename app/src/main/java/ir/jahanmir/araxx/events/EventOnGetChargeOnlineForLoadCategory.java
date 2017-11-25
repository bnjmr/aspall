package ir.jahanmir.araxx.events;

import java.util.List;

import ir.jahanmir.araxx.gson.ChargeOnlineCategory;


/**
 * Created by HaMiD on 3/14/2017.
 */

public class EventOnGetChargeOnlineForLoadCategory {
    List<ChargeOnlineCategory> chargeOnlineCategoryList;

    public EventOnGetChargeOnlineForLoadCategory(List<ChargeOnlineCategory> chargeOnlineCategoryList) {
        this.chargeOnlineCategoryList = chargeOnlineCategoryList;
    }

    public List<ChargeOnlineCategory> getChargeOnlineCategoryList() {
        return chargeOnlineCategoryList;
    }
}
