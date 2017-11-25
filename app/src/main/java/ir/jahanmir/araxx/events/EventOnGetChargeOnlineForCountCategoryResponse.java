package ir.jahanmir.araxx.events;

import java.util.List;

import ir.jahanmir.araxx.gson.ChargeOnlineCategory;


/**
 * Created by HaMiD on 4/24/2017.
 */

public class EventOnGetChargeOnlineForCountCategoryResponse {
    List<ChargeOnlineCategory> chargeOnlineCategoryList;
    int isClub;
    int whichMenuItem;
    long groupCode;

    public EventOnGetChargeOnlineForCountCategoryResponse(List<ChargeOnlineCategory> chargeOnlineCategoryList, int isClub, int whichMenuItem, long groupCode) {
        this.chargeOnlineCategoryList = chargeOnlineCategoryList;
        this.isClub = isClub;
        this.whichMenuItem = whichMenuItem;
        this.groupCode = groupCode;
    }

    public List<ChargeOnlineCategory> getChargeOnlineCategoryList() {
        return chargeOnlineCategoryList;
    }

    public void setChargeOnlineCategoryList(List<ChargeOnlineCategory> chargeOnlineCategoryList) {
        this.chargeOnlineCategoryList = chargeOnlineCategoryList;
    }

    public int isClub() {
        return isClub;
    }

    public void setClub(int club) {
        isClub = club;
    }

    public int getWhichMenuItem() {
        return whichMenuItem;
    }

    public void setWhichMenuItem(int whichMenuItem) {
        this.whichMenuItem = whichMenuItem;
    }

    public long getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(long groupCode) {
        this.groupCode = groupCode;
    }
}