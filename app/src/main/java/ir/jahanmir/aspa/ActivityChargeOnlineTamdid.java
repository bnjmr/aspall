package ir.jahanmir.aspa;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
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
import ir.jahanmir.aspa.adapter.AdapterFactorDetail;
import ir.jahanmir.aspa.classes.DialogClass;
import ir.jahanmir.aspa.classes.Logger;
import ir.jahanmir.aspa.classes.U;
import ir.jahanmir.aspa.classes.WebService;
import ir.jahanmir.aspa.component.PersianTextViewThin;
import ir.jahanmir.aspa.enums.EnumResponse;
import ir.jahanmir.aspa.events.EventOnErrorInConnectingToServer;
import ir.jahanmir.aspa.events.EventOnGetChargeOnlineForTamdid;
import ir.jahanmir.aspa.events.EventOnGetCheckTarazResponse;
import ir.jahanmir.aspa.events.EventOnGetErrorGetFactorDetail;
import ir.jahanmir.aspa.events.EventOnGetFactorDetailsResponse;
import ir.jahanmir.aspa.model.FactorDetail;

import static ir.jahanmir.aspa.R.id.layBtnBack;


public class ActivityChargeOnlineTamdid extends AppCompatActivity implements View.OnClickListener {


    //    @Bind(R.id.txtFactorDes)
//    TextView txtFactorDes;
//    @Bind(R.id.txtFactorCode)
//    TextView txtFactorCode;
//    @Bind(R.id.txtFactorStartDate)
//    TextView txtFactorStartDate;
//    @Bind(R.id.txtFactorGheymatPaye)
//    TextView txtFactorGheymatPaye;
//    @Bind(R.id.txtFactorGheymat)
//    TextView txtFactorGheymat;
//    @Bind(R.id.txtFactorMaliat)
//    TextView txtFactorMaliat;
//    @Bind(R.id.txtFactorTakhfif)
//    TextView txtFactorTakhfif;
//    @Bind(R.id.txtFactorMablaghePardakhti)
//    TextView txtFactorMablaghePardakhti;
//    @Bind(R.id.txtPay)
//    TextView txtPay;
//    @Bind(R.id.txtShowMessage)
//    TextView txtShowMessage;


    @Bind(R.id.layToolbarMain)
    LinearLayout layToolbarMain;


    @Bind(R.id.imgIcon)
    ImageView imgIcon;

    @Bind(R.id.txtName)
    PersianTextViewThin txtName;


    RecyclerView lstFactorDetail;
    LinearLayout layPay;

    @Bind(R.id.layBtnClose)
    FrameLayout layBtnClose;


    AdapterFactorDetail adapterFactorDetail;
    List<FactorDetail> factorDetails = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    DialogClass dlgWaiting;

    long factorCode;
    long factorPrice;
    int IS_CLUB;
    long orderId;// this filed use when User request to pay, and get this item from webservice chargeOnlineForPay
    DialogClass dlgPayMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_online_tamdid);
        ButterKnife.bind(this);
        dlgWaiting = new DialogClass(this);
        dlgWaiting.DialogWaiting();
        lstFactorDetail = (RecyclerView) findViewById(R.id.lstFactorDetail);
        layPay = (LinearLayout) findViewById(R.id.layPay);
        layPay.setOnClickListener(this);

        Intent intent = getIntent();
        if (intent != null) {
            factorCode = intent.getLongExtra("factorCode", -1);
            IS_CLUB = intent.getIntExtra("IS_CLUB", -1);
        }
        WebService.sendChargeOnlineForTamdidRequest(IS_CLUB);
        initToolbar();
        layBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initToolbar() {
        int width = U.getDeviceWidth() / 4;
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, width);
        layToolbarMain.setLayoutParams(layoutParams);
        layToolbarMain.setBackgroundColor(getResources().getColor(R.color.color_charg_online));
        imgIcon.setImageResource(R.drawable.ic_charge_online);
        txtName.setText("شارژ آنلاین");

    }


    public void onEventMainThread(EventOnGetChargeOnlineForTamdid event) {
        Logger.d("ActivityShowGraph : EventOnGetChargeOnlineForTamdid is raised");
        int result = event.isResult();
        final String message = event.getMessage();
        long factorCode = event.getFactorCode();
        if (result == EnumResponse.OK) {
            WebService.sendGetFactorDetailRequest(factorCode);
        } else {
            Intent intent = new Intent(ActivityChargeOnlineTamdid.this, ActivityChargeOnline.class);
            startActivity(intent);
            finish();
            dlgWaiting.DialogWaitingClose();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    DialogClass.showMessageDialog("پیغام", "" + message);
                }
            }, 500);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
        G.context = this;
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.d("FragmentShowFactorDetail : onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Logger.d("FragmentShowFactorDetail : onStop()");
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d("FragmentShowFactorDetail : onDestroy()");
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent(this, ActivityChargeOnline.class);
        startActivity(intent);
        finish();
    }

    public void onEventMainThread(EventOnGetFactorDetailsResponse event) {
        Logger.d("FragmentShowFactorDetail : EventOnGetFactorDetailsResponse is raised");
//        FactorDetailResponse response = event.getFactorDetailResponse().get(0);
//        factorDetails = event.getFactorDetailResponse();
        factorDetails = new Select().from(FactorDetail.class).orderBy("Price desc").execute();
        final RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        adapterFactorDetail = new AdapterFactorDetail(factorDetails);
        lstFactorDetail.setLayoutManager(layoutManager2);
        lstFactorDetail.setAdapter(adapterFactorDetail);
        dlgWaiting.DialogWaitingClose();

//        txtFactorDes.setText(response.Name);
//        txtFactorGheymatPaye.setText(response.Price + " " + G.currentUserInfo.UnitName);
//        WebService.sendSelectFactorRequest(factorCode);
    }

    public void onEventMainThread(EventOnGetErrorGetFactorDetail event) {
        Logger.d("FragmentShowFactorDetail : EventOnGetErrorGetFactorDetail is raised");
        dlgWaiting.DialogWaitingClose();
//        txtShowMessage.setText("خطا در دریافت اطلاعات از سمت سرور لطفا مجددا تلاش کنید.");
    }

    public void onEventMainThread(EventOnGetCheckTarazResponse event) {
        Logger.d("FragmentShowFactorDetail : EventOnGetCheckTarazResponse is raised");
        int taraz = event.getTaraz();
        boolean CanPayment = event.isCanPayment();
        if (taraz > 0 && CanPayment) {
            Logger.d("FragmentShowFactorDetail : taraz is positive and it's " + taraz);
            // TODO: 5/23/2016 showDialog aya mikhahid az etebar khod pardakht konid ya kheir.
            final Dialog dialog = new Dialog(G.currentActivity, R.style.DialogSlideAnim);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_pay_form_credit);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.getWindow().setGravity(Gravity.BOTTOM);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            LinearLayout layBtnCancel = (LinearLayout) dialog.findViewById(R.id.layBtnCancel);
            LinearLayout layBtnOk = (LinearLayout) dialog.findViewById(R.id.layBtnOk);
            final ImageView imgCloseDialog = (ImageView) dialog.findViewById(R.id.imgCloseDialog);
            TextView txtLayBtnCancel = (TextView) layBtnCancel.findViewById(R.id.txtValue);
            TextView txtLayBtnOk = (TextView) layBtnOk.findViewById(R.id.txtValue);

            txtLayBtnCancel.setText("  ورود به درگاه بانک  ");
            txtLayBtnOk.setText("  پرداخت از اعتبار  ");

            layBtnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WebService.sendPayFactorFromCredit(factorCode);
                    dialog.dismiss();
                    dlgWaiting.DialogWaiting();
                }
            });

            layBtnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(G.context, ActivityShowBankList.class);
                    intent.putExtra("FACTOR_CODE", factorCode);
                    intent.putExtra("MONEY", 0);
                    startActivity(intent);
                    dialog.dismiss();
                }
            });

            imgCloseDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();

        } else {
            Logger.d("FragmentShowFactorDetail : taraz is not positive and it's " + taraz);
            Intent intent = new Intent(G.context, ActivityShowBankList.class);
            intent.putExtra("FACTOR_CODE", factorCode);
            intent.putExtra("MONEY", 0);
            startActivity(intent);
        }
        dlgWaiting.DialogWaitingClose();
    }

    public void onEventMainThread(EventOnErrorInConnectingToServer errorInConnectingToServer) {
        dlgWaiting.DialogWaitingClose();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layPay:
                WebService.sendCheckTaraz(factorCode);
                break;

            case layBtnBack:
                onBackPressed();
                break;

        }
    }
}
