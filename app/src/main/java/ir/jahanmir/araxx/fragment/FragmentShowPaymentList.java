package ir.jahanmir.araxx.fragment;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.jahanmir.araxx.ActivityShowBankList;
import ir.jahanmir.araxx.G;
import ir.jahanmir.araxx.R;
import ir.jahanmir.araxx.adapter.AdapterPayment;
import ir.jahanmir.araxx.classes.DialogClass;
import ir.jahanmir.araxx.classes.Logger;
import ir.jahanmir.araxx.classes.U;
import ir.jahanmir.araxx.classes.WebService;
import ir.jahanmir.araxx.events.EventOnErrorInConnectingToServer;
import ir.jahanmir.araxx.events.EventOnGetErrorGetPayment;
import ir.jahanmir.araxx.events.EventOnGetPaymentResponse;
import ir.jahanmir.araxx.events.EventOnNoAccessServerResponse;
import ir.jahanmir.araxx.model.Payment;


public class FragmentShowPaymentList extends Fragment implements View.OnClickListener {


    @Bind(R.id.lstPayment)
    RecyclerView lstPayment;

    @Bind(R.id.layBtnBack)
    LinearLayout layBtnBack;
    @Bind(R.id.layLoading)
    LinearLayout layLoading;
    @Bind(R.id.txtShowMessage)
    TextView txtShowMessage;

    @Bind(R.id.actionBtnPayment)
    FloatingActionButton actionBtnPayment;
    @Bind(R.id.layExpandPayment)
    LinearLayout layExpandPayment;
    @Bind(R.id.edtPayment)
    EditText edtPayment;
    @Bind(R.id.layBtnPay)
    LinearLayout layBtnPay;

    AdapterPayment adapterPayment;
    List<Payment> payments = new ArrayList<>();
    private String current = "";
    DialogClass dlgPayMessage;


//    long orderId; // this filed use when user request to pay, and get this item from webservice payforpay
    /**
     * baraye inke bedunim range dokme bayad chejuri bashad
     **/
    boolean isCloseButtonShow = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_payment_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        layLoading.setVisibility(View.GONE);

        if (Build.VERSION.SDK_INT >= 21)
            getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(G.context, R.color.dark_dark_grey));

        actionBtnPayment.setOnClickListener(this);
        layBtnPay.setOnClickListener(this);

        adapterPayment = new AdapterPayment(payments);
        lstPayment.setLayoutManager(new LinearLayoutManager(getActivity()));
        lstPayment.setAdapter(adapterPayment);


        WebService.sendGetPaymentRequest();



        //agar karbar ejaze pardakht Online ra nadarad In gozine hiden shavad
        if(!G.currentLicense.PayOnline){
            layExpandPayment.setVisibility(View.GONE);
        }

        edtPayment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.toString().equals(""))
                    return;

                if (!charSequence.toString().equals(current)) {
                    edtPayment.removeTextChangedListener(this);
                    String cleanString = charSequence.toString().replace(",", "");
                    NumberFormat formatter = new DecimalFormat("#,###,###,###", new DecimalFormatSymbols(Locale.US));
                    String ss = formatter.format(Double.valueOf(cleanString));
                    current = ss;
                    edtPayment.setText(ss);
                    edtPayment.setSelection(ss.length());
                    edtPayment.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        layBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.actionBtnPayment:
                if (!layExpandPayment.isShown()) {
                    U.expand(layExpandPayment);
                    actionBtnPayment.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(G.currentActivity, R.color.red)));
                    actionBtnPayment.setRippleColor(ContextCompat.getColor(G.currentActivity, R.color.red));
                    actionBtnPayment.setImageResource(R.drawable.ic_close_white_36dp);
                    isCloseButtonShow = true;
                } else {
                    U.collapse(layExpandPayment);
                    actionBtnPayment.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(G.currentActivity, R.color.colorAccent)));
                    actionBtnPayment.setRippleColor(ContextCompat.getColor(G.currentActivity, R.color.colorAccent));
                    actionBtnPayment.setImageResource(R.drawable.sv_plus_white);
                    isCloseButtonShow = false;
                }
                break;

            case R.id.layBtnPay:
                final String payPrice = edtPayment.getText().toString().trim().replace(",", "");
//                txtShowErrorMessage.setText("");
                if (!payPrice.equals("")) {
//                    String encodedHash = Uri.encode("#");
//                    String ussd = "*" + "789" + encodedHash;
//                    startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + ussd)));
//                    WebService.sendPayOnlineForPayRequest(Integer.parseInt(payPrice));
//                    layLoading.setVisibility(View.VISIBLE);

                    Intent intent = new Intent(getActivity().getApplicationContext(), ActivityShowBankList.class);
                    intent.putExtra("FACTOR_CODE", 0);
                    intent.putExtra("MONEY", Integer.parseInt(payPrice));
                    startActivity(intent);
                } else {

                    new DialogClass().showMessageDialog(getString(R.string.error), getString(R.string.input_incorrect_error));

//                    txtShowErrorMessage.setText("لطفا مقدار صحیحی برای مبلغ وارد کنید. ");
                }
                break;
        }
    }


    //    /** darkhaste inke mikhahim yek factor ra start konim az tarafe adapterFactor*/
//    public void onEventMainThread(EventOnClickedStartFactor event){
//        Logger.d("ActivityShowPayments : EventOnClickedStartFactor is raised");
//        WebService.sendStartFactorRequest(event.getFactorId());
//        layLoading.setVisibility(View.VISIBLE);
//    }
//    /** darkhaste start factor*/
//    public void onEventMainThread(EventOnGetStartFactorResponse event){
//        Logger.d("ActivityShowPayments : EventOnGetStartFactorResponse is raised");
//        layLoading.setVisibility(View.GONE);
//        if(event.getStatus()){
//            if(dlgPayMessage != null)
//                dlgPayMessage.cancelPayMessage();
//            swipeRefreshLayout.post(new Runnable() {
//                @Override
//                public void run() {
//                    swipeRefreshLayout.setRefreshing(true);
//                }
//            });
//            WebService.sendGetPaymentRequest();
//        }
//    }
//    public void onEventMainThread(EventOnGetErrorStartFactor event){
//        Logger.d("ActivityShowPayments : EventOnGetErrorStartFactor is raised");
//        layLoading.setVisibility(View.GONE);
//        if(event.getErrorType() == EnumInternetErrorType.NO_INTERNET_ACCESS){
//            U.toast("ارتباط اینترنتی خود را چک کنید.");
//        }else{
//            U.toast("خطا در دریافت اطلاعات از سرور");
//        }
//    }
//    public void onEventMainThread(EventOnGetCheckOrderIdResultResponse event){
//        Logger.d("ActivityShowPayments : EventOnGetCheckOrderIdResultResponse is raised");
//        layLoading.setVisibility(View.GONE);
//        if(event.isResult()){
//            if(event.getFactorCode() == 0){
//                getActivity().finish();
//            }else{
//                // TODO: 4/11/2016 show dialog to start factor
//                dlgPayMessage = new DialogClass();
//                dlgPayMessage.showPayMessage(event.getFactorCode(),"نتیجه پرداخت",event.getMessage());
//            }
//        }else {
//            DialogClass dlgError = new DialogClass();
//            dlgError.showMessageDialog("خطا",event.getMessage());
//        }
//    }
//    public void onEventMainThread(EventOnGetErrorCheckOrderIdResult event){
//        Logger.d("ActivityShowPayments : EventOnGetErrorCheckOrderIdResult is raised");
//        layLoading.setVisibility(View.GONE);
//    }
//    public void onEventMainThread(EventOnGetPayOnlineForPayResponse event){
//        Logger.d("ActivityShowPayments : EventOnGetPayOnlineForPayResponse is raised");
//        layLoading.setVisibility(View.GONE);
//        orderId = event.getOrderId();
//        String ussdCode = event.getOrderUssd();
//        startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + ussdCode)));
//    }
//    public void onEventMainThread(EventOnGetErrorPayOnlineForPay event){
//        Logger.d("ActivityShowPayments : EventOnGetErrorPayOnlineForPay is raised");
//        orderId = 0;
//        layLoading.setVisibility(View.GONE);
//    }
    public void onEventMainThread(EventOnGetPaymentResponse event) {
        Logger.d("ActivityShowPayments : EventOnGetPaymentResponse is raised");
        payments = event.getPayments();
        adapterPayment.updateList(payments);
        if (payments.size() == 0) {
            txtShowMessage.setVisibility(View.VISIBLE);
            txtShowMessage.setText("موردی یافت نشد.");
        } else {
            txtShowMessage.setVisibility(View.GONE);
        }
    }

    public void onEventMainThread(EventOnGetErrorGetPayment event) {
        Logger.d("ActivityShowPayments : EventOnGetErrorGetPayment is raised");
//        txtShowMessage.setVisibility(View.VISIBLE);
//        txtShowMessage.setText("خطا در دریافت اطلاعات از سرور لطفا دوباره تلاش کنید.");
        getPaymentFromDB();
    }

    public void onEventMainThread(EventOnNoAccessServerResponse event) {
        Logger.d("ActivityShowPayments : EventOnNoAccessServerResponse is raised");
        getPaymentFromDB();
    }

    public void onEventMainThread(EventOnErrorInConnectingToServer errorInConnectingToServer){
        layLoading.setVisibility(View.GONE);
    }

    private void getPaymentFromDB() {
        payments = new Select().from(Payment.class).execute();
        adapterPayment.updateList(payments);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isCloseButtonShow) {
            actionBtnPayment.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(G.currentActivity, R.color.colorAccent)));
            actionBtnPayment.setRippleColor(ContextCompat.getColor(G.currentActivity, R.color.red));
        } else {
            actionBtnPayment.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(G.currentActivity, R.color.red)));
            actionBtnPayment.setRippleColor(ContextCompat.getColor(G.currentActivity, R.color.colorAccent));
        }


//        if (orderId != 0){
//            WebService.sendCheckOrderIdResultRequest(orderId);
//            layLoading.setVisibility(View.VISIBLE);
//            orderId = 0;
//        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
