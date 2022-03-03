package com.example.docexpress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.model.GradientColor;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.checkerframework.common.value.qual.IntVal;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProgressScreen extends AppCompatActivity {
    TextView textView;
    PieChart pieChart;
    PieData pieData;
    List<PieEntry> pieEntryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_screen);
        textView=findViewById(R.id.all_applications_count);
        pieChart = findViewById(R.id.pieChart);
        pieChart.setUsePercentValues(false);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setDescription(null);
        pieChart.setEntryLabelTextSize(16);
        Legend l = pieChart.getLegend();
        l.setTextSize(15f);
        l.setForm(Legend.LegendForm.CIRCLE);
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String s=e.getData().toString();
                if(s.equalsIgnoreCase("1"))
                {
                    Toast.makeText(getApplicationContext(),"Completed",Toast.LENGTH_SHORT).show();
                    //Intent intent=new Intent(ProgressScreen.this,)
                }
                else if (s.equalsIgnoreCase("2")) {
                    Toast.makeText(getApplicationContext(),"On Going",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(ProgressScreen.this,ongoing_Application_Screen.class);
                    startActivity(intent);
                }
                else if (s.equalsIgnoreCase("3"))
                {
                    Toast.makeText(getApplicationContext(),"Returned",Toast.LENGTH_SHORT).show();
                }
                else if(s.equalsIgnoreCase("4"))
                {
                    Toast.makeText(getApplicationContext(),"Rejected",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
        String total="0";
        String completed="0";
        String ongoing="0";
        String returned="0";
        String rejected="0";
        MyDbHelper helper=new MyDbHelper(getApplicationContext());
        Cursor data=helper.getdocSummary();
        while (data.moveToNext())
        {
            total=data.getString(1);
            completed=data.getString(2);
            ongoing=data.getString(3);
            returned=data.getString(4);
            rejected=data.getString(5);
        }
        textView.setText(total);
        pieEntryList.add(new PieEntry(Integer.parseInt(completed), "Completed",1));
        pieEntryList.add(new PieEntry(Integer.parseInt(ongoing), "On Going",2));
        pieEntryList.add(new PieEntry(Integer.parseInt(returned), "Returned",3));
        pieEntryList.add(new PieEntry(Integer.parseInt(rejected), "Rejected",4));
        PieDataSet pieDataSet = new PieDataSet(pieEntryList, null);
        pieDataSet.setValueTextSize(15);
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        //String format="###,###,###";
        //pieDataSet.setValueFormatter(new LargeValueFormatter());
        pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(getApplicationContext(),Home.class);
        startActivity(intent);
        finish();
    }
}