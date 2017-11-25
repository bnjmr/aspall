package ir.jahanmir.araxx.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

/**
 * Created by Microsoft on 3/7/2016.
 */

@Table(name = "Account")
public class Account extends Model{
    //اطلاعات اکانت مشترک

//    "username": "jahanmir",
//            "FullName": "تست جهانمیر",
//            "UnitName": "ریال",
//            "Rday": 0,
//            "RHour": -11111,
//            "Tperc": 0,
//            "Dperc": 100,
//            "Hperc": 100,
//            "GrpName": "پروموشن",
//            "GrpId": 1,
//            "PkgName": "شارژ دستی",
//            "LastNewsID": 9,
//            "LastTicketID": 2809,
//            "lastNotifyID": 49,
//            "Online": false,
//            "Balance": 22679,
//            "ExpDate": "1920-1-1 0:0",
//            "FarsiExpDate": "متصل نشده",
//            "RegConnect": false,
//            "serviceName": "سرویس معمولی",
//            "serviceTraffic": 1000,
//            "currentTraffic": 1000,
//            "TransferedserviceTraffic": 0,
//            "nonTransferedTraffic": 0,
//            "transferedTraffic": 0,
//            "activeTrafficSplitMain": 0,
//            "remaindTrafficSplit": 0,
//            "activeTrafficSplit": 0,
//            "remaindFeshfeshe": 0,
//            "activeFeshfeshe": 0,
    @Expose
    @Column(name = "username")
    public String username;

    @Expose
    @Column(name = "FullName")
    public String FullName;


    @Expose
    @Column(name = "Rday")
    public int Rday;

    @Expose
    @Column(name = "RHour")
    public int RHour;

    @Expose
    @Column(name = "Tperc")
    public int Tperc;

    @Expose
    @Column(name = "Dperc")
    public int Dperc;

    @Expose
    @Column(name = "Hperc")
    public int Hperc;

    @Expose
    @Column(name = "GrpName")
    public String GrpName;

    @Expose
    @Column(name = "GrpId")
    public int GrpId;


    @Expose
    @Column(name = "PkgName")
    public String PkgName;

    @Expose
    @Column(name = "LastNewsID")
    public int LastNewsID;

    @Expose
    @Column(name = "LastTicketID")
    public int LastTicketID;

    @Expose
    @Column(name = "lastNotifyID")
    public int lastNotifyID;

    @Expose
    @Column(name = "Online")
    public boolean Online;

    @Expose
    @Column(name = "Balance")
    public int Balance;

    @Expose
    @Column(name = "ExpDate")
    public String ExpDate;

    @Expose
    @Column(name = "FarsiExpDate")
    public String FarsiExpDate;

    @Expose
    @Column(name = "RegConnect")
    public boolean RegConnect;

    @Expose
    @Column(name = "serviceName")
    public String serviceName;


    @Expose
    @Column(name = "serviceTraffic")
    public int serviceTraffic;

    @Expose
    @Column(name = "currentTraffic")
    public int currentTraffic;

    @Expose
    @Column(name = "TransferedserviceTraffic")
    public int TransferedserviceTraffic;

    @Expose
    @Column(name = "nonTransferedTraffic")
    public int nonTransferedTraffic;

    @Expose
    @Column(name = "transferedTraffic")
    public int transferedTraffic;

    @Expose
    @Column(name = "activeTrafficSplitMain")
    public int activeTrafficSplitMain;

    @Expose
    @Column(name = "remaindTrafficSplit")
    public int remaindTrafficSplit;

    @Expose
    @Column(name = "activeTrafficSplit")
    public int activeTrafficSplit;

    @Expose
    @Column(name = "remaindFeshfeshe")
    public int remaindFeshfeshe;

    @Expose
    @Column(name = "activeFeshfeshe")
    public int activeFeshfeshe;

    @Expose
    @Column(name = "Avatar")
    public String Avatar;


    @Expose
    @Column(name = "Message")
    public String Message;

    @Expose
    @Column(name = "Result")
    public int Result;

    @Expose
    @Column(name = "IsExpire")
    public boolean IsExpire;
////////////
    @Expose
    @Column(name = "CountNotify")
    public int CountNotify;

    @Expose
    @Column(name = "countTicket")
    public int countTicket;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public int getRday() {
        return Rday;
    }

    public void setRday(int rday) {
        Rday = rday;
    }

    public int getRHour() {
        return RHour;
    }

    public void setRHour(int RHour) {
        this.RHour = RHour;
    }

    public int getTperc() {
        return Tperc;
    }

    public void setTperc(int tperc) {
        Tperc = tperc;
    }

    public int getDperc() {
        return Dperc;
    }

    public void setDperc(int dperc) {
        Dperc = dperc;
    }

    public int getHperc() {
        return Hperc;
    }

    public void setHperc(int hperc) {
        Hperc = hperc;
    }

    public String getGrpName() {
        return GrpName;
    }

    public void setGrpName(String grpName) {
        GrpName = grpName;
    }

    public int getGrpId() {
        return GrpId;
    }

    public void setGrpId(int grpId) {
        GrpId = grpId;
    }

    public String getPkgName() {
        return PkgName;
    }

    public void setPkgName(String pkgName) {
        PkgName = pkgName;
    }

    public int getLastNewsID() {
        return LastNewsID;
    }

    public void setLastNewsID(int lastNewsID) {
        LastNewsID = lastNewsID;
    }

    public int getLastTicketID() {
        return LastTicketID;
    }

    public void setLastTicketID(int lastTicketID) {
        LastTicketID = lastTicketID;
    }

    public int getLastNotifyID() {
        return lastNotifyID;
    }

    public void setLastNotifyID(int lastNotifyID) {
        this.lastNotifyID = lastNotifyID;
    }

    public boolean isOnline() {
        return Online;
    }

    public void setOnline(boolean online) {
        Online = online;
    }

    public int getBalance() {
        return Balance;
    }

    public void setBalance(int balance) {
        Balance = balance;
    }

    public String getExpDate() {
        return ExpDate;
    }

    public void setExpDate(String expDate) {
        ExpDate = expDate;
    }

    public String getFarsiExpDate() {
        return FarsiExpDate;
    }

    public void setFarsiExpDate(String farsiExpDate) {
        FarsiExpDate = farsiExpDate;
    }

    public boolean isRegConnect() {
        return RegConnect;
    }

    public void setRegConnect(boolean regConnect) {
        RegConnect = regConnect;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getServiceTraffic() {
        return serviceTraffic;
    }

    public void setServiceTraffic(int serviceTraffic) {
        this.serviceTraffic = serviceTraffic;
    }

    public int getCurrentTraffic() {
        return currentTraffic;
    }

    public void setCurrentTraffic(int currentTraffic) {
        this.currentTraffic = currentTraffic;
    }

    public int getTransferedserviceTraffic() {
        return TransferedserviceTraffic;
    }

    public void setTransferedserviceTraffic(int transferedserviceTraffic) {
        TransferedserviceTraffic = transferedserviceTraffic;
    }

    public int getNonTransferedTraffic() {
        return nonTransferedTraffic;
    }

    public void setNonTransferedTraffic(int nonTransferedTraffic) {
        this.nonTransferedTraffic = nonTransferedTraffic;
    }

    public int getTransferedTraffic() {
        return transferedTraffic;
    }

    public void setTransferedTraffic(int transferedTraffic) {
        this.transferedTraffic = transferedTraffic;
    }

    public int getActiveTrafficSplitMain() {
        return activeTrafficSplitMain;
    }

    public void setActiveTrafficSplitMain(int activeTrafficSplitMain) {
        this.activeTrafficSplitMain = activeTrafficSplitMain;
    }

    public int getRemaindTrafficSplit() {
        return remaindTrafficSplit;
    }

    public void setRemaindTrafficSplit(int remaindTrafficSplit) {
        this.remaindTrafficSplit = remaindTrafficSplit;
    }

    public int getActiveTrafficSplit() {
        return activeTrafficSplit;
    }

    public void setActiveTrafficSplit(int activeTrafficSplit) {
        this.activeTrafficSplit = activeTrafficSplit;
    }

    public int getRemaindFeshfeshe() {
        return remaindFeshfeshe;
    }

    public void setRemaindFeshfeshe(int remaindFeshfeshe) {
        this.remaindFeshfeshe = remaindFeshfeshe;
    }

    public int getActiveFeshfeshe() {
        return activeFeshfeshe;
    }

    public void setActiveFeshfeshe(int activeFeshfeshe) {
        this.activeFeshfeshe = activeFeshfeshe;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public int getResult() {
        return Result;
    }

    public void setResult(int result) {
        Result = result;
    }

    public boolean isExpire() {
        return IsExpire;
    }

    public void setExpire(boolean expire) {
        IsExpire = expire;
    }

    public int getCountNotify() {
        return CountNotify;
    }

    public void setCountNotify(int countNotify) {
        CountNotify = countNotify;
    }

    public int getCountTicket() {
        return countTicket;
    }

    public void setCountTicket(int countTicket) {
        this.countTicket = countTicket;
    }

    //    @Column(Name = "userId")
//    public long userId;
//    @Column(Name = "rTraffic")
//    public int rTraffic;
//    @Column(Name = "rDay")
//    public int rDay;
//    @Column(Name = "rHour")
//    public double rHour;
//    @Column(Name = "tPerc")
//    public int tPerc;
//    @Column(Name = "dPerc")
//    public int dPerc;
//    @Column(Name = "hPerc")
//    public int hPerc;
//    @Column(Name = "grpId")
//    public long grpId;
//    @Column(Name = "grpName")
//    public String grpName = "";
//    @Column(Name = "pkgName")
//    public String pkgName = "";
//    @Column(Name = "lastNewsID")
//    public int lastNewsID;
//    @Column(Name = "lastTicketID")
//    public int lastTicketID;
//    @Column(Name = "lastNotifyID")
//    public int lastNotifyID;
//    @Column(Name = "online")
//    public boolean online;
//    @Column(Name = "balance")
//    public int balance;
//    @Column(Name = "expDate")
//    public String expDate = "";
//    @Column(Name = "farsiExpDate")
//    public String farsiExpDate = "";
//    @Column(Name = "regConnect")
//    public boolean regConnect;
//    @Column(Name = "remaindTrafficSplit")
//    public int remaindTrafficSplit;
//    @Column(Name = "activeTrafficSplit")
//    public int activeTrafficSplit;
//    @Column(Name = "remaindFeshfeshe")
//    public int remaindFeshfeshe;
//    @Column(Name = "activeFeshfeshe")
//    public int activeFeshfeshe;
//    @Column(Name = "transferedTraffic")
//    public int transferedTraffic;
//    @Column(Name = "serviceTraffic")
//    public int serviceTraffic ;
//    @Column(Name = "nonTransferedTraffic")
//    public int nonTransferedTraffic ;

}
