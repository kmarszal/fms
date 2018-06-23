package com.example.mapwithmarker;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class CustomChart {
    CustomChart(PieChart statChart){
        List<PieEntry> entries = new ArrayList<>(2);
        entries.add(new PieEntry(75.0f, ""));
        entries.add(new PieEntry(25.0f, ""));
        PieDataSet dataSet = new PieDataSet(entries,"");
        dataSet.setColors(ColorTemplate.rgb("55FFFF"), ColorTemplate.COLOR_NONE);
        dataSet.setDrawValues(false);
        statChart.setData(new PieData(dataSet));
        Description description = new Description();
        description.setText("");
        statChart.getLegend().setEnabled(false);
        statChart.setDescription(description);
        statChart.setHoleRadius(75f);
        statChart.setCenterText("75%");
        statChart.setCenterTextSize(24f);
        statChart.setTouchEnabled(false);
        statChart.setHoleColor(ColorTemplate.COLOR_NONE);
        statChart.invalidate();
    }
}
