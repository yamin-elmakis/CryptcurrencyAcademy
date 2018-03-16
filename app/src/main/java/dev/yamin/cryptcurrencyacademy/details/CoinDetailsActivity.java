package dev.yamin.cryptcurrencyacademy.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
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
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import dev.yamin.cryptcurrencyacademy.R;
import dev.yamin.cryptcurrencyacademy.base.BaseActivity;
import dev.yamin.cryptcurrencyacademy.network.GsonJsonParser;
import dev.yamin.cryptcurrencyacademy.network.POJOS.KLines;
import dev.yamin.cryptcurrencyacademy.network.RequestBuilder;
import dev.yamin.cryptcurrencyacademy.utils.AppUtils;
import lib.yamin.easylog.EasyLog;

public class CoinDetailsActivity extends BaseActivity implements
        Response.Listener<ArrayList<KLines>>, Response.ErrorListener, GsonJsonParser<ArrayList<KLines>, Object> {

    public static final String ARG_COIN = "arg_coin_symbol";
    public static final String  INTERVAL_H = "1h";
    public static final String  INTERVAL_D = "1d";
    public static final String  INTERVAL_W = "1w";
    public static final String  INTERVAL_M = "1M";
    private LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_details);

        chartSetup();

        Intent intent = getIntent();
        String coinSymbol = intent.getStringExtra(ARG_COIN);
        if (TextUtils.isEmpty(coinSymbol)) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            finish();
            EasyLog.e("finish");
            return;
        }

        EasyLog.e(coinSymbol);
        RequestBuilder.getInstance(this).GenerateKLinesRequest(coinSymbol, INTERVAL_H, "150", this, this, this);
    }

    private void chartSetup() {
        lineChart = findViewById(R.id.details_line_chart);
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
        xAxis.setLabelCount(7, false);
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

    @Override
    public void onResume() {
        super.onResume();

//        ArrayList<Entry> yVals= new ArrayList<>();
//        Calendar calendar = Calendar.getInstance();
//        long millis = calendar.getTimeInMillis();
//        for (int i = 0; i < 125; i++) {
//            millis += 1000 * 60 * 60 * 24;
//            float val = (float) (AppUtils.randomInt(20, 30));
//            float sec = (float) (millis / 1000) / 60;
//            yVals.add(new Entry(sec, val));
//        }
//        setChartData(yVals);
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
            set1 = new LineDataSet(yVals, "DataSet 1");

            set1.setMode(LineDataSet.Mode.LINEAR);
            set1.setCubicIntensity(0.2f);
            //set1.setDrawFilled(true);
            set1.setDrawCircles(false);
            set1.setLineWidth(1.8f);
            set1.setHighLightColor(ContextCompat.getColor(this, R.color.colorAccent));
            set1.setHighlightLineWidth(3);
            set1.setHighlightEnabled(true);
            set1.setColor(ContextCompat.getColor(this, R.color.colorPrimary));
            set1.setFillColor(ContextCompat.getColor(this, R.color.colorPrimary));
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
    }

    @Override
    public ArrayList<KLines> parseJsonToObj(String data) {
        ArrayList<KLines> _items = new ArrayList<KLines>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for(int i = 0; i < jsonArray.length();i++){
                ArrayList<String> item = AppUtils.getObjectFromStr(jsonArray.getJSONArray(i).toString(), new TypeToken<ArrayList<String>>() {
                }.getType());

                KLines kLines = new KLines();
                kLines.setArrayList(item);
                _items.add(kLines);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return _items;
    }

    @Override
    public String parseObjToJson(Object obj) {
        return null;
    }
}
