package ir.jahanmir.araxx;

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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.jahanmir.araxx.adapter.AdapterNews;
import ir.jahanmir.araxx.classes.DialogClass;
import ir.jahanmir.araxx.classes.Logger;
import ir.jahanmir.araxx.classes.U;
import ir.jahanmir.araxx.classes.WebService;
import ir.jahanmir.araxx.component.PersianTextViewThin;
import ir.jahanmir.araxx.enums.EnumInternetErrorType;
import ir.jahanmir.araxx.events.EventOnErrorInConnectingToServer;
import ir.jahanmir.araxx.events.EventOnGetErrorGetNews;
import ir.jahanmir.araxx.events.EventOnGetNewsResponse;
import ir.jahanmir.araxx.model.News;


public class ActivityShowNews extends AppCompatActivity {

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

    AdapterNews adapterNews;
    LinearLayoutManager linearLayoutManager;
    DialogClass dlgWaiting;
    List<News> newses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        dlgWaiting = new DialogClass(this);
        dlgWaiting.DialogWaiting();
        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setStatusBarColor(ContextCompat.getColor(G.context, R.color.dark_dark_grey));

        adapterNews = new AdapterNews(newses);
        linearLayoutManager = new LinearLayoutManager(this);
        lstNews.setLayoutManager(linearLayoutManager);
        lstNews.setAdapter(adapterNews);

        sendRequestGetNewNews();

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


    private void sendRequestGetNewNews() {
//        News lastNews = new Select()
//                .from(News.class)
//                .orderBy("code desc")
//                .limit(1)
//                .executeSingle();
//        if (lastNews == null) {
        WebService.sendGetNewsRequest(0);
//        } else {
//            WebService.sendGetNewsRequest(lastNews.newsID);
//        }
    }

    public void onEventMainThread(EventOnGetNewsResponse event) {
        dlgWaiting.DialogWaitingClose();
        Logger.d("ActivityShowNews : EventOnGetNewsResponse is raised.");
        getNewsFromDB();
    }

    public void onEventMainThread(EventOnGetErrorGetNews event) {
        dlgWaiting.DialogWaitingClose();
        Logger.d("ActivityShowNews : EventOnGetErrorGetNews is raised.");
        getNewsFromDB();

        if (event.getErrorType() == EnumInternetErrorType.NO_INTERNET_ACCESS) {
            U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
        }

    }

    public void onEventMainThread(EventOnErrorInConnectingToServer errorInConnectingToServer) {
        dlgWaiting.DialogWaitingClose();
    }

    private void getNewsFromDB() {
        newses = new Select()
                .from(News.class)
//                .orderBy("NewsDate desc")
                .execute();
        adapterNews.updateList(newses);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
