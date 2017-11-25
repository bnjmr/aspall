package ir.jahanmir.araxx.classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import de.greenrobot.event.EventBus;
import ir.jahanmir.araxx.events.EventOnNetworkStatusChange;

/**
 * Created by Microsoft on 5/3/2016.
 */
public class NetworkChangeReceiver extends BroadcastReceiver {

    public static int CONNECTED = 3;
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;
    @Override
    public void onReceive(final Context context, final Intent intent) {

        int status = NetworkUtil.getConnectivityStatus(context);
        if(status != TYPE_NOT_CONNECTED){
            if(isOnline(context))
                EventBus.getDefault().post(new EventOnNetworkStatusChange(CONNECTED));
        }
    }
    public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in air plan mode it will be null
        return (netInfo != null && netInfo.isConnected());

    }
}
