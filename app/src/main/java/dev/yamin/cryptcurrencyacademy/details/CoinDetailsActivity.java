package dev.yamin.cryptcurrencyacademy.details;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.Calendar;

import dev.yamin.cryptcurrencyacademy.R;
import dev.yamin.cryptcurrencyacademy.base.BaseActivity;
import dev.yamin.cryptcurrencyacademy.utils.AppUtils;
import lib.yamin.easylog.EasyLog;

public class CoinDetailsActivity extends BaseActivity {

    private LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_details);

        chartSetup();
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

        setChartData();
    }

    public void setChartData() {
        ArrayList<Entry> yVals = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        long millis = calendar.getTimeInMillis();
        for (int i = 0; i < 125; i++) {
            millis += 1000 * 60 * 60 * 24;
            float val = (float) (AppUtils.randomInt(20, 30));
//            yVals.add(new Entry(i, val));

//            EasyLog.e("i: "+i+" M: "+millis);
            float sec = (float) (millis / 1000) / 60;
//            EasyLog.e("i: "+i+" S: "+sec);
            EasyLog.e(val);
            yVals.add(new Entry(sec, val));
        }

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

}
