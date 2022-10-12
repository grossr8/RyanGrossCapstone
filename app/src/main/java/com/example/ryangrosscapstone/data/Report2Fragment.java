package com.example.ryangrosscapstone.data;

import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ryangrosscapstone.DBHandler;
import com.example.ryangrosscapstone.FishSizeClass;
import com.example.ryangrosscapstone.data.ui.report1.Report1ViewModel;
import com.example.ryangrosscapstone.databinding.FragmentReport1Binding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.ryangrosscapstone.databinding.FragmentReport2Binding;
import com.example.ryangrosscapstone.R;

public class Report2Fragment extends Fragment {

    private Report2ViewModel report2ViewModel;
    private FragmentReport2Binding binding;
    private DBHandler dbHandler;
    ArrayList<FishSizeClass> fishSizeClassArrayList = new ArrayList<>();
    ArrayList<BarEntry> arrayValuesBream = new ArrayList<>();
    ArrayList<BarEntry> arrayValuesParkki = new ArrayList<>();
    ArrayList<BarEntry> arrayValuesPerch = new ArrayList<>();
    ArrayList<BarEntry> arrayValuesPike = new ArrayList<>();
    ArrayList<BarEntry> arrayValuesRoach = new ArrayList<>();
    ArrayList<BarEntry> arrayValuesSmelt = new ArrayList<>();
    ArrayList<BarEntry> arrayValuesWhitefish = new ArrayList<>();
    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;
    ArrayList barEntriesArrayList;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        report2ViewModel =
                new ViewModelProvider(this).get(Report2ViewModel.class);

        binding = FragmentReport2Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dbHandler = new DBHandler(getContext());
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        report2ViewModel = new ViewModelProvider(this).get(Report2ViewModel.class);
        barChart = view.findViewById(R.id.idBarChart);

        dbHandler = new DBHandler(getContext());
        fishSizeClassArrayList = dbHandler.readFishSizeFromDatabase();
        addFishToIndividualArrays();
        getBarEntries();

        final ArrayList<String> xAxisLabel = new ArrayList<>();
        xAxisLabel.add("Bream");
        xAxisLabel.add("Parkki");
        xAxisLabel.add("Perch");
        xAxisLabel.add("Pike");
        xAxisLabel.add("Roach");
        xAxisLabel.add("Smelt");
        xAxisLabel.add("Whitefish");

        barDataSet = new BarDataSet(barEntriesArrayList, "Total Fish By Species");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        barData = new BarData(barDataSet);

        barChart.setData(barData);
        barChart.getAxisLeft().setAxisMinimum(0);
        barChart.getDescription().setEnabled(false);
        barChart.getAxisRight().setAxisMinimum(0);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(10);

        Legend l = barChart.getLegend();
        l.setWordWrapEnabled(true);
        l.setTextSize(14);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.CIRCLE);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelRotationAngle(-45);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMaximum(barEntriesArrayList.size());
        xAxis.setTextSize(16f);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.setTypeface(Typeface.DEFAULT);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setDrawGridLines(false);
        barChart.getAxisRight().setEnabled(false);

        barChart.invalidate();

    }

    private void getBarEntries() {
        barEntriesArrayList = new ArrayList<>();

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntriesArrayList.add(new BarEntry(1f, arrayValuesBream.size()));
        barEntriesArrayList.add(new BarEntry(2f, arrayValuesParkki.size()));
        barEntriesArrayList.add(new BarEntry(3f, arrayValuesPerch.size()));
        barEntriesArrayList.add(new BarEntry(4f, arrayValuesPike.size()));
        barEntriesArrayList.add(new BarEntry(5f, arrayValuesRoach.size()));
        barEntriesArrayList.add(new BarEntry(6f, arrayValuesSmelt.size()));
        barEntriesArrayList.add(new BarEntry(7f, arrayValuesWhitefish.size()));
    }

    public void addFishToIndividualArrays()
    {
        for (FishSizeClass fish : fishSizeClassArrayList) {
            Double value1 = Double.valueOf(fish.getFishWeight());
            float value2 = value1.floatValue();
            Double value3 = Double.valueOf(fish.getFishDiagonalLength());
            float value4 = value3.floatValue();

            if (fish.getFishName().equalsIgnoreCase("bream")) {
                arrayValuesBream.add(new BarEntry(value2, value4));
            }else if(fish.getFishName().equalsIgnoreCase("parkki")){
                arrayValuesParkki.add(new BarEntry(value2, value4));
            }else if(fish.getFishName().equalsIgnoreCase("perch")) {
                arrayValuesPerch.add(new BarEntry(value2, value4));
            }else if(fish.getFishName().equalsIgnoreCase("pike")){
                arrayValuesPike.add(new BarEntry(value2, value4));
            }else if(fish.getFishName().equalsIgnoreCase("roach")){
                arrayValuesRoach.add(new BarEntry(value2, value4));
            }else if(fish.getFishName().equalsIgnoreCase("smelt")){
                arrayValuesSmelt.add(new BarEntry(value2, value4));
            }else if(fish.getFishName().equalsIgnoreCase("whitefish")){
                arrayValuesWhitefish.add(new BarEntry(value2, value4));
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}