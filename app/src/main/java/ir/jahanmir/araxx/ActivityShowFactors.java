package ir.jahanmir.araxx;

import android.content.Intent;
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
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.jahanmir.araxx.adapter.AdapterFactor;
import ir.jahanmir.araxx.classes.DialogClass;
import ir.jahanmir.araxx.classes.Logger;
import ir.jahanmir.araxx.classes.U;
import ir.jahanmir.araxx.classes.WebService;
import ir.jahanmir.araxx.component.PersianTextViewThin;
import ir.jahanmir.araxx.enums.EnumInternetErrorType;
import ir.jahanmir.araxx.events.EventOnClickedFactorMoreDetail;
import ir.jahanmir.araxx.events.EventOnClickedStartFactor;
import ir.jahanmir.araxx.events.EventOnClickedYesOnYesNoDialog;
import ir.jahanmir.araxx.events.EventOnErrorInConnectingToServer;
import ir.jahanmir.araxx.events.EventOnGetErrorGetFactor;
import ir.jahanmir.araxx.events.EventOnGetErrorGetFactorDetail;
import ir.jahanmir.araxx.events.EventOnGetErrorStartFactor;
import ir.jahanmir.araxx.events.EventOnGetFactorDetailsResponse;
import ir.jahanmir.araxx.events.EventOnGetFactorResponse;
import ir.jahanmir.araxx.events.EventOnGetStartFactorResponse;
import ir.jahanmir.araxx.events.EventOnNoAccessServerResponse;
import ir.jahanmir.araxx.gson.FactorDetailResponse;
import ir.jahanmir.araxx.model.Factor;
import ir.jahanmir.araxx.model.FactorDetail;
import ir.jahanmir.araxx.model.ModelYesNoDialog;

import static ir.jahanmir.araxx.enums.EnumYesNoKind.startFactor;


public class ActivityShowFactors extends AppCompatActivity {


    @Bind(R.id.lstFactor)
    RecyclerView lstFactor;
    @Bind(R.id.layBtnClose)
    FrameLayout layBack;

    @Bind(R.id.txtShowMessage)
    TextView txtShowMessage;

    @Bind(R.id.layToolbarMain)
    LinearLayout layToolbarMain;

    @Bind(R.id.imgIcon)
    ImageView imgIcon;

    @Bind(R.id.txtName)
    PersianTextViewThin txtName;


    AdapterFactor adapterFactor;
    LinearLayoutManager linearLayoutManager;
    List<Factor> factors = new ArrayList<>();
    private String current = "";
    DialogClass dlgWaiting;
    DialogClass dlgNewWaiting;

    /**
     * baraye inke bedunim range dokme bayad chejuri bashad
     **/
    boolean isCloseButtonShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_factors);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        dlgNewWaiting = new DialogClass(this);
        dlgNewWaiting.DialogWaiting();

        initToolbar();

        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setStatusBarColor(ContextCompat.getColor(G.context, R.color.dark_dark_grey));

        adapterFactor = new AdapterFactor(factors, this);
        linearLayoutManager = new LinearLayoutManager(this);
        lstFactor.setLayoutManager(linearLayoutManager);
        lstFactor.setAdapter(adapterFactor);


        WebService.sendGetFactorRequest();


        layBack.setOnClickListener(new View.OnClickListener() {
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
        layToolbarMain.setBackgroundColor(getResources().getColor(R.color.color_factor));
        imgIcon.setImageResource(R.drawable.ic_factor_detail_toolbar);
        txtName.setText("صورتحساب");

    }

    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
    }

    /**
     * darkhaste inke mikhahim yek factor ra start konim az tarafe adapterFactor
     */
    public void onEventMainThread(EventOnClickedStartFactor event) {
        dlgNewWaiting.DialogWaitingClose();

        Logger.d("ActivityShowFactors : EventOnClickedStartFactor is raised");
        DialogClass dialogClass = new DialogClass();
        ModelYesNoDialog modelYesNoDialog = new ModelYesNoDialog("شروع فاکتور", "آیا مطمئن هستید میخواهید این فاکتور را شروع کنید؟", event.getFactorId() + "", startFactor);
        dialogClass.showYesNoDialog(modelYesNoDialog);

    }

    /**
     * darkhaste start factor
     */
    public void onEventMainThread(EventOnGetStartFactorResponse event) {

        Logger.d("ActivityShowFactors : EventOnGetStartFactorResponse is raised");
        dlgNewWaiting.DialogWaitingClose();
        txtShowMessage.setVisibility(View.GONE);

        WebService.sendGetFactorRequest();
    }

    public void onEventMainThread(EventOnGetErrorStartFactor event) {
        dlgNewWaiting.DialogWaitingClose();

        Logger.d("ActivityShowFactors : EventOnGetErrorStartFactor is raised");
        if (event.getErrorType() == EnumInternetErrorType.NO_INTERNET_ACCESS) {
            U.toast("ارتباط اینترنتی خود را چک کنید.");
        } else {
            U.toast("خطا در دریافت اطلاعات از سرور");
        }
    }

    /**
     * daryaft list factor'ha
     */
    public void onEventMainThread(EventOnGetFactorResponse event) {
        Logger.d("ActivityShowFactors : EventOnGetPaymentResponse is raised");
        dlgNewWaiting.DialogWaitingClose();
        txtShowMessage.setVisibility(View.GONE);

        factors = event.getFactorResponses();
        adapterFactor.updateList(factors);
        if (factors.size() == 0) {
            txtShowMessage.setVisibility(View.VISIBLE);
            txtShowMessage.setText("موردی یافت نشد.");
        } else {
            txtShowMessage.setVisibility(View.GONE);
        }
    }

    public void onEventMainThread(EventOnGetErrorGetFactor event) {
        Logger.d("ActivityShowFactors : EventOnGetErrorGetPayment is raised");
        dlgNewWaiting.DialogWaitingClose();
        getFactorFromDB();
//        txtShowMessage.setVisibility(View.VISIBLE);
        txtShowMessage.setText("خطا در دریافت اطلاعات از سرور لطفا دوباره تلاش کنید.");
    }

    public void onEventMainThread(EventOnClickedFactorMoreDetail event) {
        dlgNewWaiting.DialogWaitingClose();

        Logger.d("ActivityShowFactors : EventOnClickedFactorMoreDetail is raised");
        WebService.sendGetFactorDetailRequest(event.getFactorCode());
        dlgWaiting = new DialogClass();
        dlgWaiting.DialogWaiting();
    }

    public void onEventMainThread(EventOnGetFactorDetailsResponse event) {
        dlgNewWaiting.DialogWaitingClose();
        txtShowMessage.setVisibility(View.GONE);

        Logger.d("ActivityShowFactors : EventOnGetFactorDetailsResponse is raised");
        ArrayList<FactorDetailResponse> response = event.getFactorDetailResponse();
        if (response.size() == 0) {
            U.toast("موردی ثبت نشده است.");

//        }else if( response.size() == 1 ) {
//            DialogClass dlgFactorDetail = new DialogClass();
//            dlgFactorDetail.showFactorDetail(response.get(0));
//

        } else {
            String json = new Gson().toJson(response);
            Intent intent = new Intent(G.context, ActivityShowFactorDetail.class);
            Bundle bundle = new Bundle();
            bundle.putString("FACTOR_DETAIL", json);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        if (dlgWaiting != null) {
            dlgWaiting.DialogWaitingClose();
            dlgWaiting = null;
        }
    }

    public void onEventMainThread(EventOnGetErrorGetFactorDetail event) {
        dlgNewWaiting.DialogWaitingClose();
        Logger.d("ActivityShowFactors : EventOnGetErrorGetFactorDetail is raised");
        if (dlgWaiting != null) {
            dlgWaiting.DialogWaitingClose();
            dlgWaiting = null;
        }
        if (event.getErrorType() == EnumInternetErrorType.NO_INTERNET_ACCESS) {
            List<FactorDetail> factorDetails = new Select()
                    .from(FactorDetail.class)
                    .where("ParentId = ?", event.getFactorCode())
                    .execute();

            ArrayList<FactorDetailResponse> response = new ArrayList<>();
            for (FactorDetail detail : factorDetails) {
                FactorDetailResponse detailResponse = new FactorDetailResponse(detail.Name, detail.Des, detail.Price);
                response.add(detailResponse);
            }

//            if (response.size() == 1) {
//                DialogClass dlgFactorDetail = new DialogClass();
//                dlgFactorDetail.showFactorDetail(response.get(0));
//
//            } else {
                Intent intent = new Intent(G.context, ActivityShowFactorDetail.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("FACTOR_DETAIL", response);
                intent.putExtras(bundle);
                startActivity(intent);
//            }

        } else {
            U.toast("خطا در دریافت اطلاعات از سمت سرور لطفا مجددا تلاش کنید.");
        }
    }

    public void onEventMainThread(EventOnNoAccessServerResponse event) {
        Logger.d("ActivityShowFactors : EventOnNoAccessServerResponse is raised");
        dlgNewWaiting.DialogWaitingClose();

        getFactorFromDB();
        if (dlgWaiting != null) {
            dlgWaiting.DialogWaitingClose();
            dlgWaiting = null;
        }

    }

    public void onEventMainThread(EventOnClickedYesOnYesNoDialog dialog) {
        switch (dialog.getYesNoKind()) {
            case startFactor:
                WebService.sendStartFactorRequest(Long.parseLong(dialog.getDate()));
                dlgNewWaiting.DialogWaiting();
                break;
        }
    }

    public void onEventMainThread(EventOnErrorInConnectingToServer errorInConnectingToServer) {
        dlgNewWaiting.DialogWaitingClose();
    }

    private void getFactorFromDB() {
        factors = new Select().from(Factor.class).orderBy("Date desc").execute();
        adapterFactor.updateList(factors);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    /*-------------------------------------------------------------------------------------------------------------*/

}
