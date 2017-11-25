package ir.jahanmir.araxx.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by FCC on 10/10/2017.
 */

public class TrafficSplitNotMainModel {

    public String des;
    public int day;
    @SerializedName("package")
    public String Package;
    public String date;
    public long factorCode;
    public long code;

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getPackage() {
        return Package;
    }

    public void setPackage(String aPackage) {
        Package = aPackage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getFactorCode() {
        return factorCode;
    }

    public void setFactorCode(long factorCode) {
        this.factorCode = factorCode;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }
}
