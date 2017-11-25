package ir.jahanmir.araxx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.jahanmir.araxx.adapter.AdapterChargeOnlineGroupPackage;
import ir.jahanmir.araxx.classes.DialogClass;
import ir.jahanmir.araxx.classes.Logger;
import ir.jahanmir.araxx.classes.U;
import ir.jahanmir.araxx.classes.WebService;
import ir.jahanmir.araxx.component.PersianTextViewThin;
import ir.jahanmir.araxx.enums.EnumResponse;
import ir.jahanmir.araxx.events.EventOnClickSlecetChargeOnlinePakage;
import ir.jahanmir.araxx.events.EventOnErrorInConnectingToServer;
import ir.jahanmir.araxx.events.EventOnGetChargeOnlineForLoadPackages;
import ir.jahanmir.araxx.events.EventOnGetChargeOnlineForSelectPackage;
import ir.jahanmir.araxx.events.EventOnGetErrorChargeOnlineForLoadPackages;
import ir.jahanmir.araxx.gson.ChargeOnlineGroupPackage;


public class ActivityChargeOnlineGroupPackage extends AppCompatActivity {
    @Bind(R.id.lstPackage)
    RecyclerView lstPackage;

    @Bind(R.id.txtShowMessage)
    TextView txtShowMessage;

    @Bind(R.id.layBtnClose)
    FrameLayout layBtnClose;

    @Bind(R.id.layToolbarMain)
    LinearLayout layToolbarMain;


    @Bind(R.id.imgIcon)
    ImageView imgIcon;

    @Bind(R.id.txtName)
    PersianTextViewThin txtName;

    DialogClass dlgWaiting;


    AdapterChargeOnlineGroupPackage adapterChargeOnlineGroupPackage;
    LinearLayoutManager linearLayoutManager;
    List<ChargeOnlineGroupPackage> packages = new ArrayList<>();

    int isClub;
    int whichMenuItem;
    long groupCode;
    int categoryCode;
    boolean isOneItem = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_online_group_pakage);

        dlgWaiting = new DialogClass(this);
        dlgWaiting.DialogWaiting();

        Intent intent = getIntent();
        isClub = intent.getIntExtra("IS_CLUB", 0);
        isOneItem = intent.getBooleanExtra("isOneItem", false);
        whichMenuItem = intent.getIntExtra("WHICH_MENU_ITEM", 0);
        groupCode = intent.getLongExtra("GROUP_CODE", 0);
        categoryCode = intent.getIntExtra("CATEGORY_CODE", 0);
        Logger.d("ActivityChargeOnlineGroupPackage : onCreate()");
        ButterKnife.bind(this);
        initView();
        initToolbar();


    }

    private void initToolbar() {
        int width = U.getDeviceWidth() / 4;
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, width);
        layToolbarMain.setLayoutParams(layoutParams);
        layToolbarMain.setBackgroundColor(getResources().getColor(R.color.color_charg_online));
        imgIcon.setImageResource(R.drawable.ic_charge_online);
        txtName.setText("گروه ها");
    }

    @Override
    protected void onResume() {
        super.onResume();
        G.context = this;
        G.currentActivity = this;
        EventBus.getDefault().register(this);

    }

    private void initView() {

dlgWaiting.DialogWaitingClose();


        adapterChargeOnlineGroupPackage = new AdapterChargeOnlineGroupPackage(packages, isClub, groupCode);
        linearLayoutManager = new LinearLayoutManager(G.context);
        lstPackage.setLayoutManager(linearLayoutManager);
        lstPackage.setHasFixedSize(true);
        lstPackage.setAdapter(adapterChargeOnlineGroupPackage);
        /** be dalil inke dar fragment
         *  swipeRefreshLayout.setRefreshing(true);
         *  dar ebteda kar nemikonad ke listener
         *  swipeRefreshLayout.setOnRefreshListener ra seda bezanad be surate dasti aan ra seda mizanim.*/

        WebService.sendChargeOnlineForLoadPackagesRequest(isClub, groupCode, whichMenuItem, categoryCode);


        layBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void onEventMainThread(EventOnGetChargeOnlineForLoadPackages event) {
        Logger.d("ActivityChargeOnlineGroupPackage : EventOnGetChargeOnlineForLoadPackages is raised");
        packages = event.getChargeOnlineGroupPackages();
        if (packages.size() == 0) {
            txtShowMessage.setVisibility(View.VISIBLE);
            txtShowMessage.setText("موردی یافت نشد.");
        } else {
            adapterChargeOnlineGroupPackage.updateList(packages);
        }
    }

    public void onEventMainThread(EventOnGetErrorChargeOnlineForLoadPackages event) {
        Logger.d("ActivityChargeOnlineGroupPackage : EventOnGetErrorChargeOnlineForLoadPackages is raised");
        packages = new ArrayList<>();
        adapterChargeOnlineGroupPackage.updateList(packages);
        txtShowMessage.setVisibility(View.VISIBLE);
        txtShowMessage.setText("خطا در دریافت اطلاعات از سرور لطفا دوباره تلاش کنید.");
    }

    public void onEventMainThread(EventOnClickSlecetChargeOnlinePakage event) {
        Logger.d("ActivityChargeOnlineGroupPackage : EventOnClickedChargeOnlineGroupPackage0 is raised");
        long packageCode = event.getPackageCode();
        long groupCode = event.getGroupCode();
        dlgWaiting.DialogWaiting();
        WebService.sendChargeOnlineForSelectPackageRequest(isClub, packageCode, groupCode);
    }

    public void onEventMainThread(EventOnGetChargeOnlineForSelectPackage event) {
        dlgWaiting.DialogWaitingClose();
        Logger.d("ActivityChargeOnline : EventOnGetChargeOnlineForSelectPackage is raised");
        int result = event.isResult();
        String message = event.getMessage();
        long factorCode = event.getFactorCode();
        if (result == EnumResponse.OK) {
            if (message.length() == 0) {

                Intent intent = new Intent(this, ActivityShowPackageFactorDetail.class);
                intent.putExtra("FACTOR_CODE", factorCode);
                startActivity(intent);
//                fragmentManager.beginTransaction()
//                        .add(ir.aspacrm.my.app.mahanet.R.id.layFragment, FragmentShowFactorDetail.newInstance(factorCode))
//                        .addToBackStack("FragmentShowFactorDetail")
//                        .commit();
            } else {
                DialogClass dlgShow = new DialogClass();
                dlgShow.showMessageDialog("پیغام", "" + message);
            }
        } else {
            DialogClass dlgShow = new DialogClass();
            dlgShow.showMessageDialog("پیغام", "" + message);
        }
    }

    public void onEventMainThread(EventOnErrorInConnectingToServer errorInConnectingToServer){
        dlgWaiting.DialogWaitingClose();
    }
    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);

    }
}
