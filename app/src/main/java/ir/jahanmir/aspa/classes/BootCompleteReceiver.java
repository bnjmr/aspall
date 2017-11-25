package ir.jahanmir.aspa.classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import ir.jahanmir.aspa.G;


public class BootCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Logger.d("BootCompleteReceiver : onReceive");
        G.checkNotification = new CheckNotification();
        G.checkNotification.SetRepeatAlarm(69, Calendar.getInstance().getTimeInMillis() + G.NOTIFICATION_CHECKER_TIME, G.NOTIFICATION_CHECKER_TIME);
    }
}
