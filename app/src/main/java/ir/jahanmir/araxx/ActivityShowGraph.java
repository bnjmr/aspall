package ir.jahanmir.araxx;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.jahanmir.araxx.classes.Logger;
import ir.jahanmir.araxx.classes.U;
import ir.jahanmir.araxx.classes.WebService;
import ir.jahanmir.araxx.enums.EnumInternetErrorType;
import ir.jahanmir.araxx.events.EventOnGetErrorGetGraph;
import ir.jahanmir.araxx.events.EventOnGetGraphResponse;
import ir.jahanmir.araxx.model.Graph;


public class ActivityShowGraph extends AppCompatActivity {

    @Bind(R.id.chart1)
    HorizontalBarChart mChart;
    @Bind(R.id.layBtnClose)
    LinearLayout layBtnClose;
    @Bind(R.id.layBtnBack)
    LinearLayout layBtnBack;
    @Bind(R.id.txtShowMessage)
    TextView txtShowMessage;

    @Bind(R.id.persianTextViewThin)
    TextView persianTextViewThin;
    @Bind(R.id.layLoading)
    LinearLayout layLoading;
    Graph graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_graph);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        mChart.setVisibility(View.GONE);
        persianTextViewThin.setVisibility(View.GONE);

        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setStatusBarColor(ContextCompat.getColor(G.context, R.color.dark_dark_grey));

        WebService.sendGetGraphRequest();
        layBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        layBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        //mChart.setOnChartValueSelectedListener(this);
        mChart.setHighlightPerTapEnabled(false);
        mChart.setDrawBarShadow(false);
//        mChart.setDrawValueAboveBar(true);
        mChart.setDescription("");
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(100);
        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(true);
        mChart.zoom(0, 5, 0, U.getDeviceHeight(), YAxis.AxisDependency.RIGHT);
        // draw shadows for each bar that show the maximum value
//         mChart.setDrawBarShadow(true);
//        mChart.setDrawXLabels(false);
        mChart.setDrawGridBackground(false);
        mChart.setBackgroundColor(ContextCompat.getColor(G.context, R.color.trans));

        XAxis xl = mChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setAvoidFirstLastClipping(true);
//        xl.setDrawAxisLine(true);
        xl.setTextColor(ContextCompat.getColor(G.currentActivity, R.color.text_color_items));//نوشته سمت چپ نمودار
        xl.setTypeface(Typeface.MONOSPACE);
        xl.setTextSize(getResources().getDimension(R.dimen.size_font_very_small));
        xl.setDrawGridLines(false);
        xl.setGridLineWidth(0.3f);

        /** in ghesmat marbute be khate balaye nemudar ast*/
        YAxis yl = mChart.getAxisLeft();
        yl.setDrawAxisLine(false);
        yl.setDrawGridLines(false); // khat'haei ke az paein nemudar miad ke moshakhas konande meghdar masraf ast.
        yl.setGridLineWidth(0.3f);
        yl.setAxisMinValue(0f); // this replaces setStartAtZero(true)
        yl.setTextColor(ContextCompat.getColor(G.currentActivity, R.color.text_color_items));
        yl.setTypeface(Typeface.MONOSPACE);
        yl.setTextSize(getResources().getDimension(R.dimen.size_font_small));
//        yl.setInverted(true);

        // مقادیر پایین نمودار
        YAxis yr = mChart.getAxisRight();
        yr.setDrawAxisLine(false);
        yr.setDrawGridLines(false);
        yr.setAxisMinValue(0f); // this replaces setStartAtZero(true)
        yr.setTextColor(ContextCompat.getColor(G.currentActivity, R.color.text_color_items));
        yr.setTypeface(Typeface.MONOSPACE);
        yr.setTextSize(getResources().getDimension(R.dimen.size_font_very_very_small));
//        yr.setInverted(true);
        mChart.animateY(2500);

    }

    public void onEventMainThread(EventOnGetGraphResponse event) {
        Logger.d("ActivityShowGraph : EventOnGetGraphResponse is raised");
        layLoading.setVisibility(View.GONE);
        graph = event.getGraph();
        if (graph.userId == 0) {
            /** yani graaphi baraaye eon sabt nashode ast*/
            mChart.setVisibility(View.GONE);
            persianTextViewThin.setVisibility(View.GONE);
            txtShowMessage.setVisibility(View.VISIBLE);
            txtShowMessage.setText("داده ای برای نمایش وجود ندارد");
        } else {
            txtShowMessage.setVisibility(View.GONE);
            initializeGraph();
        }
    }

    private void initializeGraph() {
        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        ArrayList<String> xVals = new ArrayList<>();
        String[] mLabels = graph.xData.split(",");
        String[] mValues = graph.yData.split(",");
        if (mLabels.length == 1) {
            /** yani hich graphi sabt nashode ast*/
            mChart.setVisibility(View.GONE);
            persianTextViewThin.setVisibility(View.GONE);
            txtShowMessage.setVisibility(View.VISIBLE);
            txtShowMessage.setText("داده ای برای نمایش وجود ندارد");
        } else {
            int i = 0;
            for (String value : mValues) {
                xVals.add("" + mLabels[i]);
                yVals1.add(new BarEntry(Float.parseFloat(value), i));
                i++;
            }
            BarDataSet set1 = new BarDataSet(yVals1, "حجم مصرف شده به مگابایت");
            set1.setColor(ContextCompat.getColor(G.currentActivity, R.color.orange));
            set1.setValueTextColor(ContextCompat.getColor(G.currentActivity, R.color.white));
            mChart.getLegend().setTextColor(ContextCompat.getColor(G.currentActivity, R.color.white));
            mChart.getLegend().setTypeface(Typeface.SANS_SERIF);
            set1.setValueTypeface(Typeface.MONOSPACE);
            set1.setValueTextSize(getResources().getDimension(R.dimen.size_font_small));

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(xVals, dataSets);
            data.setValueTextSize(getResources().getDimension(R.dimen.size_font_very_very_small)); // size of Des of useage traffic
            data.setValueTextColor(ContextCompat.getColor(G.currentActivity, R.color.text_color_items));
            mChart.setData(data);
            mChart.setVisibility(View.VISIBLE);
            persianTextViewThin.setVisibility(View.VISIBLE);

        }
    }

    public void onEventMainThread(EventOnGetErrorGetGraph event) {
        Logger.d("ActivityShowGraph : EventOnGetErrorGetGraph is raised");
        layLoading.setVisibility(View.GONE);
        if (event.getErrorType() == EnumInternetErrorType.NO_INTERNET_ACCESS) {
            graph = new Select().from(Graph.class).executeSingle();
            if (graph != null) {
                initializeGraph();
            } else {
                mChart.setVisibility(View.GONE);
                persianTextViewThin.setVisibility(View.GONE);

            }
        } else {
            txtShowMessage.setVisibility(View.VISIBLE);
            txtShowMessage.setText("خطا در دریافت اطلاعات از سرور لطفا دوباره تلاش کنید.");
        }
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
