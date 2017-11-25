package ir.jahanmir.araxx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.jahanmir.araxx.adapter.AdapterChargeOnlineCategory;
import ir.jahanmir.araxx.classes.DialogClass;
import ir.jahanmir.araxx.classes.Logger;
import ir.jahanmir.araxx.classes.WebService;
import ir.jahanmir.araxx.enums.EnumGridType;
import ir.jahanmir.araxx.events.EventOnErrorInConnectingToServer;
import ir.jahanmir.araxx.events.EventOnGetChargeOnlineForLoadCategory;
import ir.jahanmir.araxx.gson.ChargeOnlineCategory;

public class ActivityChargeOnlineCategory extends AppCompatActivity {

    int isClub;
    int whichMenuItem;
    int columnsOfGrid = 2;
    int typeOfGrid = 1;
    long groupCode;
    boolean isOneItem = false;


    @Bind(R.id.lstGroup)
    RecyclerView lstGroup;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.txtShowMessage)
    TextView txtShowMessage;
    @Bind(R.id.imgToolbar)
    ImageView imgToolbar;

    //    @Bind(R.id.layBtnBack) LinearLayout layBtnBack;
    @Bind(R.id.txtPageTitle)
    TextView txtPageTitle;

    @Bind(R.id.layBtnBack)
    LinearLayout layBtnBack;

    DialogClass dlgWaiting;
    AdapterChargeOnlineCategory adapterChargeOnlineCategory;
    LinearLayoutManager linearLayoutManager;
    List<ChargeOnlineCategory> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_online_category);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        dlgWaiting = new DialogClass(this);
        dlgWaiting.DialogWaiting();

        Intent intent = getIntent();
        isClub = intent.getIntExtra("IS_CLUB", isClub);
        whichMenuItem = intent.getIntExtra("WHICH_MENU_ITEM", whichMenuItem);
        groupCode = intent.getLongExtra("GROUP_CODE", 0);

        initView();

    }

    private void initView() {

        layBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Logger.d("FragmentChargeOnlineGroup : onActivityCreated()");

//        txtPageTitle.setText(U.getMenuItemName(whichMenuItem));
        txtPageTitle.setText("دسته بندی ها");
//        U.getMenuItemIcon(imgToolbar,whichMenuItem);
        imgToolbar.setImageResource(R.drawable.ic_factor_detail_toolbar);

        adapterChargeOnlineCategory = new AdapterChargeOnlineCategory(categories, isClub, whichMenuItem, groupCode);
        /** baraye anke moshakhas konim grid
         * ma be surate list namayesh dade sahvad ya grid chand setune*/
        typeOfGrid = G.localMemory.getInt("GRID_TYPE", 1);
        if (typeOfGrid == EnumGridType.GRID) {
            columnsOfGrid = getResources().getInteger(R.integer.grid_columns);
        } else {
            columnsOfGrid = getResources().getInteger(R.integer.grid_list_columns);
        }
        linearLayoutManager = new GridLayoutManager(G.context, columnsOfGrid);
        lstGroup.setLayoutManager(linearLayoutManager);
        lstGroup.setHasFixedSize(true);
        lstGroup.setAdapter(adapterChargeOnlineCategory);

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

        WebService.sendChargeOnlineForLoadCategoryRequest(isClub, groupCode, whichMenuItem);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                Logger.d("ActivityShowFactors : swipeRefreshLayout onRefresh()");
                WebService.sendChargeOnlineForLoadCategoryRequest(isClub, groupCode, whichMenuItem);
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

    public void onEventMainThread(EventOnGetChargeOnlineForLoadCategory event) {
        Logger.d("FragmentChargeOnlineGroup : EventOnGetChargeOnlineForLoadGroups is raised");
        dlgWaiting.DialogWaitingClose();
        swipeRefreshLayout.setRefreshing(false);
        categories = event.getChargeOnlineCategoryList();
        if (categories.size() == 1) {
            Intent intent = new Intent(G.context, ActivityChargeOnlineGroupPackage.class);
            intent.putExtra("WHICH_MENU_ITEM", whichMenuItem);
            intent.putExtra("IS_CLUB", isClub);
            intent.putExtra("GROUP_CODE", groupCode);
            intent.putExtra("isOneItem", isOneItem);
            intent.putExtra("CATEGORY_CODE", categories.get(0).code);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            G.context.startActivity(intent);
        } else {
            if (categories.size() == 0) {
                txtShowMessage.setVisibility(View.VISIBLE);
                txtShowMessage.setText("موردی یافت نشد.");
            } else {
                adapterChargeOnlineCategory.updateList(categories);
            }
        }

    }

    public void onEventMainThread(EventOnErrorInConnectingToServer errorInConnectingToServer) {
        dlgWaiting.DialogWaitingClose();
    }
}
