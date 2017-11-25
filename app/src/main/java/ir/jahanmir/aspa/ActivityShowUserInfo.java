package ir.jahanmir.aspa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.jahanmir.aspa.classes.DialogClass;
import ir.jahanmir.aspa.classes.Logger;
import ir.jahanmir.aspa.classes.U;
import ir.jahanmir.aspa.classes.WebService;
import ir.jahanmir.aspa.component.PersianTextViewNormal;
import ir.jahanmir.aspa.component.PersianTextViewThin;
import ir.jahanmir.aspa.enums.EnumResponse;
import ir.jahanmir.aspa.events.EventOnGetErrorGetUserInfo;
import ir.jahanmir.aspa.events.EventOnGetUserInfoResponse;
import ir.jahanmir.aspa.events.EventOnNoAccessServerResponse;
import ir.jahanmir.aspa.gson.UserInfoResponse;
import ir.jahanmir.aspa.model.Account;
import ir.jahanmir.aspa.model.Info;


public class ActivityShowUserInfo extends AppCompatActivity {
    @Bind(R.id.layBtnClose)
    FrameLayout layBtnClose;
    @Bind(R.id.txtShowErrorMessage)
    TextView txtShowErrorMessage;
    @Bind(R.id.txtUserFullName)
    PersianTextViewNormal txtUserFullName;
    @Bind(R.id.txtUsername)
    PersianTextViewNormal txtUsername;
    @Bind(R.id.txtMobile)
    PersianTextViewNormal txtMobile;
    @Bind(R.id.txtTarazMalli)
    PersianTextViewNormal txtTarazMalli;
    @Bind(R.id.txtFirstConnection)
    PersianTextViewNormal txtFirstConnection;
    @Bind(R.id.txtLastConnection)
    PersianTextViewNormal txtLastConnection;
    @Bind(R.id.txtExpireDate)
    PersianTextViewNormal txtExpireDate;
    @Bind(R.id.txtNoeService)
    PersianTextViewNormal txtNoeService;
    @Bind(R.id.txtStatus)
    PersianTextViewNormal txtStatus;
    @Bind(R.id.txtNamayandeForush)
    PersianTextViewNormal txtNamayandeForush;

    @Bind(R.id.card_view)
    LinearLayout cardView;
    @Bind(R.id.layBtnUploadDods)
    FrameLayout layBtnUploadDods;


    @Bind(R.id.layToolbarMain)
    LinearLayout layToolbarMain;
    @Bind(R.id.imgIcon)
    ImageView imgIcon;
    @Bind(R.id.txtName)
    PersianTextViewThin txtName;

    DialogClass dlgWaiting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_info);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        dlgWaiting = new DialogClass(this);
        dlgWaiting.DialogWaiting();
//
//        if (Build.VERSION.SDK_INT >= 21)
//            getWindow().setStatusBarColor(ContextCompat.getColor(G.context, R.color.dark_dark_grey));

        cardView.setVisibility(View.GONE);
        WebService.sendGetUserInfoRequest();


        layBtnClose
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });


        layBtnUploadDods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityShowUserInfo.this, ActivityUploadDocuments.class);
                startActivity(intent);
            }
        });

        initToolbar();
    }

    private void initToolbar() {
        int width = U.getDeviceWidth() / 4;
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, width);
        layToolbarMain.setLayoutParams(layoutParams);
        layToolbarMain.setBackgroundColor(getResources().getColor(R.color.color_factor));
        imgIcon.setImageResource(R.drawable.ic_profile);
        txtName.setText("پروفایل");

    }

    public void onEventMainThread(EventOnGetUserInfoResponse event) {
        Logger.d("ActivityShowUserInfo : EventOnGetUserInfoResponse is raised.");
        dlgWaiting.DialogWaitingClose();
        UserInfoResponse response = event.getUserInfo();
        if (response.result == EnumResponse.OK) {
            txtShowErrorMessage.setVisibility(View.GONE);
            setTextViewValue(response);
        } else {
            /** can show error message */
            cardView.setVisibility(View.GONE);
        }
    }

    public void onEventMainThread(EventOnGetErrorGetUserInfo event) {
        Logger.d("ActivityShowUserInfo : EventOnGetErrorGetUserInfo is raised.");
        dlgWaiting.DialogWaitingClose();
        getUserInfoFromDB();
    }

    public void onEventMainThread(EventOnNoAccessServerResponse event) {
        Logger.d("ActivityShowUserInfo : EventOnNoAccessServerResponse is raised.");
dlgWaiting.DialogWaitingClose();
        getUserInfoFromDB();
    }

    private void getUserInfoFromDB() {
        Info info = new Select().from(Info.class).executeSingle();
        if (info != null) {
            txtShowErrorMessage.setVisibility(View.VISIBLE);
            UserInfoResponse response = new UserInfoResponse();
            response.fullName = info.fullName;
            response.username = info.username;
            response.firstCon = info.firstCon;
            response.lastCon = info.lastCon;
            response.mobileNo = info.mobileNo;
            response.resellerName = info.resellerName;
            response.serviceType = info.serviceType;
            response.status = "نامشخص";
            /** set TextView Value from Response */
            setTextViewValue(response);
        } else {
            cardView.setVisibility(View.GONE);
        }
    }

    private void setTextViewValue(UserInfoResponse response) {
        txtUserFullName.setText(response.fullName);
        txtUsername.setText(response.username);
        txtMobile.setText(response.mobileNo);
        txtFirstConnection.setText(response.firstCon);
        txtLastConnection.setText(response.lastCon);
        txtNoeService.setText(response.serviceType);
        txtStatus.setText(response.status);
        txtNamayandeForush.setText(response.resellerName);
        txtTarazMalli.setText("نامشخص");
        txtExpireDate.setText("نامشخص");
        Account account = new Select().from(Account.class).executeSingle();
        if (account != null) {
            String pattern = "#,###";
            DecimalFormat myFormatter = new DecimalFormat(pattern);
            txtTarazMalli.setText(" " + myFormatter.format(account.Balance) + "");

//            txtTarazMalli.setText("" + account.balance);
            txtExpireDate.setText(account.FarsiExpDate);
        }
        cardView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
