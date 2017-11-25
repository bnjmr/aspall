package ir.jahanmir.araxx;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import ir.jahanmir.araxx.adapter.AdapterTicketDetails;
import ir.jahanmir.araxx.classes.DialogClass;
import ir.jahanmir.araxx.classes.Logger;
import ir.jahanmir.araxx.classes.U;
import ir.jahanmir.araxx.classes.WebService;
import ir.jahanmir.araxx.component.PersianTextViewThin;
import ir.jahanmir.araxx.enums.EnumResponse;
import ir.jahanmir.araxx.events.EventOnErrorInConnectingToServer;
import ir.jahanmir.araxx.events.EventOnGetAddTicketDetailsResponse;
import ir.jahanmir.araxx.events.EventOnGetErrorGetTicketDetails;
import ir.jahanmir.araxx.events.EventOnGetTicketDetailsResponse;
import ir.jahanmir.araxx.events.EventOnNoAccessServerResponse;
import ir.jahanmir.araxx.model.TicketDetail;
import ir.jahanmir.araxx.model.Unit;


public class ActivityShowTicketDetails extends AppCompatActivity {

    @Bind(R.id.lstTicketDetail)
    RecyclerView lstTicketDetail;
    @Bind(R.id.layBtnClose)
    FrameLayout layBtnClose;
    @Bind(R.id.layReplay)
    LinearLayout layReplay;
    @Bind(R.id.txtShowMessage)
    TextView txtShowMessage;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.laySendChatMessage)
    LinearLayout laySendChatMessage;
    @Bind(R.id.edtTicketReplay)
    EditText edtTicketReplay;

    AdapterTicketDetails adapterTicketDetail;
    LinearLayoutManager linearLayoutManager;
    List<TicketDetail> tickets = new ArrayList<>();


    @Bind(R.id.layToolbarMain)
    LinearLayout layToolbarMain;


    @Bind(R.id.imgIcon)
    ImageView imgIcon;

    @Bind(R.id.txtName)
    PersianTextViewThin txtName;

    long ticketCode = 0;
    int ticketOpen = 0;
    Unit ticketUnit;

    DialogClass dlgWaiting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ticket_details);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        dlgWaiting = new DialogClass(this);
        dlgWaiting.DialogWaiting();
        //default
        ticketUnit = new Unit();
        ticketUnit.code = 0;

        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setStatusBarColor(ContextCompat.getColor(G.context, R.color.dark_dark_grey));


        ticketCode = getIntent().getExtras().getLong("TICKET_CODE");
        ticketOpen = getIntent().getExtras().getInt("TICKET_OPEN");
        //agar tiket baste shode bood ,emkane replay vojod nadarad
        if (ticketOpen == 0) {
            layReplay.setVisibility(View.INVISIBLE);
        }

        adapterTicketDetail = new AdapterTicketDetails(tickets);
        linearLayoutManager = new LinearLayoutManager(this);
        lstTicketDetail.setLayoutManager(linearLayoutManager);
        lstTicketDetail.setAdapter(adapterTicketDetail);

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
        WebService.sendGetTicketDetailsRequest(ticketCode);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                Logger.d("ActivityShowTickets : swipeRefreshLayout onRefresh()");
                WebService.sendGetTicketDetailsRequest(ticketCode);
            }
        });
        layBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        laySendChatMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = edtTicketReplay.getText().toString().trim();
                if (message.length() == 0) {
                    return;
                } else {
                    WebService.sendAddTicketDetailRequest(ticketCode, ticketUnit.code, message);
                    laySendChatMessage.setOnClickListener(null);
                }
            }
        });
        initToolbar();
    }

    private void initToolbar() {
        int width = U.getDeviceWidth() / 4;
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, width);
        layToolbarMain.setLayoutParams(layoutParams);
        layToolbarMain.setBackgroundColor(getResources().getColor(R.color.color_support));
        imgIcon.setImageResource(R.drawable.ic_support);
        txtName.setText("جزییات تیکت ");

    }

    public void onEventMainThread(EventOnGetTicketDetailsResponse event) {
        dlgWaiting.DialogWaitingClose();

        Logger.d("ActivityShowTicketDetails : EventOnGetTicketDetailsResponse is rasied");
//        tickets = event.getTicketDetails();
//        adapterTicketDetail.updateList(tickets);
        getTicketDetailsFromDB();
        swipeRefreshLayout.setRefreshing(false);
    }

    public void onEventMainThread(EventOnGetErrorGetTicketDetails event) {
        dlgWaiting.DialogWaitingClose();

        Logger.d("ActivityShowTicketDetails : EventOnGetErrorGetTicketDetails is rasied");
        getTicketDetailsFromDB();
        swipeRefreshLayout.setRefreshing(false);
    }

    public void onEventMainThread(EventOnGetAddTicketDetailsResponse event) {
        dlgWaiting.DialogWaitingClose();

        Logger.d("ActivityShowTicketDetails : EventOnGetAddTicketDetailsResponse is rasied");
        swipeRefreshLayout.setRefreshing(true);
        WebService.sendGetTicketDetailsRequest(ticketCode);
        laySendChatMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = edtTicketReplay.getText().toString().trim();
                if (message.length() == 0) {
                    return;
                } else {
                    WebService.sendAddTicketDetailRequest(ticketCode, tickets.get(0).UnitCode, message);
                    laySendChatMessage.setOnClickListener(null);
                }
            }
        });
        if (event.getStatus() == EnumResponse.OK) {
            edtTicketReplay.setText("");
        } else {
            DialogClass dlgMessage = new DialogClass();
            dlgMessage.showMessageDialog("خطا", "خطایی در دریافت تیکت بوجود آمده است.");
        }
    }

    public void onEventMainThread(EventOnErrorInConnectingToServer errorInConnectingToServer) {
        dlgWaiting.DialogWaitingClose();
    }

    public void onEventMainThread(EventOnNoAccessServerResponse event) {
        dlgWaiting.DialogWaitingClose();
        Logger.d("ActivityShowTicketDetails : EventOnNoAccessServerResponse is rasied");
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        getTicketDetailsFromDB();
    }

    private void getTicketDetailsFromDB() {
        tickets = new Select()
                .from(TicketDetail.class)
                .where("parentCode=" + ticketCode)
                .orderBy("Id desc")
                .execute();
        adapterTicketDetail.updateList(tickets);
        ticketUnit = (new Select().from(Unit.class).where("Name = ? ", tickets.get(0).UnitName).executeSingle());
        lstTicketDetail.post(new Runnable() {
            @Override
            public void run() {
                lstTicketDetail.scrollToPosition(tickets.size());
            }
        });
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
