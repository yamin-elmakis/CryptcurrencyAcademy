package dev.yamin.cryptcurrencyacademy.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;

import dev.yamin.cryptcurrencyacademy.R;
import dev.yamin.cryptcurrencyacademy.base.BaseActivity;
import dev.yamin.cryptcurrencyacademy.network.NetworkManager;
import dev.yamin.cryptcurrencyacademy.network.POJOS.KLines;
import dev.yamin.cryptcurrencyacademy.utils.AppUtils;
import dev.yamin.cryptcurrencyacademy.utils.DataUtils;
import lib.yamin.easylog.EasyLog;

public class CoinDetailsActivity extends BaseActivity implements
        Response.Listener<ArrayList<KLines>>, Response.ErrorListener {

    public static final String ARG_COIN_PAIR = "arg_coin_pair";
    public static final String ARG_COIN_PRICE = "arg_coin_price";
    public static final String ARG_COIN_PERCENT = "arg_coin_percent";

    private LineChart lineChart;
    private TextView tvTitle, tvPrice, tvPercent;
    private ImageView ivTitle;
    private ProgressBar progressBar;
    private RadioGroup rgInterval;

    @DataUtils.KLineInterval
    private String interval;
    private String coinName, price, percent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String coinPair = intent.getStringExtra(ARG_COIN_PAIR);
        if (TextUtils.isEmpty(coinPair)) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            finish();
            EasyLog.e("finish");
            return;
        }

        price= intent.getStringExtra(ARG_COIN_PRICE);
        percent= intent.getStringExtra(ARG_COIN_PERCENT);

        setContentView(R.layout.activity_coin_details);
        tvTitle = findViewById(R.id.details_tv_title);
        ivTitle = findViewById(R.id.details_iv_title);
        tvPrice = findViewById(R.id.details_tv_price);
        tvPercent = findViewById(R.id.details_tv_change);
        progressBar = findViewById(R.id.details_pb);
        lineChart = findViewById(R.id.details_line_chart);
        rgInterval = findViewById(R.id.details_rg);

        coinName = DataUtils.PairToName(coinPair);
        chartSetup();
        updateCoinData();

        EasyLog.e(coinPair);
        interval = DataUtils.KLINE_INTERVAL_H;
        setLoading(true);
        NetworkManager.getInstance().sendKLinesRequest(coinPair, interval, this, this);
    }

    private void updateCoinData() {
        String symbol = DataUtils.nameToSymbol(coinName);
        String title = coinName + " ("+symbol+")";
        tvTitle.setText(title);
        ivTitle.setImageResource(DataUtils.imageBySymbol(symbol));
        tvPrice.setText(price);
        tvPercent.setText(percent);
    }

    private void chartSetup() {
//        lineChart.setBackgroundResource(android.R.color.white);
        lineChart.getDescription().setEnabled(false);
        lineChart.setPinchZoom(true);
        lineChart.setHorizontalScrollBarEnabled(false);
        lineChart.setVerticalScrollBarEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setDrawBorders(false);
        lineChart.setTouchEnabled(true);

        lineChart.getAxisRight().setEnabled(false);
        lineChart.getAxisLeft().setDrawAxisLine(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setDrawAxisLine(false);
        lineChart.getXAxis().setDrawGridLines(false);


        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(6, false);
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            public String getFormattedValue(float value, AxisBase axis) {
//                EasyLog.e("VisibleXRange: "+lineChart.getVisibleXRange());
                String valueStr = AppUtils.getFullDateTime((long) value);
//                EasyLog.e(valueStr);
                return valueStr;
//                return String.valueOf(value).substring(1, 2);
            }
        });

        lineChart.invalidate();
    }

    private void setLoading(boolean loading) {
        lineChart.setVisibility(loading ? View.GONE : View.VISIBLE);
        progressBar.setVisibility((!loading) ? View.GONE : View.VISIBLE);
        rgInterval.setEnabled(!loading);
    }

    public void setChartData(ArrayList<Entry> yVals) {
        LineDataSet set1;

        if (lineChart.getData() != null &&
                lineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals);
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(yVals, coinName);

            set1.setMode(LineDataSet.Mode.LINEAR);
            set1.setCubicIntensity(0.2f);
            //set1.setDrawFilled(true);
            set1.setDrawCircles(false);
            set1.setLineWidth(0.5f);
            set1.setHighLightColor(ContextCompat.getColor(this, android.R.color.black));
            set1.setHighlightLineWidth(1);
            set1.setHighlightEnabled(true);
            set1.setColor(ContextCompat.getColor(this, R.color.colorGraph));
            set1.setFillColor(ContextCompat.getColor(this, R.color.colorGraph));
            set1.setFillAlpha(200);
//            set1.setFillFormatter(new IFillFormatter() {
//                @Override
//                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
//                    return -10;
//                }
//            });
            set1.setDrawFilled(true);

            // create a data object with the datasets
            LineData data = new LineData(set1);
            data.setValueTextSize(9f);
            data.setDrawValues(false);

            // set data
            lineChart.setData(data);
        }

        // dont forget to refresh the drawing
        lineChart.invalidate();
    }

    @Override
    public void onResponse(ArrayList<KLines> response) {
        EasyLog.e(response);
        setLoading(false);
        ArrayList<Entry> yVals = new ArrayList<>();
        for (KLines kLines : response) {
            float sec = (float) (kLines.getOpenTime()/ 1000) / 60;
            yVals.add(new Entry(sec, kLines.getOpenPrice()));
        }
        setChartData(yVals);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        EasyLog.e(error);
        setLoading(false);
        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
    }
}
