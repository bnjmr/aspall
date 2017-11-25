package ir.jahanmir.aspa.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.jahanmir.aspa.G;
import ir.jahanmir.aspa.R;
import ir.jahanmir.aspa.classes.DialogClass;
import ir.jahanmir.aspa.classes.Logger;
import ir.jahanmir.aspa.classes.WebService;
import ir.jahanmir.aspa.events.EventOnGetCheckTarazResponse;
import ir.jahanmir.aspa.events.EventOnGetErrorCheckTaraz;
import ir.jahanmir.aspa.events.EventOnGetErrorGetFactorDetail;
import ir.jahanmir.aspa.events.EventOnGetFactorDetailsResponse;
import ir.jahanmir.aspa.events.EventOnGetSelectFactorResponse;
import ir.jahanmir.aspa.events.EventOnShowFragmentBankListRequest;
import ir.jahanmir.aspa.gson.FactorDetailResponse;
import ir.jahanmir.aspa.gson.SelectFactorResponse;


public class FragmentShowFactorDetail extends Fragment {


    @Bind(R.id.layShowFactor)
    LinearLayout layShowFactor;
    @Bind(R.id.txtFactorDes)
    TextView txtFactorDes;
    @Bind(R.id.txtFactorCode)
    TextView txtFactorCode;
    @Bind(R.id.txtFactorStartDate)
    TextView txtFactorStartDate;
    @Bind(R.id.txtFactorGheymatPaye)
    TextView txtFactorGheymatPaye;
    @Bind(R.id.txtFactorGheymat)
    TextView txtFactorGheymat;
    @Bind(R.id.txtFactorMaliat)
    TextView txtFactorMaliat;
    @Bind(R.id.txtFactorTakhfif)
    TextView txtFactorTakhfif;
    @Bind(R.id.txtFactorMablaghePardakhti)
    TextView txtFactorMablaghePardakhti;
    @Bind(R.id.txtPay)
    TextView txtPay;
    @Bind(R.id.layPay)
    LinearLayout layPay;
    @Bind(R.id.layLoading)
    LinearLayout layLoading;
    @Bind(R.id.txtShowMessage)
    TextView txtShowMessage;
    @Bind(R.id.layBtnClose)
    LinearLayout layBtnClose;
    long factorCode;
    long orderId;// this filed use when user request to pay, and get this item from webservice chargeOnlineForPay
    DialogClass dlgPayMessage;

    public static FragmentShowFactorDetail newInstance(long factorCode) {
        Bundle args = new Bundle();
        args.putLong("FACTOR_CODE",factorCode);
        FragmentShowFactorDetail fragment = new FragmentShowFactorDetail();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("FragmentShowFactorDetail : onCreate()");
        EventBus.getDefault().register(this);
    }
    @Override
    public void onStart() {
        super.onStart();
        Logger.d("FragmentShowFactorDetail : onStart()");
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_factor_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        Logger.d("FragmentShowFactorDetail : onResume()");
        if (orderId != 0){
//            WebService.sendCheckOrderIdResultRequest(orderId);
            layLoading.setVisibility(View.VISIBLE);
            orderId = 0;
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.d("FragmentShowFactorDetail : onActivityCreated()");
        layShowFactor.setVisibility(View.GONE);
        factorCode = getArguments().getLong("FACTOR_CODE");
        WebService.sendGetFactorDetailRequest(factorCode);
        layBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        layPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                WebService.sendChargeOnlineForPayRequest(factorCode);
                WebService.sendCheckTaraz(factorCode);
                layLoading.setVisibility(View.VISIBLE);
            }
        });
    }
//    public void onEventMainThread(EventOnGetCheckOrderIdResultResponse event){
//        Logger.d("ActivityShowPayments : EventOnGetCheckOrderIdResultResponse is raised");
//        layLoading.setVisibility(View.GONE);
//        if(event.isResult()){
//            if(event.getFactorCode() == 0){
//               getActivity().finish();
//            }else{
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
//    public void onEventMainThread(EventOnGetChargeOnlineForPayResponse event){
//        Logger.d("ActivityShowPayments : EventOnGetPayOnlineForPayResponse is raised");
//        layLoading.setVisibility(View.GONE);
//        orderId = event.getOrderId();
//        String ussdCode = event.getOrderUssd();
//        startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + ussdCode)));
//    }
//    public void onEventMainThread(EventOnGetErrorChargeOnlineForPayRequest event){
//        Logger.d("ActivityShowPayments : EventOnGetErrorPayOnlineForPay is raised");
//        orderId = 0;
//        layLoading.setVisibility(View.GONE);
//    }
    public void onEventMainThread(EventOnGetFactorDetailsResponse event){
        Logger.d("FragmentShowFactorDetail : EventOnGetFactorDetailsResponse is raised");
        FactorDetailResponse response = event.getFactorDetailResponse().get(0);
        txtFactorDes.setText(response.Name);
        txtFactorGheymatPaye.setText(response. Price +" "+ G.currentUserInfo.unit);
        WebService.sendSelectFactorRequest(factorCode);
    }
    public void onEventMainThread(EventOnGetErrorGetFactorDetail event){
        Logger.d("FragmentShowFactorDetail : EventOnGetErrorGetFactorDetail is raised");
        layLoading.setVisibility(View.GONE);
        txtShowMessage.setText("خطا در دریافت اطلاعات از سمت سرور لطفا مجددا تلاش کنید.");
    }
    public void onEventMainThread(EventOnGetSelectFactorResponse event){
        Logger.d("FragmentShowFactorDetail : EventOnGetSelectFactorResponse is raised");
        SelectFactorResponse response = event.getSelectFactorResponse();
        txtFactorCode.setText("" + response.code);
        txtFactorStartDate.setText("" + response.StartDate);
        txtFactorGheymat.setText("" + response.Price + " "+G.currentUserInfo.unit);
        txtFactorTakhfif.setText("" + response.Discount +   " "+G.currentUserInfo.unit);
        txtFactorMaliat.setText("" + response.Tax +   " "+G.currentUserInfo.unit);
        txtFactorMablaghePardakhti.setText("" + response.Amount +   " "+G.currentUserInfo.unit);
        layLoading.setVisibility(View.GONE);
        layShowFactor.setVisibility(View.VISIBLE);
    }
    public void onEventMainThread(EventOnGetCheckTarazResponse event){
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
            ImageView imgCloseDialog = (ImageView) dialog.findViewById(R.id.imgCloseDialog);
            TextView txtLayBtnCancel = (TextView) layBtnCancel.findViewById(R.id.txtValue);
            TextView txtLayBtnOk = (TextView) layBtnOk.findViewById(R.id.txtValue);

            txtLayBtnCancel.setText("  ورود به درگاه بانک  ");
            txtLayBtnOk.setText("  پرداخت از اعتبار  ");

            layBtnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WebService.sendPayFactorFromCredit(factorCode);
                    dialog.dismiss();
                    layLoading.setVisibility(View.VISIBLE);
                }
            });

            layBtnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   EventBus.getDefault().post(new EventOnShowFragmentBankListRequest(factorCode,0));
                    layPay.setVisibility(View.GONE);

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

        }else{
            Logger.d("FragmentShowFactorDetail : taraz is not positive and it's " + taraz);
            EventBus.getDefault().post(new EventOnShowFragmentBankListRequest(factorCode,0));
        }
        layLoading.setVisibility(View.GONE);
    }
    public void onEventMainThread(EventOnGetErrorCheckTaraz event){
        Logger.d("FragmentShowFactorDetail : EventOnGetErrorCheckTaraz is raised");
        layLoading.setVisibility(View.GONE);
        DialogClass dlgShowErrorMessage= new DialogClass();
        dlgShowErrorMessage.showMessageDialog("پیغام","خطا در دریافت اطلاعات تراز مالی، لطفا مجددا تلاش کنید.");
    }
//    public void onEventMainThread(EventOnGetPayFactorFromCreditResponse event){
//        Logger.d("FragmentShowFactorDetail : EventOnGetPayFactorFromCreditResponse is raised");
//        final PayFactorFromCreditResponse response = event.getResponse();
//        layLoading.setVisibility(View.GONE);
//        layPay.setVisibility(View.GONE);
//
//        if(response.Result!= EnumResponse.OK){
//            /** dar surate khata dashtan.*/
//            DialogClass dlgShowErrorMessage = new DialogClass();
//            dlgShowErrorMessage.showMessageDialog("خطا در پرداخت",response.Message);
//        }else{
//            Intent intent = new Intent(getContext(), ActivityShowCurrentService.class);
//            startActivity(intent);
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    if(!response.started){
//                        /** dar surati ke factor shoruh nashode bashad.*/
////                dlgPayMessage = new DialogClass();
////                dlgPayMessage.showPayMessage(factorCode,"نتیجه پرداخت","آیا مایل به شروع فاکتور خود هستید؟" + "\n" + "تاریخ شروع فاکتور : " + response.startDate);
//
//                        /** show dialog halati ke mitavanad factor ra ghabl az moaed shoruh konad.**/
//                        final Dialog dialogPayMessage = new Dialog(G.currentActivity, R.style.DialogSlideAnim);
//                        dialogPayMessage.setContentView(R.layout.dialog_start_factor_message);
//                        dialogPayMessage.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                        dialogPayMessage.getWindow().setGravity(Gravity.BOTTOM);
//                        dialogPayMessage.setCancelable(false);
//                        dialogPayMessage.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//
//                        CardView layBtnStartFactor = (CardView) dialogPayMessage.findViewById(R.id.layBtnStartFactor);
//                        CardView layBtnCancel = (CardView) dialogPayMessage.findViewById(R.id.layBtnCancel);
//                        ImageView imgCloseDialog = (ImageView) dialogPayMessage.findViewById(R.id.imgCloseDialog);
//                        TextView txtMessageTitle = (TextView) dialogPayMessage.findViewById(R.id.txtMessageTitle);
//                        TextView txtMessageDescription = (TextView) dialogPayMessage.findViewById(R.id.txtMessageDescription);
//
//                        txtMessageTitle.setText("نتیجه پرداخت");
//                        txtMessageDescription.setText("فاکتور شما با موفقیت پرداخت شد و در تاریخ " + response.startDate + " شروع خواهد شد." + "\n" + "آیا مایل به شروع فاکتور خود در همین لحظه هستید؟");
//
//                        if(factorCode == 0)
//                            layBtnStartFactor.setVisibility(View.GONE);
//                        else
//                            layBtnStartFactor.setVisibility(View.VISIBLE);
//
//                        layBtnStartFactor.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                EventBus.getDefault().post(new EventOnClickedStartFactor(factorCode));
//                                dialogPayMessage.dismiss();
//                            }
//                        });
//                        layBtnCancel.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                dialogPayMessage.dismiss();
//                                FragmentManager fm = getActivity().getSupportFragmentManager();
//                                for(int i = 0; i < fm.getBackStackEntryCount() - 1; ++i) {
//                                    fm.popBackStack();
//                                }
//                            }
//                        });
//                        imgCloseDialog.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                dialogPayMessage.dismiss();
//                                FragmentManager fm = getActivity().getSupportFragmentManager();
//                                for(int i = 0; i < fm.getBackStackEntryCount() - 1; ++i) {
//                                    fm.popBackStack();
//                                }
//                            }
//                        });
//                        dialogPayMessage.show();
//
//                    }else{
//
//                        final Dialog dialog = new Dialog(G.currentActivity, R.style.DialogSlideAnim);
//                        dialog.setContentView(R.layout.dialog_show_message);
//                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                        dialog.getWindow().setGravity(Gravity.BOTTOM);
//                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//
//                        ImageView imgCloseDialog = (ImageView) dialog.findViewById(R.id.imgCloseDialog);
//                        TextView txtMessageTitle = (TextView) dialog.findViewById(R.id.txtMessageTitle);
//                        TextView txtMessageDescription = (TextView) dialog.findViewById(R.id.txtMessageDescription);
//                        txtMessageTitle.setText("" + "پیغام");
//                        txtMessageDescription.setText("" + "پرداخت با موفقیت انجام شد.");
//                        imgCloseDialog.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                dialog.dismiss();
//                            }
//                        });
//                        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                            @Override
//                            public void onCancel(DialogInterface dialogInterface) {
//                                FragmentManager fm = getActivity().getSupportFragmentManager();
//                                for(int i = 0; i < fm.getBackStackEntryCount() - 1; ++i) {
//                                    fm.popBackStack();
//                                }
//                            }
//                        });
//                        dialog.show();
//                    }
//                }
//            },600);
//
//        }
//
//    }
    @Override
    public void onPause() {
        super.onPause();
        Logger.d("FragmentShowFactorDetail : onPause()");
    }
    @Override
    public void onStop() {
        super.onStop();
        Logger.d("FragmentShowFactorDetail : onStop()");
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        Logger.d("FragmentShowFactorDetail : onDestroyView()");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d("FragmentShowFactorDetail : onDestroy()");
        EventBus.getDefault().unregister(this);
    }
}
