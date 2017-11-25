package ir.jahanmir.araxx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.activeandroid.query.Select;

import ir.jahanmir.araxx.model.User;


/**
 * Created by Microsoft on 3/3/2016.
 */
public class ActivityStarter extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (G.customerId != 0) {
            if (!G.currentUser.isLogin) // dar surati ke karbar logout karde bashad
                startActivity(new Intent(G.context, ActivityLogin.class));
            else
                startActivity(new Intent(G.context, ActivityShowCurrentService.class));
        } else {
            User isLastLogin = new Select().from(User.class).where("isLastLogin = ? ", true).executeSingle();
            if (isLastLogin == null)
            /** yani ta konun hich kas login nakarde ast */
                startActivity(new Intent(G.context, ActivitySearchISP.class));
            else {
                if (!G.currentUser.isLogin) // dar surati ke karbar logout karde bashad
                    startActivity(new Intent(G.context, ActivityLogin.class));
                else
                    startActivity(new Intent(G.context, ActivityShowCurrentService.class));
            }
        }
        finish();
    }
}
