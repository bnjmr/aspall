package ir.jahanmir.aspa;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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
import ir.jahanmir.aspa.adapter.AdapterTicket;
import ir.jahanmir.aspa.classes.DialogClass;
import ir.jahanmir.aspa.classes.Logger;
import ir.jahanmir.aspa.classes.U;
import ir.jahanmir.aspa.classes.WebService;
import ir.jahanmir.aspa.component.PersianTextViewThin;
import ir.jahanmir.aspa.enums.EnumResponse;
import ir.jahanmir.aspa.events.EventOnClickedTicketItem;
import ir.jahanmir.aspa.events.EventOnErrorInConnectingToServer;
import ir.jahanmir.aspa.events.EventOnGetAddTicketResponse;
import ir.jahanmir.aspa.events.EventOnGetErrorAddTicket;
import ir.jahanmir.aspa.events.EventOnGetErrorGetTickets;
import ir.jahanmir.aspa.events.EventOnGetTicketResponse;
import ir.jahanmir.aspa.events.EventOnSendTicketRequest;
import ir.jahanmir.aspa.model.Ticket;


public class ActivityShowTickets extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.lstTicket)
    RecyclerView lstTicket;
    @Bind(R.id.layBtnClose)
    FrameLayout layBtnClose;
    @Bind(R.id.txtShowMessage)
    TextView txtShowMessage;
    @Bind(R.id.txtShowErrorMessage)
    TextView txtShowErrorMessage;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.actionBtnAddTicket)
    FloatingActionButton actionBtnAddTicket;
    @Bind(R.id.layExpandTicket)
    LinearLayout layExpandTicket;


    @Bind(R.id.layBtnNewTicket)
    CardView layBtnNewTicket;


    @Bind(R.id.layToolbarMain)
    LinearLayout layToolbarMain;
    @Bind(R.id.imgIcon)
    ImageView imgIcon;
    @Bind(R.id.txtName)
    PersianTextViewThin txtName;

    AdapterTicket adapterTicket;
    LinearLayoutManager linearLayoutManager;
    List<Ticket> tickets = new ArrayList<>();
    DialogClass dlgWaiting;
    /**
     * baraye inke bedunim range dokme bayad chejuri bashad
     **/
    boolean isCloseButtonShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tickets);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        dlgWaiting = new DialogClass(this);
        dlgWaiting.DialogWaiting();


        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(G.context, R.color.dark_dark_grey));

        }


        ((TextView) (layBtnNewTicket.findViewById(R.id.txtValue))).setText("تیکت جدید");
        layBtnNewTicket.setClickable(true);
        layBtnNewTicket.setOnClickListener(this);
        actionBtnAddTicket.setOnClickListener(this);
        adapterTicket = new AdapterTicket(tickets);
        linearLayoutManager = new LinearLayoutManager(this);
        lstTicket.setLayoutManager(linearLayoutManager);
        lstTicket.setAdapter(adapterTicket);
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
        WebService.sendGetTicketsRequest();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                Logger.d("ActivityShowTickets : swipeRefreshLayout onRefresh()");
                WebService.sendGetTicketsRequest();
            }
        });

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
        layToolbarMain.setBackgroundColor(getResources().getColor(R.color.color_support));
        imgIcon.setImageResource(R.drawable.ic_support);
        txtName.setText("پشتیبانی");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layBtnNewTicket:
                startActivity(new Intent(G.context, ActivitySendTiket.class));
                break;
        }
    }

    public void onEventMainThread(EventOnGetTicketResponse event) {
        dlgWaiting.DialogWaitingClose();

        Logger.d("ActivityShowTickets : EventOnGetTicketResponse is raised.");
        swipeRefreshLayout.setRefreshing(false);
        tickets = new Select().from(Ticket.class).orderBy("Date desc").execute();
//        tickets = event.getTicketResponses();
        adapterTicket.updateList(tickets);
    }

    public void onEventMainThread(EventOnGetErrorGetTickets event) {
        dlgWaiting.DialogWaitingClose();

        Logger.d("ActivityShowTickets : EventOnGetErrorGetTickets is raised.");
        getTicketFromDB();
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void onEventMainThread(EventOnSendTicketRequest event) {
        Logger.d("ActivityShowTickets : EventOnSendTicketRequest is raised.");
        dlgWaiting.DialogWaitingClose();
        dlgWaiting.DialogWaiting();
    }

    public void onEventMainThread(EventOnGetAddTicketResponse event) {
        Logger.d("ActivityShowTickets : EventOnGetAddTicketResponse is raised.");

        if (dlgWaiting != null)
            dlgWaiting.DialogWaitingClose();
        if (event.getStatus() == EnumResponse.OK) {

            U.collapse(layExpandTicket);
            actionBtnAddTicket.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(G.currentActivity, R.color.colorAccent)));
            actionBtnAddTicket.setRippleColor(ContextCompat.getColor(G.currentActivity, R.color.colorAccent));
            actionBtnAddTicket.setImageResource(R.drawable.sv_plus_white);
            isCloseButtonShow = false;

            swipeRefreshLayout.setRefreshing(true);
            WebService.sendGetTicketsRequest();
        }
    }

    public void onEventMainThread(EventOnGetErrorAddTicket event) {
        Logger.d("ActivityShowTickets : EventOnGetErrorAddTicket is raised.");
        dlgWaiting.DialogWaitingClose();

        if (dlgWaiting != null)
            dlgWaiting.DialogWaitingClose();
    }

    private void getTicketFromDB() {
//        List<Ticket> ticketsInDB = new Select()
//                .from( Ticket.class)
//                .where("userId = ? ", G.currentUser.userId)
//                .execute();
//        tickets.clear();
//        for (Ticket t : ticketsInDB){
//            TicketResponse ticket = new TicketResponse();
//            ticket.Priority = t.priority;
//            ticket.State = t.State;
//            ticket.Date = t.Date;
//            ticket.Title = t.title;
//            ticket.code = t.code;
//            ticket.Open = t.open;
//            tickets.add(ticket);
//        }
        tickets = new Select().from(Ticket.class).orderBy("Date desc").execute();
        adapterTicket.updateList(tickets);
    }

    public void onEventMainThread(EventOnClickedTicketItem event) {
        Logger.d("ActivityShowTickets : EventOnClickedTicketItem is raised.");
        dlgWaiting.DialogWaitingClose();
        Intent intent = new Intent(G.context, ActivityShowTicketDetails.class);
        intent.putExtra("TICKET_CODE", event.getCode());
        intent.putExtra("TICKET_OPEN", event.getOpen());
        startActivity(intent);
    }

    public void onEventMainThread(EventOnErrorInConnectingToServer errorInConnectingToServer) {
        dlgWaiting.DialogWaitingClose();
    }


    @Override
    protected void onResume() {
        super.onResume();

        G.currentActivity = this;
//        dlgWaiting.DialogWaiting();
        WebService.sendGetTicketsRequest();
        if (isCloseButtonShow) {
            actionBtnAddTicket.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(G.currentActivity, R.color.red)));
            actionBtnAddTicket.setRippleColor(ContextCompat.getColor(G.currentActivity, R.color.red));
        } else {
            actionBtnAddTicket.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(G.currentActivity, R.color.colorAccent)));
            actionBtnAddTicket.setRippleColor(ContextCompat.getColor(G.currentActivity, R.color.colorAccent));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
