package ir.jahanmir.araxx.classes;

/**
 * Created by HaMiD on 1/15/2017.
 */


import android.content.SharedPreferences;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import ir.jahanmir.araxx.G;


/**
 * Created by Firoozian on 1/14/2017.
 */


public class firebaseInstanceIDService extends FirebaseInstanceIdService {
    SharedPreferences pref;

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        pref = getApplicationContext().getSharedPreferences(G.FB_SHARED_PREF, 0);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        Toast.makeText(G.context,refreshedToken,Toast.LENGTH_LONG).show();
        Logger.d("Firebase Token "+refreshedToken);
        storeTokenInPref(refreshedToken);
    }

    private void storeTokenInPref(String token) {
        pref = getApplicationContext().getSharedPreferences(G.FB_SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("token", token);
        Logger.d("Firebase Token"+editor.toString());
        editor.apply();
    }

}