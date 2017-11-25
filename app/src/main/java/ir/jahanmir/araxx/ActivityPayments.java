package ir.jahanmir.araxx;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmir.araxx.classes.Logger;
import ir.jahanmir.araxx.classes.U;
import ir.jahanmir.araxx.component.PersianTextViewThin;
import ir.jahanmir.araxx.fragment.FragmentShowPaymentList;


public class ActivityPayments extends AppCompatActivity {


    public static boolean isReturnedFromBrowser = false;
    public static boolean isParent = false;

    private FragmentManager supportFragmentManager;
    @Bind(R.id.layBtnClose)FrameLayout layBtnClose;
    @Bind(R.id.imgIcon)ImageView imgIcon;
    @Bind(R.id.layToolbarMain)LinearLayout layToolbarMain;
    @Bind(R.id.txtName)PersianTextViewThin txtName;

    @Override
    public FragmentManager getSupportFragmentManager() {
        return supportFragmentManager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_payments);
        ButterKnife.bind(this);
//        EventBus.getDefault().register(this);

        initToolbar();
        isParent = true;
        layBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        supportFragmentManager = super.getSupportFragmentManager();
        supportFragmentManager
                .beginTransaction()
                .add(R.id.layFragment, new FragmentShowPaymentList())
                .addToBackStack("FragmentShowPaymentList")
                .commit();


    }

    private void initToolbar() {

        int width = U.getDeviceWidth() / 4;
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, width);
        layToolbarMain.setLayoutParams(layoutParams);
        layToolbarMain.setBackgroundColor(getResources().getColor(R.color.color_payment));
        imgIcon.setImageResource(R.drawable.ic_payments);
        txtName.setText("پرداخت ها");

    }




    @Override
    protected void onResume() {
        super.onResume();
        Logger.d("ActivityShowPayments : onResume()");
        G.currentActivity = this;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        Logger.d("ActivityChargeOnline : onBackPressed()");
        if (supportFragmentManager.getBackStackEntryCount() == 1)
            finish();
        else
            super.onBackPressed();
    }
    /*-------------------------------------------------------------------------------------------------------------*/

    @Override
    protected void onStop() {
        super.onStop();

        isParent = false;
    }
}
