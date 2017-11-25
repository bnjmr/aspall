package ir.jahanmir.araxx;

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
import ir.jahanmir.araxx.adapter.AdapterFeshfeshe;
import ir.jahanmir.araxx.classes.DialogClass;
import ir.jahanmir.araxx.classes.Logger;
import ir.jahanmir.araxx.classes.U;
import ir.jahanmir.araxx.classes.WebService;
import ir.jahanmir.araxx.component.PersianTextViewThin;
import ir.jahanmir.araxx.enums.EnumResponse;
import ir.jahanmir.araxx.events.EventOnClickedEndFeshfeshe;
import ir.jahanmir.araxx.events.EventOnClickedStartFeshfeshe;
import ir.jahanmir.araxx.events.EventOnClickedYesOnYesNoDialog;
import ir.jahanmir.araxx.events.EventOnErrorInConnectingToServer;
import ir.jahanmir.araxx.events.EventOnGetCurrentFeshFesheResponse;
import ir.jahanmir.araxx.events.EventOnGetEndFeshFeshesResponse;
import ir.jahanmir.araxx.events.EventOnGetErrorEndFeshFeshes;
import ir.jahanmir.araxx.events.EventOnGetErrorLoadFeshFeshes;
import ir.jahanmir.araxx.events.EventOnGetErrorStartFeshFeshes;
import ir.jahanmir.araxx.events.EventOnGetLoadFeshFeshesResponse;
import ir.jahanmir.araxx.events.EventOnGetStartFeshFeshesResponse;
import ir.jahanmir.araxx.events.EventOnNoAccessServerResponse;
import ir.jahanmir.araxx.model.Feshfeshe;


/**
 * Created by Microsoft on 4/2/2016.
 */
public class ActivityShowFeshfeshe extends AppCompatActivity {

    @Bind(R.id.lstFeshfeshe)
    RecyclerView lstFeshfeshe;

    @Bind(R.id.txtShowMessage)
    TextView txtShowMessage;
    @Bind(R.id.txtCurrentFeshfesheExpireDate)
    TextView txtCurrentFeshfesheExpireDate;
    @Bind(R.id.txtCurrentFeshfesheTraffic)
    TextView txtCurrentFeshfesheTraffic;
    @Bind(R.id.layCurrentFeshfeshe)
    FrameLayout layCurrentFeshfeshe;
    @Bind(R.id.imgEndCurrentFeshfesheRequest)
    LinearLayout imgEndCurrentFeshfesheRequest;

    @Bind(R.id.layBtnClose)
    FrameLayout layBtnClose;

    @Bind(R.id.layToolbarMain)
    LinearLayout layToolbarMain;


    @Bind(R.id.imgIcon)
    ImageView imgIcon;

    @Bind(R.id.txtName)
    PersianTextViewThin txtName;


    AdapterFeshfeshe adapterFeshfeshe;
    List<Feshfeshe> feshfeshes = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    DialogClass dlgWaiting;
    DialogClass dlgNewWaiting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_feshfeshe);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        dlgNewWaiting = new DialogClass(this);
        dlgNewWaiting.DialogWaiting();

        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setStatusBarColor(ContextCompat.getColor(G.context, R.color.dark_dark_grey));

        layCurrentFeshfeshe.setVisibility(View.GONE);
        layBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        adapterFeshfeshe = new AdapterFeshfeshe(feshfeshes, this);
        linearLayoutManager = new LinearLayoutManager(G.context);
        lstFeshfeshe.setLayoutManager(linearLayoutManager);
        lstFeshfeshe.setAdapter(adapterFeshfeshe);


        WebService.sendGetCurrentFeshFeshesRequest();
        WebService.sendLoadFeshFeshesRequest();



        imgEndCurrentFeshfesheRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogClass dlgClass = new DialogClass();
                dlgClass.showQuestionForEndFeshfesheDialog();
            }
        });

        initToolbar();
    }

    private void initToolbar() {
        int width = U.getDeviceWidth() / 4;
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, width);
        layToolbarMain.setLayoutParams(layoutParams);
        layToolbarMain.setBackgroundColor(getResources().getColor(R.color.color_feshfeshe));
        imgIcon.setImageResource(R.drawable.ic_feshfeshe);
        txtName.setText("فشفشه");

    }


    public void onEventMainThread(EventOnGetLoadFeshFeshesResponse event) {
        Logger.d("ActivityShowFeshfeshe : EventOnGetLoadFeshFeshesResponse is raised.");
        dlgNewWaiting.DialogWaitingClose();
        if (event.getFeshfesheList().size() == 0) {
            txtShowMessage.setVisibility(View.VISIBLE);
            feshfeshes = new ArrayList<>();
            adapterFeshfeshe.updateList(feshfeshes);
            txtShowMessage.setText("موردی یافت نشد.");
        } else {
            txtShowMessage.setVisibility(View.GONE);
            feshfeshes = event.getFeshfesheList();
            adapterFeshfeshe.updateList(feshfeshes);
        }
    }

    public void onEventMainThread(EventOnGetErrorLoadFeshFeshes event) {
        Logger.d("ActivityShowFeshfeshe : EventOnGetErrorLoadFeshFeshes is raised.");
        dlgNewWaiting.DialogWaitingClose();
        getFeshfesheFromDB();
    }

    public void onEventMainThread(EventOnClickedStartFeshfeshe event) {
        Logger.d("ActivityShowFeshfeshe : EventOnClickedStartFeshfeshe is raised.");
        dlgWaiting = new DialogClass();
        dlgWaiting.DialogWaiting();
        WebService.sendStartFeshFeshesRequest(event.getFeshfesheCode());
    }

    public void onEventMainThread(EventOnClickedEndFeshfeshe event) {
        Logger.d("ActivityShowFeshfeshe : EventOnClickedEndFeshfeshe is raised.");
        dlgWaiting = new DialogClass();
        dlgWaiting.DialogWaiting();
        WebService.sendGetEndFeshFeshesRequest();
    }

    public void onEventMainThread(EventOnGetStartFeshFeshesResponse event) {
        Logger.d("ActivityShowFeshfeshe : EventOnGetStartFeshFeshesResponse is raised.");
        if (dlgWaiting != null)
            dlgWaiting.DialogWaitingClose();
        if (event.getStatus() != EnumResponse.OK) {
            DialogClass dlgMessage = new DialogClass();
            dlgMessage.showMessageDialog("خطا", event.getMessage());
        } else {
            WebService.sendLoadFeshFeshesRequest();
            WebService.sendGetCurrentFeshFeshesRequest();
        }
    }

    public void onEventMainThread(EventOnGetEndFeshFeshesResponse event) {
        Logger.d("ActivityShowFeshfeshe : EventOnGetEndFeshFeshesResponse is raised.");
        if (dlgWaiting != null)
            dlgWaiting.DialogWaitingClose();

        WebService.sendLoadFeshFeshesRequest();
        WebService.sendGetCurrentFeshFeshesRequest();

    }

    public void onEventMainThread(EventOnGetErrorStartFeshFeshes event) {
        Logger.d("ActivityShowFeshfeshe : EventOnGetErrorStartFeshFeshes is raised.");
        if (dlgWaiting != null)
            dlgWaiting.DialogWaitingClose();
    }

    public void onEventMainThread(EventOnGetCurrentFeshFesheResponse event) {
        Logger.d("ActivityShowFeshfeshe : EventOnGetCurrentFeshFesheResponse is raised.");
        if (event.getCurrentFeshFesheResponse() == null || event.getCurrentFeshFesheResponse().Expire.equals("")) {
            layCurrentFeshfeshe.setVisibility(View.GONE);
        } else {
            layCurrentFeshfeshe.setVisibility(View.VISIBLE);
            txtCurrentFeshfesheExpireDate.setText(event.getCurrentFeshFesheResponse().Expire);
            txtCurrentFeshfesheTraffic.setText("ترافیک باقیمانده : " + event.getCurrentFeshFesheResponse().Traffic + "مگابایت ");
        }
    }

    public void onEventMainThread(EventOnGetErrorEndFeshFeshes event) {
        Logger.d("ActivityShowFeshfeshe : EventOnGetErrorEndFeshFeshes is raised.");
        if (dlgWaiting != null)
            dlgWaiting.DialogWaitingClose();
    }

    public void onEventMainThread(EventOnNoAccessServerResponse event) {
        Logger.d("ActivityShowFeshfeshe : EventOnNoAccessServerResponse is raised.");

        if (dlgWaiting != null)
            dlgWaiting.DialogWaitingClose();
        getFeshfesheFromDB();
    }

    public void onEventMainThread(EventOnErrorInConnectingToServer errorInConnectingToServer) {
        dlgNewWaiting.DialogWaitingClose();
    }

    public void onEventMainThread(EventOnClickedYesOnYesNoDialog onYesNoDialog) {
        switch (onYesNoDialog.getYesNoKind()) {
            case StartFeshfeshe:
                EventBus.getDefault().post(new EventOnClickedStartFeshfeshe(Integer.parseInt(onYesNoDialog.getDate())));
                break;
        }
    }


    private void getFeshfesheFromDB() {
        feshfeshes = new Select().from(Feshfeshe.class).execute();
        adapterFeshfeshe.updateList(feshfeshes);
    }

    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
