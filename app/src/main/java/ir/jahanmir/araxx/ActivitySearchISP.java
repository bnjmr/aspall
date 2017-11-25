package ir.jahanmir.araxx;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.jahanmir.araxx.adapter.AdapterISP;
import ir.jahanmir.araxx.classes.Logger;
import ir.jahanmir.araxx.events.EventOnClickedIspItem;
import ir.jahanmir.araxx.events.EventOnGetErrorIspList;
import ir.jahanmir.araxx.events.EventOnGetIspListResponse;
import ir.jahanmir.araxx.events.EventOnNoAccessServerResponse;
import ir.jahanmir.araxx.gson.SearchISPResponse;


/**
 * Created by Microsoft on 3/2/2016.
 */
public class ActivitySearchISP extends AppCompatActivity {

    @Bind(R.id.lstISP) RecyclerView lstISP;
    @Bind(R.id.edtSearchISP)
    EditText edtSearchISP;
    @Bind(R.id.layBtnSearch)
    LinearLayout layBtnSearch;
    @Bind(R.id.progressWaiting) ProgressWheel progressWaiting;
    @Bind(R.id.txtShowMessage)
    TextView txtShowMessage;
    @Bind(R.id.txtShowErrorMessage)
    TextView txtShowErrorMessage;

    LinearLayoutManager linearLayoutManager;
    AdapterISP adapterISP;
    List<SearchISPResponse> isps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_isp);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);



        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setStatusBarColor(ContextCompat.getColor(G.context, R.color.dark_dark_grey));

        linearLayoutManager = new LinearLayoutManager(G.context);
        lstISP.setLayoutManager(linearLayoutManager);
        adapterISP = new AdapterISP(isps);
        lstISP.setAdapter(adapterISP);


        layBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  /*hide soft keyboard*/
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                searchIspRequest();
            }
        });

        edtSearchISP.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    searchIspRequest();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edtSearchISP.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

    }
    private void searchIspRequest() {
        txtShowMessage.setVisibility(View.GONE);
        String searchDes = edtSearchISP.getText().toString().trim();
        if (searchDes.length() == 0) {
            txtShowErrorMessage.setText("لطفا یک متن جستجوی مناسب وارد کنید.");
            return;
        } else {
            progressWaiting.setVisibility(View.VISIBLE);
//            WebService.sendGetIspListRequest(searchDes);
        }
    }
    /*hide keyboard when touch screen*/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
    public void onEventMainThread(EventOnGetIspListResponse event){
        Logger.d("ActivitySearchISP : EventOnGetIspList is raised");
        progressWaiting.setVisibility(View.GONE);
        txtShowErrorMessage.setText("");
        isps = event.getIsps();
        adapterISP = new AdapterISP(isps);
        lstISP.setAdapter(adapterISP);
        if(isps.size() == 0 ){
            txtShowMessage.setText("موردی یافت نشد.");
            txtShowMessage.setVisibility(View.VISIBLE);
        }else{
            txtShowMessage.setVisibility(View.GONE);
        }
    }
    public void onEventMainThread(EventOnGetErrorIspList event){
        Logger.d("ActivitySearchISP : EventOnGetIspListError is raised");
        txtShowErrorMessage.setText("");
        progressWaiting.setVisibility(View.GONE);
        txtShowMessage.setText("خطا در دریافت اطلاعات از سرور");
        txtShowMessage.setVisibility(View.VISIBLE);
    }
    public void onEventMainThread(EventOnNoAccessServerResponse event){
        Logger.d("ActivitySearchISP : EventOnNoAccessServerResponse is raised");
        txtShowErrorMessage.setText("");
        progressWaiting.setVisibility(View.GONE);
        txtShowMessage.setText("خطا در دریافت اطلاعات از سرور");
        txtShowMessage.setVisibility(View.VISIBLE);
    }
    public void onEventMainThread(EventOnClickedIspItem event){
        Logger.d("ActivitySearchISP : EventOnClickedIspItem is raised");
        Intent intent = new Intent(G.context,ActivityLogin.class);
        intent.putExtra("ISP_URL",event.getIspUrl());
        intent.putExtra("ISP_ID",event.getIspId());
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
