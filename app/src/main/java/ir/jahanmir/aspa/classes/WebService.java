package ir.jahanmir.aspa.classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ProgressBar;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import de.greenrobot.event.EventBus;
import ir.jahanmir.aspa.G;
import ir.jahanmir.aspa.enums.EnumInternetErrorType;
import ir.jahanmir.aspa.enums.EnumResponse;
import ir.jahanmir.aspa.enums.EventOnSuccessGoToMainTraffic;
import ir.jahanmir.aspa.events.EventOnErrorInConnectingToServer;
import ir.jahanmir.aspa.events.EventOnFailStartTrafficSplit;
import ir.jahanmir.aspa.events.EventOnGetAvatarImageResponse;
import ir.jahanmir.aspa.events.EventOnGetErrorAddTicket;
import ir.jahanmir.aspa.events.EventOnGetErrorChangePassword;
import ir.jahanmir.aspa.events.EventOnGetErrorChargeOnlineForLoadGroups;
import ir.jahanmir.aspa.events.EventOnGetErrorChargeOnlineForLoadPackages;
import ir.jahanmir.aspa.events.EventOnGetErrorChargeOnlineForSelectPackage;
import ir.jahanmir.aspa.events.EventOnGetErrorChargeOnlineForTamdid;
import ir.jahanmir.aspa.events.EventOnGetErrorCheckChargeOnlineClub;
import ir.jahanmir.aspa.events.EventOnGetErrorCheckTaraz;
import ir.jahanmir.aspa.events.EventOnGetErrorClubScores;
import ir.jahanmir.aspa.events.EventOnGetErrorEndFeshFeshes;
import ir.jahanmir.aspa.events.EventOnGetErrorGetBankList;
import ir.jahanmir.aspa.events.EventOnGetErrorGetChargeOnlineMainItems;
import ir.jahanmir.aspa.events.EventOnGetErrorGetCities;
import ir.jahanmir.aspa.events.EventOnGetErrorGetClubScore;
import ir.jahanmir.aspa.events.EventOnGetErrorGetConnections;
import ir.jahanmir.aspa.events.EventOnGetErrorGetCurrentFeshFeshes;
import ir.jahanmir.aspa.events.EventOnGetErrorGetFactor;
import ir.jahanmir.aspa.events.EventOnGetErrorGetFactorDetail;
import ir.jahanmir.aspa.events.EventOnGetErrorGetGraph;
import ir.jahanmir.aspa.events.EventOnGetErrorGetNews;
import ir.jahanmir.aspa.events.EventOnGetErrorGetNotifies;
import ir.jahanmir.aspa.events.EventOnGetErrorGetPoll;
import ir.jahanmir.aspa.events.EventOnGetErrorGetTicketDetails;
import ir.jahanmir.aspa.events.EventOnGetErrorGetTickets;
import ir.jahanmir.aspa.events.EventOnGetErrorGetUnits;
import ir.jahanmir.aspa.events.EventOnGetErrorGetUserAccountInfo;
import ir.jahanmir.aspa.events.EventOnGetErrorGetUserInfo;
import ir.jahanmir.aspa.events.EventOnGetErrorIspInfo;
import ir.jahanmir.aspa.events.EventOnGetErrorIspUrl;
import ir.jahanmir.aspa.events.EventOnGetErrorLoadFeshFeshes;
import ir.jahanmir.aspa.events.EventOnGetErrorLocations;
import ir.jahanmir.aspa.events.EventOnGetErrorLogin;
import ir.jahanmir.aspa.events.EventOnGetErrorPayFactorFromCredit;
import ir.jahanmir.aspa.events.EventOnGetErrorRegConnect;
import ir.jahanmir.aspa.events.EventOnGetErrorRegisterCustomer;
import ir.jahanmir.aspa.events.EventOnGetErrorSelectFactor;
import ir.jahanmir.aspa.events.EventOnGetErrorSendRememberPassword;
import ir.jahanmir.aspa.events.EventOnGetErrorSetPoll;
import ir.jahanmir.aspa.events.EventOnGetErrorStartFactor;
import ir.jahanmir.aspa.events.EventOnGetErrorStartFeshFeshes;
import ir.jahanmir.aspa.events.EventOnGetErrorUserLicense;
import ir.jahanmir.aspa.events.EventOnGetErrorVisitMobile;
import ir.jahanmir.aspa.events.EventOnNoAccessServerResponse;
import ir.jahanmir.aspa.events.EventOnRemoveAvatarImageResponse;
import ir.jahanmir.aspa.events.EventOnStartUploadingAvatar;
import ir.jahanmir.aspa.events.EventOnStopUploadingAvatar;
import ir.jahanmir.aspa.events.EventOnSuccessEndTrafficSplit;
import ir.jahanmir.aspa.events.EventOnSuccessStartTrafficSplit;
import ir.jahanmir.aspa.events.EventOnSuccessUploadDocument;
import ir.jahanmir.aspa.model.Locations;
import ir.jahanmir.aspa.model.ModelRegisterCustomer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WebService {
    private static int timeOut = 30;

    //جستجوی شرکت ها  http://mng.aspacrm.ir/service.aspx
    public static void sendGetIspUrlRequest(long customerId) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "GetISPUrl")
                .add("Id", "" + customerId)
                .build();
        Request request = new Request.Builder().url(G.JMWS).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorIspUrl(EnumInternetErrorType.NO_INTERNET_ACCESS));
                //U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorIspUrl(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.getIspUrlResponse(response.body().string());
            }
        });
    }

    //جستجوی شرکت ها  http://mng.aspacrm.ir/service.aspx
//    public static void sendGetIspListRequest(String Des) {
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .build();
//        RequestBody body = new FormBody.Builder()
//                .add("rt", "SearchISP")
//                .add("Des", Des)
//                .build();
//        Request request = new Request.Builder().url(G.JMWS).post(body).build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
//                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
//            }
//            //////////////
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (!response.isSuccessful()) {
//                    EventBus.getDefault().post(new EventOnGetErrorIspList());
//                    return;
//                }
//                JsonParser.getIspListResponse(response.body().string());
//            }
//        });
//    }

    //گرفتن اطلاعات شرکت برای نمایش دادن در صفحه لاگین
    public static void sendGetIspInfoLoginRequest(String ispURL) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder().url(G.JMWS).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorIspInfo());
                    return;
                }
                JsonParser.getIspInfoResponse(response.body().string());
            }
        });
    }

    //لاکین کردن
    public static void sendLoginRequest(final String ispURL, final long ispId, String username, String password) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
//                .add("rt", "Authenticate")
                .add("DeviceName", "" + U.getDeviceName())
                .add("DeviceModel", "" + U.getDeviceModel())
                .add("OsVer", "" + U.getOSVersion())
                .add("ResW", "" + U.getDeviceWidth())
                .add("ResH", "" + U.getDeviceHeight())
                .add("Density", "" + U.getDeviceDensity())
                .add("username", "" + U.persianToDecimal(username))
                .add("password", "" + U.persianToDecimal(password))
                .add("imeis", "" /*+ U.getIMEI()*/)
                .build();

        Logger.d("Webservice : username is " + U.persianToDecimal(username));
        Logger.d("Webservice : password is " + U.persianToDecimal(password));
        Logger.d("Webservice : ispURL is " + G.Api + "login/Authenticate");

        Request request = new Request.Builder().url(G.Api + "login/Authenticate").post(body).build();
//        Logger.d("WebService : rt is " + "Authenticate");
//        Logger.d("WebService : deviceName is " + U.getDeviceName());
//        Logger.d("WebService : DeviceModel is " +  U.getDeviceModel());
//        Logger.d("WebService : OsVer is " + U.getOSVersion());
//        Logger.d("WebService : ResW is " + U.getDeviceWidth());
//        Logger.d("WebService : ResH is " + U.getDeviceHeight());
//        Logger.d("WebService : Density is " + U.getDeviceDensity());
//        Logger.d("WebService : username is " + username);
//        Logger.d("WebService : password is " + password);
//        Logger.d("WebService : imei is " + U.getIMEI());
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Logger.d("Webservice : onResponse " + res);
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorLogin());
                    return;
                }
                JsonParser.getLoginResponse(res, ispId, ispURL);
            }
        });
    }

    //گرفتن لایسنس برای مشترکی که لاگین کرده است
    public static void sendGetUserLicenseRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder().url(G.Api + "BaseInfo/GetLicence?Token=" + G.currentUser.Token).get().build();
        Logger.d("WebService : sendGetUserLicenseRequest rt is " + "GetLicence");
        Logger.d("WebService : sendGetUserLicenseRequest Token is " + G.currentUser.Token);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorUserLicense());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorUserLicense());
                    return;
                }
                JsonParser.getUserLicenseResponse(response.body().string());
            }
        });
    }

    //گرفتن اطلاعات اکانت مشترک
    public static void sendGetUserAccountInfoRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder().url(G.Api + "/BaseInfo/GetRemain?Token=" + G.currentUser.Token).get().build();
        Logger.d("WebService : sendGetUserAccountInfoRequest url is " + G.Api);
        Logger.d("WebService : sendGetUserAccountInfoRequest rt is " + "GetRemain");
        Logger.d("WebService : sendGetUserAccountInfoRequest Token is " + G.currentUser.Token);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorGetUserAccountInfo(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetUserAccountInfo(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.getUserAccountInfoResponse(response.body().string());
            }
        });
    }

    //گرفتن اطلاعات مشترکی که لاگین کرده است
    public static void sendGetUserInfoRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("Token", "" + G.currentUser.Token)
                .build();
        Request request = new Request.Builder().url(G.Api + "BaseInfo/GetUserProfile?Token=" + G.currentUser.Token).get().build();
        Logger.d("WebService : sendGetUserInfoRequest rt is " + "GetUserProfile");
        Logger.d("WebService : sendGetUserInfoRequest Token is " + G.currentUser.Token);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetUserInfo());
                    return;
                }
                JsonParser.getGetUserInfoResponse(response.body().string());
            }
        });
    }

    //بازیابی رمز عبور توسط نام کاربری و شماره موبایل ذخیره در سیستم
    public static void sendRememberPassRequest(String username, String mobile, String ispUrl) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
//                .add("rt", "RememberPass")
                .add("username", "" + username)
                .add("mobile", "" + mobile)
                .build();
        Logger.d("WebService : sendRememberPassRequest rt is " + "RememberPass");
        Logger.d("WebService : sendRememberPassRequest Username is " + username);
        Logger.d("WebService : sendRememberPassRequest Mobile is " + mobile);

        Request request = new Request.Builder().url(G.Api + "login/RememberPass").post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorSendRememberPassword());
                    return;
                }
                JsonParser.sendRememberPasswordResponse(response.body().string());
            }
        });
    }

    //تغییر رمز مشترک
    public static void sendChangePasswordRequest(String currentPassword, String newPassword) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
//                .add("rt", "ChangePassword")
                .add("Token", "" + G.currentUser.Token)
                .add("OldPass", currentPassword)
                .add("NewPass", newPassword)
                .build();
        Logger.d("WebService : sendChangePasswordRequest rt is " + "ChangePassword");
        Logger.d("WebService : sendChangePasswordRequest Token is " + G.currentUser.Token);
        Logger.d("WebService : sendChangePasswordRequest LastPass is " + currentPassword);
        Logger.d("WebService : sendChangePasswordRequest NewPass is " + newPassword);

        Request request = new Request.Builder().url(G.Api + "login/ChangePassword").post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorChangePassword());
                    return;
                }
                JsonParser.getChangePasswordResponse(response.body().string());
            }
        });
    }

    /* WebService for Payment*/
    //گرفتن همه پرداخت های مشترک بر اساس تعدادی که در مدیریت اجازه داده شده است
    public static void sendGetPaymentRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
//        RequestBody body = new FormBody.Builder()
//                .add("rt", "GetPayments")
//                .add("UserID", "" + G.currentUser.userId)
//                .add("ExKey", "" + G.currentUser.exKey)
//                .build();
        Logger.d("WebService : sendGetPaymentRequest rt is " + "GetPayments");
        Logger.d("WebService : sendGetPaymentRequest Token is " + G.currentUser.Token);

        Request request = new Request.Builder().url(G.Api + "Payments/GetPayments?Token=" + G.currentUser.Token).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorChangePassword());
                    return;
                }
                JsonParser.getPaymentResponse(response.body().string());
            }
        });
    }

    /* WebService for Factor*/
    //گرفتن کلیه فاکتورهای مشترک بر اساس تعدادی که در قسمت مدیریت اجازه داده شده است`
    public static void sendGetFactorRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Logger.d("WebService : sendGetFactorRequest rt is " + "GetFactors");
        Logger.d("WebService : sendGetFactorRequest UserID is " + G.currentUser.Token);

        Request request = new Request.Builder().url(G.Api + "Factor/GetFactors?Token=" + G.currentUser.Token).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetFactor());
                    return;
                }
                JsonParser.getFactorResponse(response.body().string());
            }
        });
    }

    //گرفتن جزییات فاکتور
    public static void sendGetFactorDetailRequest(final long factorCode) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Logger.d("WebService : sendGetFactorDetailRequest rt is " + "GetFactorDetails");
        Logger.d("WebService : sendGetFactorDetailRequest Token is " + G.currentUser.Token);
        Logger.d("WebService : sendGetFactorDetailRequest FactorCode is " + factorCode);

        Request request = new Request.Builder().url(G.Api + "Factor/GetFactorDetails?FactorCode=" + factorCode + "&Token=" + G.currentUser.Token).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorGetFactorDetail(EnumInternetErrorType.NO_INTERNET_ACCESS, factorCode));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetFactorDetail(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED, factorCode));
                    return;
                }
                JsonParser.getFactorDetailResponse(response.body().string(), factorCode);
            }
        });
    }

    //شروع کردن فاکتور
    public static void sendSelectFactorRequest(long factorCode) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        Logger.d("WebService : sendStartFactorRequest Token is " + G.currentUser.Token);
        Logger.d("WebService : sendStartFactorRequest FactorCode is " + factorCode);

        Request request = new Request.Builder().url(G.Api + "Factor/SelectFactor?FactorCode=" + factorCode + "&Token=" + G.currentUser.Token).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorSelectFactor());
                    return;
                }
                JsonParser.getSelectFactorResponse(response.body().string());
            }
        });
    }

    //انتخاب کردن فاکتور
    public static void sendStartFactorRequest(long factorCode) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("Token", "" + G.currentUser.Token)
                .add("FactorCode", "" + factorCode)
                .build();
        Logger.d("WebService : sendStartFactorRequest rt is " + "GetFactors");
        Logger.d("WebService : sendStartFactorRequest UserID is " + G.currentUser.Token);
        Logger.d("WebService : sendStartFactorRequest FactorCode is " + factorCode);

        Request request = new Request.Builder().url(G.Api + "Factor/StartFactor").post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorStartFactor(EnumInternetErrorType.NO_INTERNET_ACCESS));
                //U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorStartFactor(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.getStartFactorResponse(response.body().string());
            }
        });
    }

    /* WebService for Ticket*/
    //گرفتن واحد ها
    public static void sendGetUnitsRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        Logger.d("WebService : sendGetUnitsRequest rt is " + "GetUnits");
        Logger.d("WebService : sendGetUnitsRequest Token is " + G.currentUser.Token);

        Request request = new Request.Builder().url(G.Api + "Ticket/GetUnits?Token=" + G.currentUser.Token).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetUnits());
                    return;
                }
                JsonParser.getUnitsResponse(response.body().string());
            }
        });
    }

    //اضافه کردن تیکت
    public static void sendAddTicketRequest(String title, long unitCode, int priority, String des, int childCode) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
//                .add("rt", "AddTicket")
                .add("Token", "" + G.currentUser.Token)
                .add("Title", "" + title)
                .add("UnitCode", "" + unitCode)
                .add("Priority", "" + priority)
                .add("Des", "" + des)
                .add("titleCode", "" + childCode)
                .build();
        Logger.d("WebService : sendAddTicketRequest rt is " + "AddTicket");
        Logger.d("WebService : sendAddTicketRequest Token is " + G.currentUser.Token);
        Logger.d("WebService : sendAddTicketRequest Title is " + title);
        Logger.d("WebService : sendAddTicketRequest UnitCode is " + unitCode);
        Logger.d("WebService : sendAddTicketRequest Priority is " + priority);
        Logger.d("WebService : sendAddTicketRequest Des is " + des);

        Request request = new Request.Builder().url(G.Api + "Ticket/AddTicket").post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorAddTicket());
                    return;
                }
                JsonParser.getAddTicketResponse(response.body().string());
            }
        });
    }

    //ثبت جزییات تیکت
    public static void sendAddTicketDetailRequest(long ticketCode, long unitCode, String des) {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        RequestBody body = new FormBody.Builder()
                .add("Token", "" + G.currentUser.Token)
                .add("TicketCode", "" + ticketCode)
                .add("Des", "" + des)
                .build();

        Logger.d("WebService : sendAddTicketDetailRequest rt is " + "AddTicketDetail");
        Logger.d("WebService : sendAddTicketDetailRequest Token is " + G.currentUser.Token);
        Logger.d("WebService : sendAddTicketDetailRequest TicketCode is " + ticketCode);
        Logger.d("WebService : sendAddTicketDetailRequest Des is " + des);

        Request request = new Request.Builder().url(G.Api + "Ticket/AddTicketDetail").post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorAddTicket());
                    return;
                }
                JsonParser.getAddTicketDetailResponse(response.body().string());
            }
        });
    }

    //گرفتن همه تیکت های مشترک
    public static void sendGetTicketsRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();


        Logger.d("WebService : sendGetTicketsRequest rt is " + "GetTickets");
        Logger.d("WebService : sendGetTicketsRequest Token is " + G.currentUser.Token);

        Request request = new Request.Builder().url(G.Api + "Ticket/GetTickets?Token=" + G.currentUser.Token).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorGetTickets(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetTickets(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.getTicketsResponse(response.body().string());
            }
        });
    }

    //گرفتن جزییات تیکت
    public static void sendGetTicketDetailsRequest(final long ticketCode) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Logger.d("WebService : sendGetTicketDetailsRequest rt is " + "GetTicketDetails");
        Logger.d("WebService : sendGetTicketDetailsRequest Token is " + G.currentUser.Token);
        Logger.d("WebService : sendGetTicketDetailsRequest TicketCode is " + ticketCode);

        Request request = new Request.Builder().url(G.Api + "Ticket/GetTicketDetails?TicketCode=" + ticketCode + "&Token=" + G.currentUser.Token).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetTicketDetails());
                    return;
                }
                JsonParser.getTicketDetailsResponse(response.body().string(), ticketCode);
            }
        });
    }

    /* WebService for Connect */
    //اجازه وصل موقت دارد یا نه
//    public static void sendRegConnectAllowRequest() {
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .build();
//        RequestBody body = new FormBody.Builder()
//                .add("rt", "RegConnectAllow")
//                .add("UserID", "" + G.currentUser.userId)
//                .add("ExKey", "" + G.currentUser.exKey)
//                .build();
//
//        Logger.d("WebService : sendRegConnectAllowRequest rt is " + "RegConnectAllow");
//        Logger.d("WebService : sendRegConnectAllowRequest UserID is " + G.currentUser.userId);
//        Logger.d("WebService : sendRegConnectAllowRequest ExKey is " + G.currentUser.exKey);
//
//        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
//                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (!response.isSuccessful()) {
//                    EventBus.getDefault().post(new EventOnGetErrorRegConnectAllow());
//                    return;
//                }
//                JsonParser.getRegConnectAllowResponse(response.body().string());
//            }
//        });
//    }

    //وصل موقت
    public static void sendRegConnectRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("Token", "" + G.currentUser.Token)
                .build();

        Logger.d("WebService : sendRegConnectRequest rt is " + "RegConnect");
        Logger.d("WebService : sendRegConnectRequest Token is " + G.currentUser.Token);

        Request request = new Request.Builder().url(G.Api + "Connect/RegConnect").post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorRegConnect(EnumInternetErrorType.NO_INTERNET_ACCESS));
                //U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorRegConnect(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.getRegConnectResponse(response.body().string());
            }
        });
    }

    /* WebService for Connections*/
    //گرفتن سوابق اتصال مشترک بر اساس تاریخ
    public static void sendGetConnectionsRequest(String startDate, String endDate, String netType) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Logger.d("WebService : sendGetConnectionsRequest rt is " + "RegConnect");
        Logger.d("WebService : sendGetConnectionsRequest Token is " + G.currentUser.Token);
        Logger.d("WebService : sendGetConnectionsRequest StartDate is " + startDate);
        Logger.d("WebService : sendGetConnectionsRequest EndDate is " + endDate);
        Logger.d("WebService : sendGetConnectionsRequest netType is " + netType);

        Request request = new Request.Builder().url(G.Api + "Connections/GetConnections?startDate="
                + startDate +
                "&endDate=" + endDate +
                "&Token=" + G.currentUser.Token + "&sub_service_name=" + netType
        ).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetConnections());
                    return;
                }
                JsonParser.getConnectionsResponse(response.body().string());
            }
        });
    }

    //گرفتن گراف مصرف مشترک بر اساس تاریخ ، بیش از یک ماه اجازه داده نمی شود
    public static void sendGetGraphRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Logger.d("WebService : sendGetGraphRequest rt is " + "GetGraph");
        Logger.d("WebService : sendGetGraphRequest Token is " + G.currentUser.Token);
        Logger.d("WebService : sendGetGraphRequest StartDate is " + U.getDate(-10));
        Logger.d("WebService : sendGetGraphRequest EndDate is " + U.getCurrentDate());

        Request request = new Request.Builder().url(G.Api + "BaseInfo/Graph?Start=" + U.getDate(-10) + "&End=" + U.getCurrentDate() + "&Type=1&Token=" +
                G.currentUser.Token).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorGetGraph(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetGraph(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.getGraphResponse(response.body().string());
            }
        });
    }

    /* WebService for Online*/
    //صدا زدن آدرس برای انتقال به صفحه شارژ آنلاین
//    public static void sendChargeOnlineRequest() {
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .build();
//        RequestBody body = new FormBody.Builder()
//                .add("rt", "ChargeOnline")
//                .add("UserID", "" + G.currentUser.userId)
//                .add("ExKey", "" + G.currentUser.exKey)
//                .build();
//
//        Logger.d("WebService : sendChargeOnlineRequest rt is " + "ChargeOnline");
//        Logger.d("WebService : sendChargeOnlineRequest UserID is " + G.currentUser.userId);
//        Logger.d("WebService : sendChargeOnlineRequest ExKey is " + G.currentUser.exKey);
//
//        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
//                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (!response.isSuccessful()) {
//                    EventBus.getDefault().post(new EventOnGetErrorChargeOnline());
//                    return;
//                }
//                JsonParser.getChargeOnlineResponse(response.body().string());
//            }
//        });
//    }

//    //صدا زدن آدرس برای انتقال به صفحه پرداخت آنلاین
//    public static void sendPayOnlineRequest() {
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .build();
//        RequestBody body = new FormBody.Builder()
//                .add("rt", "PayOnline")
//                .add("UserID", "" + G.currentUser.userId)
//                .add("ExKey", "" + G.currentUser.exKey)
//                .build();
//
//        Logger.d("WebService : sendPayOnlineRequest rt is " + "PayOnline");
//        Logger.d("WebService : sendPayOnlineRequest UserID is " + G.currentUser.userId);
//        Logger.d("WebService : sendPayOnlineRequest ExKey is " + G.currentUser.exKey);
//
//        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
//                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (!response.isSuccessful()) {
//                    EventBus.getDefault().post(new EventOnGetErrorPayOnline());
//                    return;
//                }
//                JsonParser.getPayOnlineResponse(response.body().string());
//            }
//        });
//    }

    //ارسال درخواست گرفتن کد ussd برای شارژ آنلاین
//    public static void sendChargeOnlineForPayRequest(long factorCode) {
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .build();
//        RequestBody body = new FormBody.Builder()
//                .add("rt", "ChargeOnlineForPay")
//                .add("UserID", "" + G.currentUser.userId)
//                .add("ExKey", "" + G.currentUser.exKey)
//                .add("FactorCode", "" + factorCode)
//                .build();
//        Logger.d("WebService : sendChargeOnlineForPayRequest rt is " + "ChargeOnlineForPay");
//        Logger.d("WebService : sendChargeOnlineForPayRequest UserID is " + G.currentUser.userId);
//        Logger.d("WebService : sendChargeOnlineForPayRequest ExKey is " + G.currentUser.exKey);
//        Logger.d("WebService : sendChargeOnlineForPayRequest FactorCode is " + factorCode);
//
//        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                EventBus.getDefault().post(new EventOnGetErrorChargeOnlineForPayRequest(EnumInternetErrorType.NO_INTERNET_ACCESS));
//                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (!response.isSuccessful()) {
//                    EventBus.getDefault().post(new EventOnGetErrorChargeOnlineForPayRequest(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
//                    return;
//                }
//                JsonParser.getChargeOnlineForPayRequest(response.body().string());
//            }
//        });
//    }

    //ارسال درخواست گرفتن کد ussd
//    public static void sendPayOnlineForPayRequest(int money) {
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .build();
//        RequestBody body = new FormBody.Builder()
//                .add("rt", "PayOnlineForPay")
//                .add("UserID", "" + G.currentUser.userId)
//                .add("ExKey", "" + G.currentUser.exKey)
//                .add("Money", "" + money)
//                .build();
//        Logger.d("WebService : sendPayOnlineForPayRequest rt is " + "PayOnlineForPay");
//        Logger.d("WebService : sendPayOnlineForPayRequest UserID is " + G.currentUser.userId);
//        Logger.d("WebService : sendPayOnlineForPayRequest ExKey is " + G.currentUser.exKey);
//        Logger.d("WebService : sendPayOnlineForPayRequest Money is " + money);
//
//        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                EventBus.getDefault().post(new EventOnGetErrorPayOnlineForPay(EnumInternetErrorType.NO_INTERNET_ACCESS));
//                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (!response.isSuccessful()) {
//                    EventBus.getDefault().post(new EventOnGetErrorPayOnlineForPay(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
//                    return;
//                }
//                JsonParser.getPayOnlineForPayRequest(response.body().string());
//            }
//        });
//    }

//    public static void sendCheckOrderIdResultRequest(long orderId) {
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .build();
//        RequestBody body = new FormBody.Builder()
//                .add("rt", "CheckOrderIdResult")
//                .add("UserID", "" + G.currentUser.userId)
//                .add("ExKey", "" + G.currentUser.exKey)
//                .add("OrderId", "" + orderId)
//                .build();
//        Logger.d("WebService : sendCheckOrderIdResultRequest rt is " + "CheckOrderIdResult");
//        Logger.d("WebService : sendCheckOrderIdResultRequest UserID is " + G.currentUser.userId);
//        Logger.d("WebService : sendCheckOrderIdResultRequest ExKey is " + G.currentUser.exKey);
//        Logger.d("WebService : sendCheckOrderIdResultRequest OrderId is " + orderId);
//
//        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                EventBus.getDefault().post(new EventOnGetErrorCheckOrderIdResult(EnumInternetErrorType.NO_INTERNET_ACCESS));
//                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (!response.isSuccessful()) {
//                    EventBus.getDefault().post(new EventOnGetErrorCheckOrderIdResult(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
//                    return;
//                }
//                JsonParser.getCheckOrderIdResultRequest(response.body().string());
//            }
//        });
//    }

    // لود کردن لیست بانک ها
    public static void sendGetBankList() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        Logger.d("WebService : sendGetBankList rt is " + "LoadBanks");
        Logger.d("WebService : sendGetBankList Token is " + G.currentUser.Token);

        Request request = new Request.Builder().url(G.Api + "ChargeOnline/loadBanks?Token=" + G.currentUser.Token).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorGetBankList(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetBankList(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.getBankListRequest(response.body().string());
            }
        });
    }

    // چک کردن تراز مالی
    public static void sendCheckTaraz(long factorCode) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        Logger.d("WebService : sendCheckTaraz url is " + G.currentUser.ispUrl + G.WS_PAGE);
        Logger.d("WebService : sendCheckTaraz rt is " + "CheckTarazForPaymentThisFactor");
        Logger.d("WebService : sendCheckTaraz Token is " + G.currentUser.Token);
        Logger.d("WebService : sendCheckTaraz FactorCode is " + factorCode);

        Request request = new Request.Builder().url(G.Api + "ChargeOnline/checkTarazForPaymentThisFactor?FactorCode=" + factorCode + "&Token=" + G.currentUser.Token).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorCheckTaraz(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorCheckTaraz(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.getCheckTarazRequest(response.body().string());
            }
        });
    }

    // پرداخت توسط اعتبار
    public static void sendPayFactorFromCredit(long factorCode) {

        OkHttpClient client = new OkHttpClient();
        new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("Token", "" + G.currentUser.Token)
                .add("FactorCode", "" + factorCode)
                .build();

        Logger.d("WebService : sendPayFactorFromCredit rt is " + "PayFactorFromCredit");
        Logger.d("WebService : sendPayFactorFromCredit Token is " + G.currentUser.Token);
        Logger.d("WebService : sendPayFactorFromCredit FactorCode is " + factorCode);

        Request request = new Request.Builder().url(G.Api + "ChargeOnline/payFactorFromCredit").post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorPayFactorFromCredit(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorPayFactorFromCredit(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.getPayFactorFromCreditRequest(response.body().string());
            }
        });
    }

    /* WebService for Club*/
    //گرفتن کلیه امتیازهایی که مشترک گرفته است
    public static void sendClubScoresRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Logger.d("WebService : sendClubScoresRequest rt is " + "ClubScores");
        Logger.d("WebService : sendClubScoresRequest Token is " + G.currentUser.Token);

        Request request = new Request.Builder().url(G.Api + "Club/ClubScores?Token=" + G.currentUser.Token).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorClubScores());
                    return;
                }
                JsonParser.getClubScoresResponse(response.body().string());
            }
        });
    }

    //گرفتن کل امتیاز مشترک
    public static void sendGetClubScoreRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Logger.d("WebService : sendClubScoresRequest rt is " + "GetClubScore");
        Logger.d("WebService : sendClubScoresRequest Token is " + G.currentUser.Token);

        Request request = new Request.Builder().url(G.Api + "Club/GetClubScore?Token=" + G.currentUser.Token).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetClubScore());
                    return;
                }
                JsonParser.getClubScoreResponse(response.body().string());
            }
        });
    }

    //گرفتن کلیه فشفشه های مشترک
    public static void sendLoadFeshFeshesRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Logger.d("WebService : sendLoadFeshFeshesRequest rt is " + "LoadFeshFeshes");
        Logger.d("WebService : sendLoadFeshFeshesRequest Token is " + G.currentUser.Token);

        Request request = new Request.Builder().url(G.Api + "Packages/LoadFeshfeshe?Token=" + G.currentUser.Token).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorLoadFeshFeshes());
                    return;
                }
                JsonParser.getLoadFeshFeshesResponse(response.body().string());
            }
        });
    }

    //شروع کردن فشفشه
    public static void sendStartFeshFeshesRequest(long code) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "StartFeshFeshe")
                .add("Token", "" + G.currentUser.Token)
                .add("code", "" + code)
                .build();
        Logger.d("WebService : sendStartFeshFeshesRequest rt is " + "StartFeshFeshe");
        Logger.d("WebService : sendStartFeshFeshesRequest Token is " + G.currentUser.Token);
        Logger.d("WebService : sendStartFeshFeshesRequest code is " + code);
        Request request = new Request.Builder().url(G.Api + "Packages/StartFeshfeshe").post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorStartFeshFeshes());
                    return;
                }
                JsonParser.getStartFeshFeshesResponse(response.body().string());
            }
        });
    }

    //گرفتن اطلاعات فشفشه جاری
    public static void sendGetCurrentFeshFeshesRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Logger.d("WebService : sendStartFeshFeshesRequest rt is " + "CurrentFeshFeshe");
        Logger.d("WebService : sendStartFeshFeshesRequest Token is " + G.currentUser.Token);

        Request request = new Request.Builder().url(G.Api + "Packages/GetCurrentFeshfeshe?Token=" + G.currentUser.Token).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetCurrentFeshFeshes());
                    return;
                }
                JsonParser.getCurrentFeshFeshesResponse(response.body().string());
            }
        });
    }

    //پایان دادن به فشفشه
    public static void sendGetEndFeshFeshesRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("Token", "" + G.currentUser.Token)
                .build();

        Logger.d("WebService : sendGetEndFeshFeshesRequest rt is " + "EndFeshFeshe");
        Logger.d("WebService : sendGetEndFeshFeshesRequest Token is " + G.currentUser.Token);

        Request request = new Request.Builder().url(G.Api + "Packages/EndFeshfeshe").post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorEndFeshFeshes());
                    return;
                }
                JsonParser.getEndFeshFeshesResponse(response.body().string());
            }
        });
    }

    /* WebService for News*/
    //گرفتن همه اخبارها
    public static void sendGetNewsRequest(long code) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        Logger.d("WebService : sendGetNewsRequest rt is " + "GetNews");
        Logger.d("WebService : sendGetNewsRequest Token is " + G.currentUser.Token);
        Logger.d("WebService : sendGetNewsRequest code is " + code);

        Request request = new Request.Builder().url(G.Api + "News/GetNews?Code=" + code + "&Token=" + G.currentUser.Token).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorGetNews(EnumInternetErrorType.NO_INTERNET_ACCESS));
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetNews(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.getNewsResponse(response.body().string());
            }
        });
    }

    //گرفتن پیام هشدار
//    public static void sendGetAlertRequest(long code) {
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .build();
//        RequestBody body = new FormBody.Builder()
//                .add("rt", "GetAlert")
//                .add("UserID", "" + G.currentUser.userId)
//                .add("ExKey", "" + G.currentUser.exKey)
//                .add("code", "" + code)
//                .build();
//        Logger.d("WebService : sendGetAlertRequest rt is " + "GetNews");
//        Logger.d("WebService : sendGetAlertRequest UserID is " + G.currentUser.userId);
//        Logger.d("WebService : sendGetAlertRequest ExKey is " + G.currentUser.exKey);
//        Logger.d("WebService : sendGetAlertRequest code is " + code);
//
//        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
//                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (!response.isSuccessful()) {
//                    EventBus.getDefault().post(new EventOnGetErrorGetAlert());
//                    return;
//                }
//                JsonParser.getAlertResponse(response.body().string());
//            }
//        });
//    }

    //گرفتن نظرسنجی
    public static void sendGetPollRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        Logger.d("WebService : sendGetNotifiesRequest rt is " + "GetPoll");
        Logger.d("WebService : sendGetNotifiesRequest Token is " + G.currentUser.Token);

        Request request = new Request.Builder().url(G.Api + "News/GetPoll?Token=" + G.currentUser.Token).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetPoll());
                    return;
                }
                JsonParser.getPollResponse(response.body().string());
            }
        });
    }

    //ثبت نظرسنجی
    public static void sendSetPollRequest(String pollId, String optionId, String des) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("Token",  G.currentUser.Token)
                .add("pollCode",  pollId)
                .add("optionId",  optionId)
                .add("Des", "" + des)
                .build();
        Logger.d("WebService : sendSetPollRequest rt is " + "SetPoll");
        Logger.d("WebService : sendSetPollRequest Token is " + G.currentUser.Token);
        Logger.d("WebService : sendSetPollRequest PollID is " + pollId);
        Logger.d("WebService : sendSetPollRequest OptionID is " + optionId);
        Logger.d("WebService : sendSetPollRequest Des is " + des);

        Request request = new Request.Builder().url(G.Api + "News/SetPoll").post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorSetPoll());
                    return;
                }
                JsonParser.setPollResponse(response.body().string());
            }
        });
    }


    //گرفتن همه Notification ها
    public static void sendGetNotifiesRequest(long code, final boolean showNotification) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Logger.d("WebService : sendGetNotifiesRequest rt is " + "GetNotifies");
        Logger.d("WebService : sendGetNotifiesRequest Token is " + G.currentUser.Token);
        Logger.d("WebService : sendGetNotifiesRequest code is " + code);

        Request request = new Request.Builder().url(G.Api + "News/GetNotify?size=10&index=1&Token=" + G.currentUser.Token).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorGetNotifies(EnumInternetErrorType.NO_INTERNET_ACCESS));
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetNotifies(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.getNotifiesResponse(response.body().string(), showNotification);
            }
        });
    }

    //گرفتن همه تبلیغات
//    public static void sendGetAdvsRequest() {
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .build();
//        RequestBody body = new FormBody.Builder()
//                .add("rt", "GetAdvs")
//                .add("UserID", "" + G.currentUser.userId)
//                .add("ExKey", "" + G.currentUser.exKey)
//                .build();
//        Logger.d("WebService : sendGetAdvsRequest rt is " + "GetAdvs");
//        Logger.d("WebService : sendGetAdvsRequest UserID is " + G.currentUser.userId);
//        Logger.d("WebService : sendGetAdvsRequest ExKey is " + G.currentUser.exKey);
//
//        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
//                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (!response.isSuccessful()) {
//                    EventBus.getDefault().post(new EventOnGetErrorGetAdvs());
//                    return;
//                }
//                JsonParser.GetAdvsResponse(response.body().string());
//            }
//        });
//    }

    //ثبت ابنکه مشترک تبلیغ خاصی را مشاهده کرده است
//    public static void sendSetAdsRepRequest(long adsCode) {
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .build();
//        RequestBody body = new FormBody.Builder()
//                .add("rt", "SetAdsRep")
//                .add("UserID", "" + G.currentUser.userId)
//                .add("ExKey", "" + G.currentUser.exKey)
//                .add("AdsCode", "" + adsCode)
//                .build();
//        Logger.d("WebService : sendGetAdvsRequest rt is " + "SetAdsRep");
//        Logger.d("WebService : sendGetAdvsRequest UserID is " + G.currentUser.userId);
//        Logger.d("WebService : sendGetAdvsRequest ExKey is " + G.currentUser.exKey);
//        Logger.d("WebService : sendGetAdvsRequest AdsCode is " + adsCode);
//
//        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
//                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (!response.isSuccessful()) {
//                    EventBus.getDefault().post(new EventOnGetErrorSendSetAdsRep());
//                    return;
//                }
//                JsonParser.setAdsRepResponse(response.body().string());
//            }
//        });
//    }

    /* WebService for Other*/
    //گرفتن اطلاعات شرکت برای نمایش در صفحه دریاره ما
//    public static void sendGetIspInfoRequest() {
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .build();
//        RequestBody body = new FormBody.Builder()
//                .add("rt", "GetIspInfo")
//                .add("UserID", "" + G.currentUser.userId)
//                .add("ExKey", "" + G.currentUser.exKey)
//                .build();
//        Logger.d("WebService : sendGetIspInfoRequest rt is " + "GetIspInfo");
//        Logger.d("WebService : sendGetIspInfoRequest UserID is " + G.currentUser.userId);
//        Logger.d("WebService : sendGetIspInfoRequest ExKey is " + G.currentUser.exKey);
//
//        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
//                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (!response.isSuccessful()) {
//                    EventBus.getDefault().post(new EventOnGetErrorGetIspInfo());
//                    return;
//                }
//                JsonParser.GetIspInfoResponse(response.body().string());
//            }
//        });
//    }

    //ثبت اینکه مشترک به صفحه موبایل وارد شده است جهت آمار بازدید بعدی
    public static void sendVisitMobileRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        Logger.d("WebService : sendVisitMobileRequest rt is " + "VisitMobile");
        Logger.d("WebService : sendVisitMobileRequest Token is " + G.currentUser.Token);

        Request request = new Request.Builder().url(G.Api + "Other/VisitMobile?Token=" + G.currentUser.Token).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorVisitMobile());
                    return;
                }
                JsonParser.visitMobileResponse(response.body().string());
            }
        });
    }

    /* WebService for ChargeOnline*/
    //صدا زدن آیتم های صفحه اصلی شارژ انلاینست
    public static void sendGetChargeOnlineMainItemsRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder().url(G.Api +
                "ChargeOnline/getChargeOnlineMainItems?Token=" + G.currentUser.Token).get().build();

        Logger.d("WebService : sendGetChargeOnlineMainItemsRequest rt is " + "GetChargeOnlineMainItems");
        Logger.d("WebService : sendGetChargeOnlineMainItemsRequest Token is " + G.currentUser.Token);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetChargeOnlineMainItems());
                    return;
                }
                JsonParser.getChargeOnlineMainItemResponse(response.body().string());
            }
        });
    }

    //چک کردن اینکه باشگاه نمایش داده شود یا نه
    public static void sendCheckChargeOnlineClubRequest(final int whichMenuItem) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder().url(G.Api + "ChargeOnline/checkChargeOnlineClub?Select=" + (whichMenuItem - 1) +
                "&Token=" + G.currentUser.Token).get().build();
        Logger.d("WebService : sendCheckChargeOnlineClubRequest rt is " + "CheckChargeOnlineClub");
        Logger.d("WebService : sendCheckChargeOnlineClubRequest Token is " + G.currentUser.Token);
        Logger.d("WebService : sendCheckChargeOnlineClubRequest Select is " + (whichMenuItem - 1));
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorCheckChargeOnlineClub());
                    return;
                }
                JsonParser.getCheckChargeOnlineClub(response.body().string(), whichMenuItem);
            }
        });
    }

    //درخواست تمدید سرویس
    public static void sendChargeOnlineForTamdidRequest(int isClub) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("Token", "" + G.currentUser.Token)
                .add("Club", "" + isClub)
                .build();
        Request request = new Request.Builder().url(G.Api + "ChargeOnline/chargeOnlineforTamdid").post(body).build();
        Logger.d("WebService : sendChargeOnlineForTamdidRequest rt is " + "ChargeOnlineForTamdid");
        Logger.d("WebService : sendChargeOnlineForTamdidRequest Token is " + G.currentUser.Token);
        Logger.d("WebService : sendChargeOnlineForTamdidRequest Club is " + isClub);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorChargeOnlineForTamdid());
                    return;
                }
                JsonParser.getChargeOnlineForTamdid(response.body().string());
            }
        });
    }

    //بارگذاری گروه ها
    public static void sendChargeOnlineForLoadGroupsRequest(int isClub) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder().url(G.Api + "ChargeOnline/chargeonlineforloadGroups?club=" + isClub + "&Token=" + G.currentUser.Token).get().build();
        Logger.d("WebService : sendChargeOnlineForLoadGroupsRequest rt is " + "ChargeOnlineForLoadGroups");
        Logger.d("WebService : sendChargeOnlineForLoadGroupsRequest Token is " + G.currentUser.Token);
        Logger.d("WebService : sendChargeOnlineForLoadGroupsRequest Club is " + isClub);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorChargeOnlineForLoadGroups());
                    return;
                }
                JsonParser.getChargeOnlineForLoadGroups(response.body().string());
            }
        });
    }

    //بارگذاری پکیج ها
    public static void sendChargeOnlineForLoadPackagesRequest(int isClub, long groupCode, final int whichMenuItem, int categoryCode) {
        int whichMenuItemToSend;
        if (whichMenuItem == 2) {
            whichMenuItemToSend = 12;
        } else {
            whichMenuItemToSend = whichMenuItem;
        }

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(G.Api
                        + "ChargeOnline/chargeonlineforloadPackages?categoryCode=" + categoryCode
                        + "&GroupCode=" + groupCode
                        + "&Select=" + (whichMenuItemToSend - 1)
                        + "&club=" + isClub
                        + "&Token=" + G.currentUser.Token
                )
                .get()
                .build();
        Logger.d("WebService : sendChargeOnlineForLoadPackagesRequest rt is " + "ChargeOnlineForLoadPackages");
        Logger.d("WebService : sendChargeOnlineForLoadPackagesRequest Token is " + G.currentUser.Token);
        Logger.d("WebService : sendChargeOnlineForLoadPackagesRequest Club is " + isClub);
        Logger.d("WebService : sendChargeOnlineForLoadPackagesRequest GroupCode is " + groupCode);
        Logger.d("WebService : sendChargeOnlineForLoadPackagesRequest Select is " + (whichMenuItem - 1));
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorChargeOnlineForLoadPackages());
                    return;
                }
                JsonParser.getChargeOnlineForLoadPackages(response.body().string());
            }
        });
    }

    //بارگذاری پکیج ها
    public static void sendChargeOnlineForSelectPackageRequest(int isClub, long packageCode, long groupCode) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("Token", "" + G.currentUser.Token)
                .add("Club", "" + isClub)
                .add("PackageCode", "" + packageCode)
                .add("groupCode", "" + groupCode)
                .build();
        Request request = new Request.Builder().url(G.Api + "ChargeOnline/chargeonlineforSelectPackage").post(body).build();
        Logger.d("WebService : sendChargeOnlineForSelectPackageRequest rt is " + "ChargeOnlineForSelectPackage");
        Logger.d("WebService : sendChargeOnlineForSelectPackageRequest Token is " + G.currentUser.Token);
        Logger.d("WebService : sendChargeOnlineForSelectPackageRequest Club is " + isClub);
        Logger.d("WebService : sendChargeOnlineForSelectPackageRequest PackageCode is " + packageCode);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorChargeOnlineForSelectPackage());
                    return;
                }
                JsonParser.getChargeOnlineForSelectPackage(response.body().string());
            }
        });
    }

    public static void sendRegisterCustomerRequest(int cityId, int groupId, String name, String family, String birthday, String melli, String mobile, String phone, String address, String username, String password) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "RegisterCustomer")
                .add("CityId", "" + cityId)
                .add("GroupId", "" + groupId)
                .add("Name", "" + name)
                .add("Family", "" + family)
                .add("BirthDay", "" + birthday)
                .add("Melli", "" + melli)
                .add("Mobile", "" + mobile)
                .add("Phone", "" + phone)
                .add("Address", "" + address)
                .add("UserName", "" + username)
                .add("Pass", "" + password)
                .build();
        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        Logger.d("WebService : sendRegisterCustomerRequest rt is " + "GetCityGroups");
        Logger.d("WebService : sendRegisterCustomerRequest CityId is " + cityId);
        Logger.d("WebService : sendRegisterCustomerRequest GroupId is " + groupId);
        Logger.d("WebService : sendRegisterCustomerRequest Name is " + name);
        Logger.d("WebService : sendRegisterCustomerRequest Family is " + family);
        Logger.d("WebService : sendRegisterCustomerRequest BirthDay is " + birthday);
        Logger.d("WebService : sendRegisterCustomerRequest Melli is " + melli);
        Logger.d("WebService : sendRegisterCustomerRequest Mobile is " + mobile);
        Logger.d("WebService : sendRegisterCustomerRequest Phone is " + phone);
        Logger.d("WebService : sendRegisterCustomerRequest Address is " + address);
        Logger.d("WebService : sendRegisterCustomerRequest UserName is " + username);
        Logger.d("WebService : sendRegisterCustomerRequest Pass is " + password);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorRegisterCustomer(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorRegisterCustomer(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.getRegisterCustomerResponse(response.body().string());
            }
        });
    }

    public static void sendGetLocationsRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder().url(G.Api + "Club/getGPSPoints?Token=" + G.currentUser.Token).get().build();
        Logger.d("WebService : sendGetLocationsRequest rt is " + "getgpspoints");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorLocations(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorLocations(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.getLocations(response.body().string());
            }
        });
    }

    public static void sendAddScoreRequest(final Locations location) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("Token", "" + G.currentUser.Token)
                .add("type", "" + location.getScoreTypeCode())
                .build();
        Request request = new Request.Builder().url(G.Api + "Club/addScore").post(body).build();
        Logger.d("WebService : sendGetLocationsRequest rt is " + "addscore");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorGetCities(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                if (!G.isTurnOnsGpsDialogShowedInOfflineode) {
                    G.currentActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogClass.showMessageDialog("امتیاز", "شما در موقعیت گرفتن امتیاز قرار دارید، برای ثبت امتیاز اینترنت خود را  فعال کنید و دوباره تلاش کنید");

                        }
                    });
                    G.isTurnOnsGpsDialogShowedInOfflineode = true;
                }
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetCities(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                G.isTurnOnsGpsDialogShowedInOfflineode = false;
                JsonParser.addScoreResponse(response.body().string(), location);
            }
        });
    }

    public static void addLocationScore(Locations locations) {
        String startDate = locations.getStartDate();
        String endDate = locations.getEndDate();

        try {
            DateFormat currentDay = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            DateFormat currentHour = new SimpleDateFormat("hh:mm:ss ", Locale.ENGLISH);
            String nDay = currentDay.format(Calendar.getInstance().getTime());
            String nHour = currentHour.format(Calendar.getInstance().getTime());


            SimpleDateFormat Datee = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            SimpleDateFormat houre = new SimpleDateFormat("hh:mm:ss", Locale.ENGLISH);
            String[] startDay = startDate.split("T");
            String[] endDay = endDate.split("T");


            Date sDay = Datee.parse(nDay);
            Date sHour = houre.parse(nHour);

            Date eDay = Datee.parse(endDay[0]);
            Date eHour = houre.parse(endDay[1]);
            int campareDay = eDay.compareTo(sDay);
            int campareHour = eHour.compareTo(sHour);

            switch (campareDay) {
                case 0:

                    if (campareHour > 0)
                        WebService.sendAddScoreRequest(locations);

                    break;
                case 1:
                    WebService.sendAddScoreRequest(locations);
                    break;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public static void sendGetTicketCodes() {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder().url(G.Api + "Ticket/loadContactTitle?Token=" + G.currentUser.Token).get().build();
        Logger.d("WebService : sendGetLocationsRequest rt is " + "loadcontacttitle");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorGetCities(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());


            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetCities(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.getTicketCodesResponse(response.body().string());
            }
        });
    }

    public static void sendTokenToServer(String token) {
        final SharedPreferences[] pref = new SharedPreferences[1];
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("devicetoken", token)
                .add("Token", G.currentUser.Token)
                .build();
        final Request request = new Request.Builder().url(G.Api + "login/updateDeviceToken").post(body).build();
        Logger.d("WebService : sendGetLocationsRequest rt is " + "loadcontacttitle");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorGetCities(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                try {

                    JSONObject jsonObject = new JSONObject(json);
                    int message = jsonObject.getInt("Message");
                    int result = jsonObject.getInt("Result");
                    if (result == EnumResponse.OK && message == 1) {
                        pref[0] = G.context.getSharedPreferences(G.FB_SHARED_PREF, 0);
                        SharedPreferences.Editor editor = pref[0].edit();
                        editor.putString("token", "token");
                        editor.apply();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void sendChargeOnlineForLoadCategoryRequest(int isClub, long groupCode, final int whichMenuItem) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder().url(G.Api +
                "ChargeOnline/chargeonlineforloadCategory?Club=" + isClub +
                "&groupCode=" + groupCode +
                "&Token=" + G.currentUser.Token).get().build();
        Logger.d("WebService : sendChargeOnlineForLoadCategoryRequest rt is " + "ChargeOnlineForLoadPackages");
        Logger.d("WebService : sendChargeOnlineForLoadCategoryRequest Token is " + G.currentUser.Token);
        Logger.d("WebService : sendChargeOnlineForLoadCategoryRequest Club is " + isClub);
        Logger.d("WebService : sendChargeOnlineForLoadCategoryRequest GroupCode is " + groupCode);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorChargeOnlineForLoadPackages());
                    return;
                }
                JsonParser.getChargeOnlineForLoadCategory(response.body().string());
            }
        });
    }

    public static void sendChargeOnlineForCountCategoryRequest(final int isClub, final int groupCode, final int whichMenuItem) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder().url(G.Api +
                "ChargeOnline/chargeonlineforloadCategory?Club=" + isClub +
                "&groupCode=" + groupCode +
                "&Token=" + G.currentUser.Token).get().build();
        Logger.d("WebService : sendChargeOnlineForLoadCategoryRequest rt is " + "ChargeOnlineForLoadPackages");
        Logger.d("WebService : sendChargeOnlineForLoadCategoryRequest Token is " + G.currentUser.Token);
        Logger.d("WebService : sendChargeOnlineForLoadCategoryRequest Club is " + isClub);
        Logger.d("WebService : sendChargeOnlineForLoadCategoryRequest GroupCode is " + groupCode);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorChargeOnlineForLoadPackages());
                    return;
                }
                JsonParser.getChargeOnlineForCountCategory(response.body().string(), isClub, groupCode, whichMenuItem);
            }
        });
    }

    public static void uploadAvatarImage(String encodedImage) {
        EventBus.getDefault().post(new EventOnStartUploadingAvatar());
        Logger.d("WebService uploadAvatarImage encodedImage is " + encodedImage);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("Token", G.currentUser.Token)
                .add("Avatar", encodedImage)
                .build();
        final Request request = new Request.Builder().url(G.Api + "Avatar/setUserAvatarBase64").post(body).build();
        Logger.d("WebService : uploadAvatarImage rt is " + "encodedImage");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorGetCities(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");

                EventBus.getDefault().post(new EventOnStopUploadingAvatar());
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                EventBus.getDefault().post(new EventOnStopUploadingAvatar());
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetCities(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.uploadImageResponse(response.body().string());
            }
        });


    }

    public static void getAvatarImage() {

        final SharedPreferences[] pref = new SharedPreferences[1];
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        final Request request = new Request.Builder().url(G.Api + "Avatar/getUserAvatar?Token=" + G.currentUser.Token).get().build();
        Logger.d("WebService : getAvatarImage rt is " + "encodedImage");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorGetCities(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnGetAvatarImageResponse());
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());


            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetCities(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.getAvatarImage(response.body().string());
            }
        });

    }

    public static void removeAvatarImage() {

        final SharedPreferences[] pref = new SharedPreferences[1];
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("Token", "" + G.currentUser.Token)
                .build();
        final Request request = new Request.Builder().url(G.Api + "Avatar/removeUserAvatar").post(body).build();
        Logger.d("WebService : getAvatarImage rt is " + "encodedImage");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorGetCities(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnGetAvatarImageResponse());
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());


            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetCities(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    int result = object.getInt("Result");
                    if (result == EnumResponse.OK) {
                        EventBus.getDefault().post(new EventOnRemoveAvatarImageResponse());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public static void sendSetNotifyReaded() {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("Token", "" + G.currentUser.Token)
                .build();
        Request request = new Request.Builder().url(G.Api + "News/SetNotifyReaded").post(body).build();
        Logger.d("WebService : sendGetLocationsRequest rt is " + "loadcontacttitle");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorGetCities(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());


            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
            }
        });
    }

    public static void sendGetRepresenterURL() {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("Token", "" + G.currentUser.Token)
                .build();
        Request request = new Request.Builder().url(G.Api + "login/GetRepresenterURL").post(body).build();
        Logger.d("WebService : sendGetRepresenterURL rt is " + "sendGetRepresenterURL");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorGetCities(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());


            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                JsonParser.getRepresenterURLRespone(s);
            }
        });
    }

    //گرفتن تقسیم ترافیک جاری
    public static void sendGetCurrentTrraficSplite() {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder().url(G.Api +
                "Packages/GetCurrentTrafficSplit?Token=" + G.currentUser.Token).get().build();
        Logger.d("WebService : sendGetLocationsRequest rt is " + "loadcontacttitle");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorGetCities(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetCities(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.getCurrentTrafficSplite(response.body().string());

            }
        });
    }

    // درخواست اتمام تقسیم ترافیک
    public static void sendEndTrraficSplite() {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        RequestBody body = new FormBody.Builder()
                .add("Token", G.currentUser.Token)
                .build();

        Request request = new Request.Builder().url(G.Api +
                "Packages/EndTrafficSplit").post(body).build();
        Logger.d("WebService : sendEndTrraficSplite rt is " + "sendEndTrraficSplite");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorGetCities(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());


            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    final int Result = jsonObject.getInt("Result");
                    final String Message = jsonObject.getString("Message");
                    final DialogClass dialogClass = new DialogClass();
                    G.currentActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (Result == EnumResponse.OK) {
                                EventBus.getDefault().post(new EventOnSuccessEndTrafficSplit());
                            } else {
                                dialogClass.showMessageDialog("خطا", Message);
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public static void sendGoToMainTrrafic() {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        RequestBody body = new FormBody.Builder()
                .add("Token", G.currentUser.Token)
                .build();

        Request request = new Request.Builder().url(G.Api +
                "Packages/GoTOMainTraffic").post(body).build();
        Logger.d("WebService : sendGoToMainTrrafic rt is " + "sendGoToMainTrrafic");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorGetCities(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    final int Result = jsonObject.getInt("Result");
                    final String Message = jsonObject.getString("Message");
                    final DialogClass dialogClass = new DialogClass();
                    G.currentActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (Result == EnumResponse.OK) {
                                EventBus.getDefault().post(new EventOnSuccessGoToMainTraffic());
                            } else {
                                dialogClass.showMessageDialog("خطا", Message);
                            }
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    //گرفتن لیست تقسیم ترافیک ها
    public static void sendLoadTrafficSplitsNotMain() {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder().url(G.Api +
                "Packages/LoadTrafficSplitsNotMain?Token=" + G.currentUser.Token).get().build();
        Logger.d("WebService : sendLoadTrafficSplitsNotMain rt is " + "sendLoadTrafficSplitsNotMain");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorGetCities(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                JsonParser.getLoadTrafficSplitNotMain(s);
            }
        });
    }

    // درخواست شروع تقسیم ترافیک
    public static void sendStartTrafficSplit(int code) {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("Token", G.currentUser.Token)
                .add("code", "" + code)
                .build();

        Request request = new Request.Builder().url(G.Api +
                "Packages/StartTrafficSplit").post(body).build();
        Logger.d("WebService : sendStartTrafficSplit rt is " + "sendStartTrafficSplit");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorGetCities(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    final int Result = jsonObject.getInt("Result");
                    final String Message = jsonObject.getString("Message");
                    G.currentActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (Result == EnumResponse.OK) {
                                EventBus.getDefault().post(new EventOnSuccessStartTrafficSplit());
                            } else {
                                EventBus.getDefault().post(new EventOnFailStartTrafficSplit(Message));

                            }
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    // چک کردن آپدیت
    public static void sendGetUpdate() {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder().url(G.Api +
                "Other/GetUpdate?type=1").get().build();
        Logger.d("WebService : sendStartTrafficSplit rt is " + "sendStartTrafficSplit");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorGetCities(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                JsonParser.getUpdateResponse(s);
            }
        });
    }

    public static void sendGetBankUrl(int factorCode, int money, int bank, final String bankName) {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("Token", G.currentUser.Token)
                .add("FactorCode", factorCode + "")
                .add("Money", money + "")
                .add("Bank", bank + "")
                .build();

        Request request = new Request.Builder().url(G.Api +
                "ChargeOnline/LinkPayment").post(body).build();
        Logger.d("WebService : sendGetBankUrl rt is " + "sendStartTrafficSplit");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorGetCities(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                JsonParser.getBankUrl(s, bankName);
            }
        });
    }

    //گرفتن لیست استان ها
    public static void sendGetRegions() {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder().url(G.Api +
                "login/Regions").get().build();
        Logger.d("WebService : sendGetRegions rt is " + "sendGetRegions");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorGetCities(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                JsonParser.getRegionsResponse(s);
            }
        });
    }

    //گرفتن لیست شهرها
    public static void sendGetCities(int region) {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder().url(G.Api +
                "Login/Cities?region=" + region).get().build();
        Logger.d("WebService : sendGetCities rt is " + "sendGetCities");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorGetCities(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                JsonParser.getCitiesResponse(s);
            }
        });
    }

    public static void sendRegisterCustomerRequest(ModelRegisterCustomer customer) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .build();

        RequestBody body = new FormBody.Builder()
                .add("city", String.valueOf(customer.getCity()))
                .add("customerType", String.valueOf(customer.getCustomerType()))
                .add("phoneRanzhe", String.valueOf(customer.getPhoneRanzhe()))
                .add("mobile", String.valueOf(customer.getMobile()))
                .add("name", customer.getName())
                .add("family", customer.getFamily())
                .add("address", customer.getAddress())
                .add("Phone", customer.getPhone())
                .add("IsCompany", "false")
                .build();

        Request request = new Request.Builder()
                .post(body)
                .url(G.Api + "Login/registerCustomer")
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorGetCities(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JsonParser.registerCustomerResponse(response.body().string());
            }
        });


    }

    public static void sendUploadDocumentRequest(final Context context, File file, String des, String extension, ProgressBar progressWheel) {


        Ion.with(context)
                .load(G.Api + "Document/uploadDocument")
                .uploadProgressBar(progressWheel)
                .setMultipartParameter("Token", G.currentUser.Token)
                .setMultipartFile("archive", "application/json; charset=utf-8", file)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Logger.d("WebService : sendUploadDocumentRequest onResponse.message is " + result.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(result.toString());
                            String Message =  jsonObject.getString("Message");
                            EventBus.getDefault().post(new EventOnSuccessUploadDocument(Message));
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
    }

    public static void sendGetCountNotify() {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder().url(G.Api +
                "News/GetCountNotify?Token="+G.currentUser.Token).get().build();
        Logger.d("WebService : sendGetCountNotify rt is " + "sendGetCountNotify");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorGetCities(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
                EventBus.getDefault().post(new EventOnErrorInConnectingToServer());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                JsonParser.GetCountNotifyResponse(s);
            }
        });
    }
}
