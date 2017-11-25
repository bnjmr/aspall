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
import ir.jahanmir.araxx.adapter.AdapterClubScore;
import ir.jahanmir.araxx.classes.DialogClass;
import ir.jahanmir.araxx.classes.Logger;
import ir.jahanmir.araxx.classes.U;
import ir.jahanmir.araxx.classes.WebService;
import ir.jahanmir.araxx.component.PersianTextViewThin;
import ir.jahanmir.araxx.enums.EnumResponse;
import ir.jahanmir.araxx.events.EventOnErrorInConnectingToServer;
import ir.jahanmir.araxx.events.EventOnGetClubScoreResponse;
import ir.jahanmir.araxx.events.EventOnGetClubScoresResponse;
import ir.jahanmir.araxx.events.EventOnGetErrorClubScores;
import ir.jahanmir.araxx.events.EventOnGetErrorGetClubScore;
import ir.jahanmir.araxx.events.EventOnNoAccessServerResponse;
import ir.jahanmir.araxx.model.ClubScore;

public class ActivityShowClubScores extends AppCompatActivity {

    @Bind(R.id.layBtnClose)
    FrameLayout layBtnClose;
    @Bind(R.id.lstClubScore)
    RecyclerView lstClubScore;
    @Bind(R.id.txtTotalClubScore)
    TextView txtTotalClubScore;
    @Bind(R.id.layTotalClubScore)
    FrameLayout layTotalClubScore;
    @Bind(R.id.txtShowMessage)
    TextView txtShowMessage;


    @Bind(R.id.layToolbarMain)
    LinearLayout layToolbarMain;
    @Bind(R.id.imgIcon)
    ImageView imgIcon;
    @Bind(R.id.txtName)
    PersianTextViewThin txtName;

    AdapterClubScore adapterClubScore;
    List<ClubScore> scores = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    DialogClass dlgWaiting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_club_scores);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        dlgWaiting = new DialogClass(this);
        dlgWaiting.DialogWaiting();

        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setStatusBarColor(ContextCompat.getColor(G.context, R.color.dark_dark_grey));

        layTotalClubScore.setVisibility(View.GONE);

        adapterClubScore = new AdapterClubScore(scores);
        linearLayoutManager = new LinearLayoutManager(G.context);
        lstClubScore.setLayoutManager(linearLayoutManager);
        lstClubScore.setAdapter(adapterClubScore);

        WebService.sendGetClubScoreRequest();
        WebService.sendClubScoresRequest();


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
        layToolbarMain.setBackgroundColor(getResources().getColor(R.color.color_club));
        imgIcon.setImageResource(R.drawable.ic_scores);
        txtName.setText("سوابق امتیاز");

    }

    public void onEventMainThread(EventOnGetClubScoreResponse event) {
        Logger.d("ActivityShowClubScores : EventOnGetClubScoreResponse is raised.");
        dlgWaiting.DialogWaitingClose();

        int result = event.isResult();
        if (result == EnumResponse.OK) {
            layTotalClubScore.setVisibility(View.VISIBLE);
            int score = event.getScore();
            if (score == 0)
                txtTotalClubScore.setText("مجموع امتیازات : " + "0");
            else if (score > 0)
                txtTotalClubScore.setText("مجموع امتیازات : " + score + "");
            else
                txtTotalClubScore.setText("مجموع امتیازات : " + score);
        } else {
            layTotalClubScore.setVisibility(View.GONE);
        }
    }

    public void onEventMainThread(EventOnGetErrorGetClubScore event) {
        Logger.d("ActivityShowClubScores : EventOnGetErrorGetClubScore is raised.");
        dlgWaiting.DialogWaitingClose();
    }

    public void onEventMainThread(EventOnGetClubScoresResponse event) {
        Logger.d("ActivityShowClubScores : EventOnGetClubScoresResponse is raised.");
        dlgWaiting.DialogWaitingClose();
        scores = new Select().from(ClubScore.class)
//                .orderBy("Score desc")
                .execute();
        if (scores.size() == 0) {
            txtShowMessage.setVisibility(View.VISIBLE);
            txtShowMessage.setText("موردی یافت نشد.");
            scores = new ArrayList<>();
            adapterClubScore.updateList(scores);
        } else {
            scores = new Select().from(ClubScore.class)
//                    .orderBy("Score desc")
                    .execute();
            adapterClubScore.updateList(scores);
        }
    }

    public void onEventMainThread(EventOnGetErrorClubScores event) {
        Logger.d("ActivityShowClubScores : EventOnGetErrorClubScores is raised.");
        dlgWaiting.DialogWaitingClose();
        getClubScoresFormDB();
    }

    public void onEventMainThread(EventOnNoAccessServerResponse event) {
        Logger.d("ActivityShowClubScores : EventOnNoAccessServerResponse is raised.");
        dlgWaiting.DialogWaitingClose();
        getClubScoresFormDB();
    }

    public void onEventMainThread(EventOnErrorInConnectingToServer errorInConnectingToServer) {
        dlgWaiting.DialogWaitingClose();
    }

    private void getClubScoresFormDB() {
        scores = new Select().from(ClubScore.class)
                .orderBy("Score desc")
                .execute();
        adapterClubScore.updateList(scores);
    }

    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
