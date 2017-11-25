package ir.jahanmir.araxx.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Microsoft on 3/7/2016.
 */
public class UserInfoResponse {
    @SerializedName("Result")
    public int result;
    @SerializedName("username")
    public String username = "";
    @SerializedName("FullName")
    public String fullName = "";
    @SerializedName("FirstCon")
    public String firstCon = "";
    @SerializedName("LastCon")
    public String lastCon = "";
    @SerializedName("MobileNo")
    public String mobileNo = "";
    @SerializedName("ServiceType")
    public String serviceType = "";
    @SerializedName("Status")
    public String status = "";
    @SerializedName("ResellerName")
    public String resellerName = "";
    @SerializedName("unit")
    public String unit = "";
}
