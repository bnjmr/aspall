package ir.jahanmir.aspa;

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

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmir.aspa.adapter.AdapterFactorDetail;
import ir.jahanmir.aspa.classes.U;
import ir.jahanmir.aspa.component.PersianTextViewThin;
import ir.jahanmir.aspa.model.FactorDetail;


public class ActivityShowFactorDetail extends AppCompatActivity {


    @Bind(R.id.layBtnClose)
    FrameLayout layBtnClose;

    @Bind(R.id.lstFactorDetail)
    RecyclerView lstFactorDetail;

    @Bind(R.id.layToolbarMain)
    LinearLayout layToolbarMain;

    @Bind(R.id.imgIcon)
    ImageView imgIcon;

    @Bind(R.id.txtName)
    PersianTextViewThin txtName;


    LinearLayoutManager linearLayoutManager;
    AdapterFactorDetail adapterFactorDetail;
    FactorDetail[] factorDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_factor_detail);
        ButterKnife.bind(this);
        initToolbar();
        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setStatusBarColor(ContextCompat.getColor(G.context, R.color.dark_dark_grey));

//        factorDetails = new Select().from(FactorDetail.class).orderBy("Price desc").execute();
        String json = getIntent().getStringExtra("FACTOR_DETAIL");
        try {
            factorDetails = G.gson.fromJson(json, FactorDetail[].class);


            linearLayoutManager = new LinearLayoutManager(this);
            adapterFactorDetail = new AdapterFactorDetail(Arrays.asList(factorDetails));
            lstFactorDetail.setLayoutManager(linearLayoutManager);
            lstFactorDetail.setAdapter(adapterFactorDetail);

            layBtnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            factorDetails = new FactorDetail[0];
        }
    }

    private void initToolbar() {
        int width = U.getDeviceWidth() / 4;
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, width);
        layToolbarMain.setLayoutParams(layoutParams);
        layToolbarMain.setBackgroundColor(getResources().getColor(R.color.color_factor));
        imgIcon.setImageResource(R.drawable.ic_factor_detail_toolbar);
        txtName.setText("جزییات فاکتور");

    }

}
