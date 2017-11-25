package ir.jahanmir.aspa;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.jahanmir.aspa.adapter.AdapterNotify;
import ir.jahanmir.aspa.classes.DialogClass;
import ir.jahanmir.aspa.classes.Logger;
import ir.jahanmir.aspa.classes.U;
import ir.jahanmir.aspa.classes.WebService;
import ir.jahanmir.aspa.component.PersianTextViewThin;
import ir.jahanmir.aspa.enums.EnumInternetErrorType;
import ir.jahanmir.aspa.events.EventOnErrorInConnectingToServer;
import ir.jahanmir.aspa.events.EventOnGetErrorGetNotifies;
import ir.jahanmir.aspa.events.EventOnGetNotifiesResponse;
import ir.jahanmir.aspa.events.EventOnNotifyDeleted;
import ir.jahanmir.aspa.model.Notify;

public class ActivityShowNotify extends AppCompatActivity {
    @Bind(R.id.layBtnClose)
    FrameLayout layBtnClose;
    @Bind(R.id.lstNotify)
    RecyclerView lstNotify;

    @Bind(R.id.txtShowMessage)
    TextView txtShowMessage;


    @Bind(R.id.layToolbarMain)
    LinearLayout layToolbarMain;
    @Bind(R.id.imgIcon)
    ImageView imgIcon;
    @Bind(R.id.txtName)
    PersianTextViewThin txtName;

    AdapterNotify adapterNotify;
    LinearLayoutManager linearLayoutManager;
    List<Notify> notifies = new ArrayList<>();

    DialogClass dlgWaiting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notify);
        ButterKnife.bind(this);
        WebService.sendSetNotifyReaded();
        EventBus.getDefault().register(this);
        dlgWaiting = new DialogClass(this);
        dlgWaiting.DialogWaiting();

        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setStatusBarColor(ContextCompat.getColor(G.context, R.color.dark_dark_grey));

        adapterNotify = new AdapterNotify(notifies);
        linearLayoutManager = new LinearLayoutManager(this);
        lstNotify.setLayoutManager(linearLayoutManager);
        lstNotify.setAdapter(adapterNotify);


        sendRequestGetNewNotify();
        refresh();


        layBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initToolbar();
    }


    private void initToolbar() {
        int width = U.getDeviceWidth() / 4;
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, width);
        layToolbarMain.setLayoutParams(layoutParams);
        layToolbarMain.setBackgroundColor(getResources().getColor(R.color.color_notify));
        imgIcon.setImageResource(R.drawable.ic_flag);
        txtName.setText("پیغام ها");

    }


    public void refresh() {

        sendRequestGetNewNotify();

    }

    private void sendRequestGetNewNotify() {
        Notify lastNotify = new Select()
                .from(Notify.class)
                .orderBy("NotifyCode desc")
                .limit(1)
                .executeSingle();
        if (lastNotify == null) {
            WebService.sendGetNotifiesRequest(0, false);
        } else {
            WebService.sendGetNotifiesRequest(0, false);
        }
    }

    public void onEventMainThread(EventOnGetNotifiesResponse event) {
        Logger.d("ActivityShowNotify : EventOnGetNotifiesResponse is raised.");
        dlgWaiting.DialogWaitingClose();
        getNotifyFromDB();


    }

    public void onEventMainThread(EventOnGetErrorGetNotifies event) {
        Logger.d("ActivityShowNotify : EventOnGetErrorGetNotifies is raised.");
        dlgWaiting.DialogWaitingClose();
        getNotifyFromDB();

        if (event.getErrorType() == EnumInternetErrorType.NO_INTERNET_ACCESS) {
            U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
        }
    }

    public void onEventMainThread(EventOnErrorInConnectingToServer errorInConnectingToServer) {
        dlgWaiting.DialogWaitingClose();
    }

    public void getNotifyFromDB() {
        notifies = new Select()
                .from(Notify.class)
//                .where("IsSeen = 0")
//                .orderBy("NotifyCode desc")
                .execute();
        adapterNotify.updateList(notifies);

        if (notifies.size() == 0) {
            txtShowMessage.setText(R.string.no_new_notifies);
            txtShowMessage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
        G.context = this;
    }


    public void onEventMainThread(EventOnNotifyDeleted event) {
        getNotifyFromDB();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
