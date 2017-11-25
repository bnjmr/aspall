package ir.jahanmir.araxx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
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
import ir.jahanmir.araxx.adapter.AdapterChargeOnlineGroup;
import ir.jahanmir.araxx.classes.DialogClass;
import ir.jahanmir.araxx.classes.Logger;
import ir.jahanmir.araxx.classes.U;
import ir.jahanmir.araxx.classes.WebService;
import ir.jahanmir.araxx.component.PersianTextViewThin;
import ir.jahanmir.araxx.enums.EnumGridType;
import ir.jahanmir.araxx.events.EventOnClickedChargeOnlineGroup;
import ir.jahanmir.araxx.events.EventOnErrorInConnectingToServer;
import ir.jahanmir.araxx.events.EventOnGetChargeOnlineForCountCategoryResponse;
import ir.jahanmir.araxx.events.EventOnGetChargeOnlineForLoadGroups;
import ir.jahanmir.araxx.gson.ChargeOnlineGroup;

public class ActivityChargeOnlineGroup extends AppCompatActivity {

    int isClub;
    int whichMenuItem;
    int columnsOfGrid = 2;
    int typeOfGrid = 1;

    @Bind(R.id.lstGroup)
    RecyclerView lstGroup;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
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


    AdapterChargeOnlineGroup adapterChargeOnlineGroup;
    LinearLayoutManager linearLayoutManager;
    List<ChargeOnlineGroup> groups = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_online_group);
        ButterKnife.bind(this);
        initToolbar();
        Intent intent = getIntent();
        isClub = intent.getIntExtra("IS_CLUB", 0);
        whichMenuItem = intent.getIntExtra("WHICH_MENU_ITEM", 0);
        dlgWaiting = new DialogClass(this);
        dlgWaiting.DialogWaiting();

        initView();
    }

    private void initToolbar() {
        int width = U.getDeviceWidth() / 4;
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, width);
        layToolbarMain.setLayoutParams(layoutParams);
        layToolbarMain.setBackgroundColor(getResources().getColor(R.color.color_factor));
        imgIcon.setImageResource(R.drawable.ic_charge_online);
        txtName.setText("گروه ها");

    }

    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
        G.context = this;
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {


        Logger.d("FragmentChargeOnlineGroup : onActivityCreated()");


        layBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//        txtPageTitle.setText(U.getMenuItemName(whichMenuItem));
//        txtPageTitle.setText("گروه ها");
//        U.getMenuItemIcon(imgToolbar,whichMenuItem);

        adapterChargeOnlineGroup = new AdapterChargeOnlineGroup(groups, isClub, whichMenuItem,ActivityChargeOnlineGroup.this);
        /** baraye anke moshakhas konim grid
         * ma be surate list namayesh dade sahvad ya grid chand setune*/
        typeOfGrid = G.localMemory.getInt("GRID_TYPE", 1);
        if (typeOfGrid == EnumGridType.GRID) {
            columnsOfGrid = getResources().getInteger(R.integer.grid_columns);
        } else {
//            columnsOfGrid = getResources().getInteger(R.integer.grid_list_columns);
            columnsOfGrid = getResources().getInteger(R.integer.grid_columns);
        }
        linearLayoutManager = new GridLayoutManager(G.context, columnsOfGrid);
        lstGroup.setLayoutManager(linearLayoutManager);
        lstGroup.setHasFixedSize(true);
        lstGroup.setAdapter(adapterChargeOnlineGroup);

        /** be dalil inke dar fragment
         *  swipeRefreshLayout.setRefreshing(true);
         *  dar ebteda kar nemikonad ke listener
         *  swipeRefreshLayout.setOnRefreshListener ra seda bezanad be surate dasti aan ra seda mizanim.*/
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });

        WebService.sendChargeOnlineForLoadGroupsRequest(isClub);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                Logger.d("ActivityShowFactors : swipeRefreshLayout onRefresh()");
                WebService.sendChargeOnlineForLoadGroupsRequest(isClub);
            }
        });

//        layBtnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getActivity().onBackPressed();
//            }
//        });
//
    }

    public void onEventMainThread(EventOnGetChargeOnlineForLoadGroups event) {
        Logger.d("FragmentChargeOnlineGroup : EventOnGetChargeOnlineForLoadGroups is raised");
        dlgWaiting.DialogWaitingClose();
        swipeRefreshLayout.setRefreshing(false);
        groups = event.getChargeOnlineGroups();
        if (groups.size() == 0) {
            txtShowMessage.setVisibility(View.VISIBLE);
            txtShowMessage.setText("موردی یافت نشد.");
        } else {
            adapterChargeOnlineGroup.updateList(groups);
        }
    }

    public void onEventMainThread(EventOnClickedChargeOnlineGroup event) {
        Logger.d("ActivityChargeOnline : EventOnClickedChargeOnlineGroup is raised");
        WebService.sendChargeOnlineForCountCategoryRequest(event.getIsClub(), event.getGroupCode(), event.getWhichMenuItem());
        dlgWaiting.DialogWaitingClose();
    }

    public void onEventMainThread(EventOnGetChargeOnlineForCountCategoryResponse event) {
        dlgWaiting.DialogWaitingClose();

        if (G.categorySize > 1) {
            Intent intentTRAFFIC = new Intent(this, ActivityChargeOnlineCategory.class);
            intentTRAFFIC.putExtra("IS_CLUB", event.isClub());
            intentTRAFFIC.putExtra("WHICH_MENU_ITEM", event.getWhichMenuItem());
            intentTRAFFIC.putExtra("GROUP_CODE", event.getGroupCode());
            startActivity(intentTRAFFIC);
        } else {
            Intent i = new Intent(G.context, ActivityChargeOnlineGroupPackage.class);
            i.putExtra("WHICH_MENU_ITEM", event.getWhichMenuItem());
            i.putExtra("IS_CLUB", event.isClub());
            i.putExtra("GROUP_CODE", event.getGroupCode());
            i.putExtra("CATEGORY_CODE", -1);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            G.context.startActivity(i);
        }


    }

    public void onEventMainThread(EventOnErrorInConnectingToServer errorInConnectingToServer) {
        dlgWaiting.DialogWaitingClose();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ButterKnife.unbind(this);

    }
}
