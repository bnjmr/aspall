package ir.jahanmir.aspa;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.jahanmir.aspa.adapter.AdapterTrafficSplit;
import ir.jahanmir.aspa.classes.DialogClass;
import ir.jahanmir.aspa.classes.U;
import ir.jahanmir.aspa.classes.WebService;
import ir.jahanmir.aspa.component.PersianTextViewThin;
import ir.jahanmir.aspa.enums.EventOnSuccessGoToMainTraffic;
import ir.jahanmir.aspa.events.EventOnClickedYesOnYesNoDialog;
import ir.jahanmir.aspa.events.EventOnErrorInConnectingToServer;
import ir.jahanmir.aspa.events.EventOnFailStartTrafficSplit;
import ir.jahanmir.aspa.events.EventOnGetCurrentTrafficSplite;
import ir.jahanmir.aspa.events.EventOnLoadTrafficSplitNotMain;
import ir.jahanmir.aspa.events.EventOnSuccessEndTrafficSplit;
import ir.jahanmir.aspa.events.EventOnSuccessStartTrafficSplit;
import ir.jahanmir.aspa.model.ModelYesNoDialog;

import static ir.jahanmir.aspa.enums.EnumYesNoKind.EndCurrentTrafficSplit;
import static ir.jahanmir.aspa.enums.EnumYesNoKind.GoToMainTraffic;


public class ActivityShowTrafficeSplite extends AppCompatActivity {

    @Bind(R.id.txtCurrentTrafficSpliteExpireDate)
    PersianTextViewThin txtCurrentTrafficSpliteExpireDate;

    @Bind(R.id.txtCurrentTrafficSplitTraffic)
    PersianTextViewThin txtCurrentTrafficSplitTraffic;

    @Bind(R.id.imgEndCurrentTrafficSplitRequest)
    LinearLayout imgEndCurrentTrafficSplitRequest;

    @Bind(R.id.imgGoToMainTraffic)
    LinearLayout imgGoToMainTraffic;

    @Bind(R.id.layCurrentTrafficSplit)
    LinearLayout layCurrentTrafficSplit;

    @Bind(R.id.lstTrafficSplit)
    RecyclerView lstTrafficSplit;

    @Bind(R.id.layBtnClose)
    FrameLayout layBtnClose;


    @Bind(R.id.txtShowMessage)
    TextView txtShowMessage;



    @Bind(R.id.layToolbarMain)
    LinearLayout layToolbarMain;


    @Bind(R.id.imgIcon)
    ImageView imgIcon;

    @Bind(R.id.txtName)
    PersianTextViewThin txtName;

    AdapterTrafficSplit adapterTrafficSplit;
    DialogClass dialogClass;
    DialogClass dlgWaiting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_traffice_splite);
        ButterKnife.bind(this);
        layBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dlgWaiting = new DialogClass(this);
        dlgWaiting.DialogWaiting();


        dialogClass = new DialogClass();

        initToolbar();
    }

    private void initToolbar() {
        int width = U.getDeviceWidth() / 4;
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, width);
        layToolbarMain.setLayoutParams(layoutParams);
        layToolbarMain.setBackgroundColor(getResources().getColor(R.color.color_traffic_split));
        imgIcon.setImageResource(R.drawable.ic_traffic_split);
        txtName.setText("تقسیم ترافیک");

    }


    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
        EventBus.getDefault().register(this);
        WebService.sendGetCurrentTrraficSplite();
        WebService.sendLoadTrafficSplitsNotMain();


    }

    @Override
    protected void onPause() {
        super.onPause();
//        G.currentActivity = this;
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(EventOnGetCurrentTrafficSplite splite) {
        dlgWaiting.DialogWaitingClose();
        if (splite.getCurrentTrraficSplite().isExist()) {
            layCurrentTrafficSplit.setVisibility(View.VISIBLE);
            txtCurrentTrafficSpliteExpireDate.setText(splite.getCurrentTrraficSplite().getEndDateTrafficSplit());
            txtCurrentTrafficSplitTraffic.setText(" باقیمانده جاری : " + splite.getCurrentTrraficSplite().getCurrentRemain() + "\n"
                    + "باقیمانده کل : " + (int) splite.getCurrentTrraficSplite().getSumRemainTraffic());
            if (splite.getCurrentTrraficSplite().isEndPackage()) {
                imgEndCurrentTrafficSplitRequest.setEnabled(true);
                imgEndCurrentTrafficSplitRequest.setVisibility(View.VISIBLE);
                imgEndCurrentTrafficSplitRequest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ModelYesNoDialog modelYesNoDialog = new ModelYesNoDialog("هشدار", "آیا مطمن هستید میخواهید تقسیم ترافیک خود را متوقف کنید؟", "", EndCurrentTrafficSplit);
                        dialogClass.showYesNoDialog(modelYesNoDialog);
                    }
                });
            } else {
                imgEndCurrentTrafficSplitRequest.setEnabled(false);
                imgEndCurrentTrafficSplitRequest.setVisibility(View.INVISIBLE);
            }

            if (splite.getCurrentTrraficSplite().isGoToMainTraffic()) {
                imgGoToMainTraffic.setVisibility(View.VISIBLE);
                imgGoToMainTraffic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ModelYesNoDialog modelYesNoDialog = new ModelYesNoDialog("هشدار", "آیا مطمئن هستید میخواهید به سرویس اصلی بروید؟", "", GoToMainTraffic);
                        dialogClass.showYesNoDialog(modelYesNoDialog);
                    }
                });
            } else {
                imgGoToMainTraffic.setVisibility(View.INVISIBLE);
            }
        } else {
            layCurrentTrafficSplit.setVisibility(View.GONE);
        }
    }


    public void onEventMainThread(EventOnLoadTrafficSplitNotMain splite) {

        lstTrafficSplit.setHasFixedSize(true);
        if (splite.getTrafficSplitNotMainModel().length == 0) {
            txtShowMessage.setVisibility(View.VISIBLE);
            txtShowMessage.setText("برای شما تقسیم ترافیکی ثبت نشده");
        } else {
            txtShowMessage.setVisibility(View.GONE);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            adapterTrafficSplit = new AdapterTrafficSplit(Arrays.asList(splite.getTrafficSplitNotMainModel()), this);
            lstTrafficSplit.setLayoutManager(layoutManager);
            lstTrafficSplit.setAdapter(adapterTrafficSplit);
        }

    }

    public void onEventMainThread(EventOnErrorInConnectingToServer errorInConnectingToServer) {
        dlgWaiting.DialogWaitingClose();
        DialogClass.showMessageDialog("خطا", "لطفا اینترنت خود را چک کنید و دوباره امتحان کنید");
    }

    public void onEventMainThread(EventOnClickedYesOnYesNoDialog yesNoDialog) {
        switch (yesNoDialog.getYesNoKind()) {
            case EndCurrentTrafficSplit:
                WebService.sendEndTrraficSplite();
                dlgWaiting.DialogWaiting();
                break;
            case GoToMainTraffic:
                WebService.sendGoToMainTrrafic();
                dlgWaiting.DialogWaiting();
                break;
            case StartTrafficSplit:
                WebService.sendStartTrafficSplit(Integer.parseInt(yesNoDialog.getDate()));
                dlgWaiting.DialogWaiting();
                break;
        }

    }

    public void onEventMainThread(EventOnSuccessStartTrafficSplit split) {
        dlgWaiting.DialogWaitingClose();
        DialogClass.showMessageDialog("", "عمیلات با موفقیت انجام شد");
        WebService.sendGetCurrentTrraficSplite();
        WebService.sendLoadTrafficSplitsNotMain();


    }

    public void onEventMainThread(EventOnFailStartTrafficSplit split) {
        dlgWaiting.DialogWaitingClose();
        DialogClass.showMessageDialog("خطا", split.getMessage());
    }

    public void onEventMainThread(EventOnSuccessGoToMainTraffic split) {
        dlgWaiting.DialogWaitingClose();
        DialogClass.showMessageDialog("", "عمیلات با موفقیت انجام شد");
        WebService.sendGetCurrentTrraficSplite();
        WebService.sendLoadTrafficSplitsNotMain();


    }

    public void onEventMainThread(EventOnSuccessEndTrafficSplit split) {
        dlgWaiting.DialogWaitingClose();
        DialogClass.showMessageDialog("", "عمیلات با موفقیت انجام شد");
        WebService.sendGetCurrentTrraficSplite();
        WebService.sendLoadTrafficSplitsNotMain();


    }

}
