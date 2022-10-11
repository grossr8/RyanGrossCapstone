package com.example.ryangrosscapstone.data.ui.report1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.ryangrosscapstone.DBHandler;
import com.example.ryangrosscapstone.FishSizeClass;
import com.example.ryangrosscapstone.R;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Collections;

import com.example.ryangrosscapstone.databinding.FragmentReport1Binding;
import com.github.mikephil.charting.utils.EntryXComparator;

public class Report1Fragment extends Fragment {

    private Report1ViewModel report1ViewModel;
    private FragmentReport1Binding binding;
    private ScatterChart chart;
    private DBHandler dbHandler;
    ScatterDataSet set5;
    ArrayList<FishSizeClass> fishSizeClassArrayList = new ArrayList<>();
    ArrayList<Entry> arrayValuesBream = new ArrayList<>();
    ArrayList<Entry> arrayValuesParkki = new ArrayList<>();
    ArrayList<Entry> arrayValuesPerch = new ArrayList<>();
    ArrayList<Entry> arrayValuesPike = new ArrayList<>();
    ArrayList<Entry> arrayValuesRoach = new ArrayList<>();
    ArrayList<Entry> arrayValuesSmelt = new ArrayList<>();
    ArrayList<Entry> arrayValuesWhitefish = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        report1ViewModel =
                new ViewModelProvider(this).get(Report1ViewModel.class);

        binding = FragmentReport1Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dbHandler = new DBHandler(getContext());

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fishSizeClassArrayList = dbHandler.readFishSizeFromDatabase();

        // initializing our scatter chart.
        chart = view.findViewById(R.id.chart1);

        // below line is use to disable the description
        // of our scatter chart.
        chart.getDescription().setEnabled(false);

        // below line is use to draw grid background
        // and we are setting it to false.
        chart.setDrawGridBackground(false);

        // below line is use to set touch
        // enable for our chart.
        chart.setTouchEnabled(true);

        // below line is use to set maximum
        // highlight distance for our chart.
        chart.setMaxHighlightDistance(50f);

        // below line is use to set
        // dragging for our chart.
        chart.setDragEnabled(true);

        // below line is use to set scale
        // to our chart.
        chart.setScaleEnabled(true);

        // below line is use to set maximum
        // visible count to our chart.
        chart.setMaxVisibleValueCount(200);

        // below line is use to set
        // pinch zoom to our chart.
        chart.setPinchZoom(true);

        // below line we are getting
        // the legend of our chart.
        Legend l = chart.getLegend();

        // after getting our chart
        // we are setting our chart for vertical and horizontal
        // alignment to top, right and vertical.
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);

        // below line is use for
        // setting draw inside to false.
        l.setDrawInside(false);

        // below line is use to set
        // offset value for our legend.
        l.setXOffset(5f);

        // below line is use to get
        // y-axis of our chart.
        YAxis yl = chart.getAxisLeft();

        // below line is use to set
        // minimum axis to our y axis.
        yl.setAxisMinimum(0f);

        // below line is use to get axis
        // right of our chart
        chart.getAxisRight().setEnabled(false);

        // below line is use to get
        // x axis of our chart.
        XAxis xl = chart.getXAxis();

        // below line is use to enable
        // drawing of grid lines.
        xl.setDrawGridLines(false);

        addFishToIndividualArrays();

        Collections.sort(arrayValuesBream, new EntryXComparator());
        Collections.sort(arrayValuesParkki, new EntryXComparator());
        Collections.sort(arrayValuesPerch, new EntryXComparator());
        Collections.sort(arrayValuesPike, new EntryXComparator());
        Collections.sort(arrayValuesRoach, new EntryXComparator());
        //Collections.sort(arrayValuesBream, new EntryXComparator());
        //Collections.sort(arrayValuesBream, new EntryXComparator());


        // create a data set and give it a type
        ScatterDataSet set1 = new ScatterDataSet(arrayValuesBream, "Bream");
        ScatterDataSet set2 = new ScatterDataSet(arrayValuesParkki, "Parkki");
        ScatterDataSet set3 = new ScatterDataSet(arrayValuesPerch, "Perch");
        ScatterDataSet set4 = new ScatterDataSet(arrayValuesPike, "Pike");
        ScatterDataSet set5 = new ScatterDataSet(arrayValuesRoach, "Roach");

        // below line is use to set shape for our point on our graph.
        set1.setScatterShape(ScatterChart.ScatterShape.SQUARE);
        set2.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        set3.setScatterShape(ScatterChart.ScatterShape.X);
        set4.setScatterShape(ScatterChart.ScatterShape.TRIANGLE);
        set5.setScatterShape(ScatterChart.ScatterShape.CROSS);

        // below line is for setting color to our shape.
        set1.setColor(ColorTemplate.COLORFUL_COLORS[0]);
        set2.setColor(ColorTemplate.COLORFUL_COLORS[1]);
        set3.setColor(ColorTemplate.COLORFUL_COLORS[2]);
        set4.setColor(ColorTemplate.COLORFUL_COLORS[3]);
        set5.setColor(ColorTemplate.COLORFUL_COLORS[4]);

        // below line is for setting color to our point in chart.
        //set2.setScatterShapeHoleColor(ColorTemplate.COLORFUL_COLORS[3]);
        //set4.setScatterShapeHoleColor(ColorTemplate.COLORFUL_COLORS[3]);
        //set5.setScatterShapeHoleColor(ColorTemplate.COLORFUL_COLORS[3]);

        // below line is use to set hole
        // radius to our point in chart.
        //set2.setScatterShapeHoleRadius(3f);
        //set4.setScatterShapeHoleRadius(3f);
        //set5.setScatterShapeHoleRadius(3f);

        // for our data set of the chart.
        set1.setScatterShapeSize(20f);
        set2.setScatterShapeSize(20f);
        set3.setScatterShapeSize(40f);
        set4.setScatterShapeSize(15f);
        set5.setScatterShapeSize(30f);

        // in below line we are creating a new array list for our data set.
        ArrayList<IScatterDataSet> dataSets = new ArrayList<>();

        // in below line we are adding all
        // data sets to above array list.
        //Collections.sort(arrayValuesBream, new EntryXComparator());
        dataSets.add(set1); // add the data sets
        dataSets.add(set2);
        dataSets.add(set3);
        dataSets.add(set4);
        dataSets.add(set5);
        try {

        // create a data object with the data sets
        ScatterData data = new ScatterData(dataSets);

        // below line is use to set data to our chart
        chart.setData(data);

        // at last we are calling
        // invalidate method on our chart.
            chart.invalidate();
        }
        catch(Exception e)
        {
            String test = e.getMessage();
        }
    }

    public void addFishToIndividualArrays()
    {
        for (FishSizeClass fish : fishSizeClassArrayList) {
            Double value1 = Double.valueOf(fish.getFishWeight());
            float value2 = value1.floatValue();
            Double value3 = Double.valueOf(fish.getFishDiagonalLength());
            float value4 = value3.floatValue();

            if (fish.getFishName().equalsIgnoreCase("bream")) {
                arrayValuesBream.add(new Entry(value2, value4));
            }else if(fish.getFishName().equalsIgnoreCase("parkki")){
                arrayValuesParkki.add(new Entry(value2, value4));//fish.getFishWeight().floatValue(), fish.getFishDiagonalLength().floatValue()));
            }else if(fish.getFishName().equalsIgnoreCase("perch")) {
                arrayValuesPerch.add(new Entry(value2, value4));//fish.getFishWeight().floatValue(), fish.getFishDiagonalLength().floatValue()));
            }else if(fish.getFishName().equalsIgnoreCase("pike")){
                arrayValuesPike.add(new Entry(value2, value4));
            }else if(fish.getFishName().equalsIgnoreCase("roach")){
                arrayValuesRoach.add(new Entry(value2, value4));
            }else if(fish.getFishName().equalsIgnoreCase("smelt")){
                arrayValuesSmelt.add(new Entry(value2, value4));
            }else if(fish.getFishName().equalsIgnoreCase("whitefish")){
                arrayValuesWhitefish.add(new Entry(value2, value4));
            }
//            else{}
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}