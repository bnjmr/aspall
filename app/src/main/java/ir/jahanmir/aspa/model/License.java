package ir.jahanmir.aspa.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

/**
 * Created by Microsoft on 3/7/2016.
 */
@Table(name = "License")
public class License extends Model {
    //لایسنس برای مشترکی که لاگین کرده است
    @Expose
    @Column(name = "Chargeonline")
    public boolean Chargeonline;

    @Expose
    @Column(name = "PayOnline")
    public boolean PayOnline;

    @Expose
    @Column(name = "Ticket")
    public boolean Ticket;

    @Expose
    @Column(name = "ChangePass")
    public boolean ChangePass;

    @Expose
    @Column(name = "MobileAdv")
    public boolean MobileAdv;

    @Expose
    @Column(name = "Club")
    public boolean Club;

    @Expose
    @Column(name = "Graph")
    public boolean Graph;

    @Expose
    @Column(name = "Feshfeshe")
    public boolean Feshfeshe;

    @Expose
    @Column(name = "remain")
    public boolean remain;

    @Expose
    @Column(name = "remainDay")
    public boolean remainDay;

    @Expose
    @Column(name = "remainTraffic")
    public boolean remainTraffic;

    @Expose
    @Column(name = "connection")
    public boolean connection;

    @Expose
    @Column(name = "trafficSplit")
    public boolean trafficSplit;

    @Expose
    @Column(name = "Factor")
    public boolean Factor;

    @Expose
    @Column(name = "Payment")
    public boolean Payment;

    @Expose
    @Column(name = "profile")
    public boolean profile;

    @Expose
    @Column(name = "Document")
    public boolean Document;

    @Expose
    @Column(name = "Message")
    public String Message;

    @Expose
    @Column(name = "Result")
    public int Result;
}
