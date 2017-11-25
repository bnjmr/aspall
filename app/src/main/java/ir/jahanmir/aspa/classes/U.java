package ir.jahanmir.aspa.classes;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.io.File;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ir.jahanmir.aspa.ActivityShowNotify;
import ir.jahanmir.aspa.G;
import ir.jahanmir.aspa.R;
import ir.jahanmir.aspa.enums.EnumChargeOnlineMenuItem;
import ir.jahanmir.aspa.model.ClubScore;
import ir.jahanmir.aspa.model.Connection;
import ir.jahanmir.aspa.model.Factor;
import ir.jahanmir.aspa.model.FactorDetail;
import ir.jahanmir.aspa.model.Feshfeshe;
import ir.jahanmir.aspa.model.Locations;
import ir.jahanmir.aspa.model.Payment;
import ir.jahanmir.aspa.model.Ticket;
import ir.jahanmir.aspa.model.TicketDetail;
import ir.jahanmir.aspa.model.Unit;


public class U {
    public static void toast(String message) {
        Toast toast = Toast.makeText(G.context, message, Toast.LENGTH_SHORT);
        LinearLayout toastLayout = (LinearLayout) toast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        toastTV.setTextSize(12);
        toastTV.setGravity(Gravity.CENTER);
        toastTV.setTypeface(Typeface.SANS_SERIF);
        toast.show();
    }

    public static void toastOnMainThread(final String message) {
        G.handler.post(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(G.context, message, Toast.LENGTH_SHORT);
                LinearLayout toastLayout = (LinearLayout) toast.getView();
                TextView toastTV = (TextView) toastLayout.getChildAt(0);
                toastTV.setTextSize(12);
                toastTV.setTypeface(Typeface.SANS_SERIF);
                toast.show();
            }
        });
    }

    public static void overrideFont(Context context, String defaultFontNameToOverride, String customFontFileNameInAssets) {
        try {
            final Typeface customFontTypeface = Typeface.createFromAsset(context.getAssets(), customFontFileNameInAssets);
            final Field defaultFontTypefaceField = Typeface.class.getDeclaredField(defaultFontNameToOverride);
            defaultFontTypefaceField.setAccessible(true);
            defaultFontTypefaceField.set(null, customFontTypeface);
        } catch (Exception e) {

        }
    }

    public static String getIMEI() {
        TelephonyManager telephonyManager = (TelephonyManager) G.context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        return imei;
    }

    public static String getApplicationName() {
        int stringId = G.context.getApplicationInfo().labelRes;
        return G.context.getString(stringId);
    }

    public static String getAppVersionName() {
        try {
            PackageInfo pInfo = G.context.getPackageManager().getPackageInfo(G.context.getPackageName(), 0);
            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getDeviceName() {
        String deviceName = Build.BRAND + " " + Build.MANUFACTURER;
        return deviceName;
    }

    public static String getDeviceModel() {
        String deviceModel = Build.PRODUCT + "(" + Build.MODEL + ")";
        return deviceModel;
    }

    public static String getOSVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    public static int getDeviceWidth() {
        DisplayMetrics metrics = G.context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        return width;
    }

    public static int getDeviceHeight() {
        DisplayMetrics metrics = G.context.getResources().getDisplayMetrics();
        int height = metrics.heightPixels;
        return height;
    }

    public static float getDeviceDensity() {
        return G.context.getResources().getDisplayMetrics().density;
    }

    public static void setStatusBarColor(View statusBar, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = G.currentActivity.getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //status bar height
            int actionBarHeight = getActionBarHeight();
            int statusBarHeight = getStatusBarHeight();
            //action bar height
            statusBar.getLayoutParams().height = actionBarHeight + statusBarHeight;
            statusBar.setBackgroundColor(color);
        }
    }

    public static void sendNotification(final String message) {
        Intent intent = new Intent(G.context, ActivityShowNotify.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(G.context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //Define sound URI
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(G.context);
        builder.setContentTitle(U.getApplicationName());
        builder.setContentText(message)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_notify)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS)
                .setAutoCancel(true);
        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) G.context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1367, notification);
    }

    public static int getActionBarHeight() {
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (G.currentActivity.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, G.context.getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = G.context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = G.context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static void expand(final View v) {
        v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();
        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? LinearLayout.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        // 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        String formattedDate;
        formattedDate = df.format(cal.getTime());
        return formattedDate;
    }

    public static String getDate(int minus) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        cal.add(Calendar.DATE, minus);
        String formattedDate;
        formattedDate = df.format(cal.getTime());
        return formattedDate;
    }

    public static void deleteUnitTableItem() {
        new Delete().from(Unit.class).execute();
    }

    public static void deleteTicketTableItem() {
        new Delete().from(Ticket.class).execute();
    }

    public static void deleteTicketDetailTableItem(long ticketCode) {
        new Delete().from(TicketDetail.class).where("ParentCode = "+ ticketCode).execute();
    }

    public static void deletePaymentTableItem() {
        new Delete().from(Payment.class).execute();
    }

    public static void deleteLocationsItem() {
        new Delete().from(Locations.class).execute();
    }

    public static void deleteFeshfesheTableItem() {
        new Delete().from(Feshfeshe.class).execute();
    }

    public static void deleteClubScoreTableItem() {
        new Delete().from(ClubScore.class).execute();
    }

    public static void deleteFactorTableItem() {
        new Delete().from(Factor.class).execute();
    }

    public static void deleteConnectionTableItem() {
        new Delete().from(Connection.class).execute();
    }

    public static void deleteFactorDetailTableItem(long factorCode) {
        new Delete().from(FactorDetail.class).execute();
    }

    public static void installUpdateApp() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(G.DIR_APP_DOWNLOAD_FOLDER + "/UpdateApp.apk")), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        G.context.startActivity(intent);
    }

    public static String getMenuItemName(int whichMenuItem) {
        switch (whichMenuItem) {
            case EnumChargeOnlineMenuItem.TAMDID_SERVICE:
                return "تمدید سرویس";
            case EnumChargeOnlineMenuItem.TAGHIR_SERVICE:
                return "تغییر سرویس";
            case EnumChargeOnlineMenuItem.IP:
                return "خرید آی پی";
            case EnumChargeOnlineMenuItem.TRAFFIC:
                return "ترافیک اضافه";
            case EnumChargeOnlineMenuItem.FESHFESHE:
                return "فشفشه";
            case EnumChargeOnlineMenuItem.TAGHSIM_TERAFIC:
                return "تقسیم ترافیک";
        }
        return "";
    }

    public static void getMenuItemIcon(ImageView imageView, int whichMenuItem) {
        switch (whichMenuItem) {
            case EnumChargeOnlineMenuItem.TAMDID_SERVICE:
                imageView.setImageResource(R.drawable.ic_change_service);
                break;
            case EnumChargeOnlineMenuItem.TAGHIR_SERVICE:
                imageView.setImageResource(R.drawable.ic_change_service);
                break;
            case EnumChargeOnlineMenuItem.IP:
                imageView.setImageResource(R.drawable.ic_change_service);
                break;
            case EnumChargeOnlineMenuItem.TRAFFIC:
                imageView.setImageResource(R.drawable.ic_extratraffic);
                break;
            case EnumChargeOnlineMenuItem.FESHFESHE:
                imageView.setImageResource(R.drawable.ic_feshfeshe);
                break;
            case EnumChargeOnlineMenuItem.TAGHSIM_TERAFIC:
                imageView.setImageResource(R.drawable.ic_change_service);
                break;
        }
    }

    public static String persianToDecimal(String number) {
        char[] chars = new char[number.length()];
        for (int i = 0; i < number.length(); i++) {
            char ch = number.charAt(i);
            if (ch >= 0x0660 && ch <= 0x0669)
                ch -= 0x0660 - '0';
            else if (ch >= 0x06f0 && ch <= 0x06F9)
                ch -= 0x06f0 - '0';
            chars[i] = ch;
        }
        return new String(chars);
    }

    public static void cancelNotification(int notifyId) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) G.context.getSystemService(ns);
        nMgr.cancel(notifyId);
    }

    public static void updateLocations() {
        G.GpsPoints = new Select().from(Locations.class).execute();
    }
}
