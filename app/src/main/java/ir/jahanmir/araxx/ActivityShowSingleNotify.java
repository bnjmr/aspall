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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmir.araxx.adapter.AdapterSingleNotify;
import ir.jahanmir.araxx.classes.U;
import ir.jahanmir.araxx.component.PersianTextViewThin;
import ir.jahanmir.araxx.model.Notify;


public class ActivityShowSingleNotify extends AppCompatActivity {
    @Bind(R.id.layBtnClose)
    FrameLayout layBtnBack;
    @Bind(R.id.lstNotifies)
    RecyclerView lstNotifies;
    @Bind(R.id.txtShowMessage)
    TextView txtShowMessage;

    @Bind(R.id.layToolbarMain)
    LinearLayout layToolbarMain;


    @Bind(R.id.imgIcon)
    ImageView imgIcon;

    @Bind(R.id.txtName)
    PersianTextViewThin txtName;

    AdapterSingleNotify adapterNofify;
    LinearLayoutManager linearLayoutManager;
    List<Notify> notifies= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notifes);



        ButterKnife.bind(this);

        Intent i = getIntent();

        Notify notify = new Notify();
        notify.Title = i.getStringExtra("title");
        notify.message = i.getStringExtra("body");
        notify.setDate(i.getStringExtra("date"));

        notifies.add(notify);

        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setStatusBarColor(ContextCompat.getColor(G.context, R.color.dark_dark_grey));

        adapterNofify = new AdapterSingleNotify(notifies);
        linearLayoutManager = new LinearLayoutManager(this);
        lstNotifies.setLayoutManager(linearLayoutManager);
        lstNotifies.setAdapter(adapterNofify);

        layBtnBack.setOnClickListener(new View.OnClickListener() {
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
        layToolbarMain.setBackgroundColor(getResources().getColor(R.color.color_notify));
        imgIcon.setImageResource(R.drawable.ic_flag);
        txtName.setText("پیغام ها");

    }


    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
        G.context = this;
    }
}
