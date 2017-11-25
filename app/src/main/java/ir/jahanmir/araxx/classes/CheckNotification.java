package ir.jahanmir.araxx.classes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.activeandroid.query.Select;

import java.util.Calendar;
import java.util.List;

import ir.jahanmir.araxx.G;
import ir.jahanmir.araxx.model.Notify;


public class CheckNotification extends BroadcastReceiver {

    private Context context;
    public CheckNotification() {
        context = G.context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Logger.d("CheckNotification : onReceive()");
        if(G.currentUser.Token != null&&!G.currentUser.Token.equals("")) {
            /** dar surati ke shakhc login bashad. */
            List<Notify> lastNotify = new Select()
                    .from(Notify.class)
                    .orderBy("NotifyCode desc")
                    .limit(1)
                    .execute();
            if(lastNotify.size() == 1)
                WebService.sendGetNotifiesRequest(lastNotify.get(0).notifyCode,true);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            G.checkNotification.SetRepeatAlarm(69, Calendar.getInstance().getTimeInMillis() + G.NOTIFICATION_CHECKER_TIME, G.NOTIFICATION_CHECKER_TIME);
        }
    }

    public void SetRepeatAlarm(int requestCode, long startTimeInMillis, long RepeatTimeInMillis) {
//        Logger.d("Set Alarm Manager to ring at next " + startTimeInMillis + " milliseconds then repeat every " + RepeatTimeInMillis + "milliseconds .");
//        //\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//        long yourmilliseconds = System.currentTimeMillis();
//        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
//        Date resultdate = new Date(yourmilliseconds);
//        Logger.d("Milliseconds : " + sdf.format(resultdate));
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, CheckNotification.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        // Only one alarm can live, so cancel previous.
        am.cancel(pi);
        /** because in new android api alarm manager dosen't run **/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            am.setExact(AlarmManager.RTC_WAKEUP, startTimeInMillis, pi);
        }
        else {
            am.setRepeating(AlarmManager.RTC_WAKEUP, startTimeInMillis, RepeatTimeInMillis, pi);
        }
    }
}
