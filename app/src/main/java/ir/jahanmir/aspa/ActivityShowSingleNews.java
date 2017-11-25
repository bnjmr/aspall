package ir.jahanmir.aspa;

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
import ir.jahanmir.aspa.adapter.AdapterSingleNews;
import ir.jahanmir.aspa.classes.U;
import ir.jahanmir.aspa.component.PersianTextViewThin;
import ir.jahanmir.aspa.model.News;


public class ActivityShowSingleNews extends AppCompatActivity {

    @Bind(R.id.lstNews)
    RecyclerView lstNews;
    @Bind(R.id.txtShowMessage)
    TextView txtShowMessage;
    @Bind(R.id.layBtnClose)
    FrameLayout layBtnClose;

    @Bind(R.id.layToolbarMain)
    LinearLayout layToolbarMain;


    @Bind(R.id.imgIcon)
    ImageView imgIcon;

    @Bind(R.id.txtName)
    PersianTextViewThin txtName;


    AdapterSingleNews adapterNews;
    LinearLayoutManager linearLayoutManager;
    List<News> newses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);

        G.currentActivity = this;
        G.context = this;
        ButterKnife.bind(G.currentActivity);

        Intent i = getIntent();

        News news = new News();
        news.title = i.getStringExtra("title");
        news.bodyText = i.getStringExtra("body");
        news.newsDate = i.getStringExtra("Date");

        newses.add(news);

        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setStatusBarColor(ContextCompat.getColor(G.context, R.color.dark_dark_grey));

        adapterNews = new AdapterSingleNews(newses);
        linearLayoutManager = new LinearLayoutManager(G.currentActivity);
        lstNews.setLayoutManager(linearLayoutManager);
        lstNews.setAdapter(adapterNews);

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
        layToolbarMain.setBackgroundColor(getResources().getColor(R.color.color_news));
        imgIcon.setImageResource(R.drawable.ic_news);
        txtName.setText("اخبار");

    }

}
