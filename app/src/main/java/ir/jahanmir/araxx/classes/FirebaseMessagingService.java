package ir.jahanmir.araxx.classes;


import android.app.PendingIntent;
import android.content.Intent;

import com.google.firebase.messaging.RemoteMessage;

import ir.jahanmir.araxx.ActivityShowNotify;

import static ir.jahanmir.araxx.G.context;


/**
 * Created by Firoozian on 1/14/2017.
 */


public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Intent intent = new Intent(this, ActivityShowNotify.class);
        PendingIntent intent2 = PendingIntent.getBroadcast(context, 1,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

    }

}