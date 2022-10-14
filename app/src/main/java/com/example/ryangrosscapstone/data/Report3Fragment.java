package com.example.ryangrosscapstone.data;

import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ryangrosscapstone.BoxSize;
import com.example.ryangrosscapstone.DBHandler;
import com.example.ryangrosscapstone.FishSizeClass;
import com.example.ryangrosscapstone.R;
import com.example.ryangrosscapstone.databinding.FragmentReport3Binding;
import com.github.mikephil.charting.charts.ScatterChart;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;

public class Report3Fragment extends Fragment {

    private Report3ViewModel report3ViewModel;
    private FragmentReport3Binding binding;
    private ScatterChart chart;
    private DBHandler dbHandler;
    ArrayList<FishSizeClass> fishSizeClassArrayList = new ArrayList<>();
    Integer box1, box2, box3, box4, box5, box6, box7 = 0;
    TextView tvBox1, tvBox2, tvBox3, tvBox4, tvBox5, tvBox6, tvBox7;
    PieChart pieChart;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        report3ViewModel =
                new ViewModelProvider(this).get(Report3ViewModel.class);

        binding = FragmentReport3Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dbHandler = new DBHandler(getContext());
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Link those objects with their respective
        // id's that we have given in .XML file
        tvBox1 = view.findViewById(R.id.tvBox1);
        tvBox2 = view.findViewById(R.id.tvBox2);
        tvBox3 = view.findViewById(R.id.tvBox3);
        tvBox4 = view.findViewById(R.id.tvBox4);
        tvBox5 = view.findViewById(R.id.tvBox5);
        tvBox6 = view.findViewById(R.id.tvBox6);
        tvBox7 = view.findViewById(R.id.tvBox7);

        pieChart = view.findViewById(R.id.piechart);
        box1 = box2 = box3 = box4 = box5 = box6 = box7 = 0;
        setData();
    }
    private void setData(){
        // Set the percentage of language used
        fishSizeClassArrayList = dbHandler.readFishSizeFromDatabase();

        for (FishSizeClass fish: fishSizeClassArrayList) {
            if(fish.getFishBoxSize() == 1)
                box1++;
            else if(fish.getFishBoxSize() ==2 )
                box2++;
            else if(fish.getFishBoxSize() ==3 )
                box3++;
            else if(fish.getFishBoxSize() ==4 )
                box4++;
            else if(fish.getFishBoxSize() ==5 )
                box5++;
            else if(fish.getFishBoxSize() ==6 )
                box6++;
            else if(fish.getFishBoxSize() ==7 )
                box7++;
        }
        tvBox1.setText(Integer.toString(box1));
        tvBox2.setText(Integer.toString(box2));
        tvBox3.setText(Integer.toString(box3));
        tvBox4.setText(Integer.toString(box4));
        tvBox5.setText(Integer.toString(box5));
        tvBox6.setText(Integer.toString(box6));
        tvBox7.setText(Integer.toString(box7));

        // Set the data and color to the pie chart
        pieChart.addPieSlice(
                new PieModel(
                        "Bream",
                        Integer.parseInt(tvBox1.getText().toString()),
                        getResources().getColor(R.color.Box1)));
        pieChart.addPieSlice(
                new PieModel(
                        "Parkki",
                        Integer.parseInt(tvBox2.getText().toString()),
                        getResources().getColor(R.color.Box2)));
        pieChart.addPieSlice(
                new PieModel(
                        "Perch",
                        Integer.parseInt(tvBox3.getText().toString()),
                        getResources().getColor(R.color.Box3)));
        pieChart.addPieSlice(
                new PieModel(
                        "Pike",
                        Integer.parseInt(tvBox4.getText().toString()),
                        getResources().getColor(R.color.Box4)));
        pieChart.addPieSlice(
                new PieModel(
                        "Roach",
                        Integer.parseInt(tvBox5.getText().toString()),
                        getResources().getColor(R.color.Box5)));
        pieChart.addPieSlice(
                new PieModel(
                        "Smelt",
                        Integer.parseInt(tvBox6.getText().toString()),
                        getResources().getColor(R.color.Box6)));
        pieChart.addPieSlice(
                new PieModel(
                        "Whitefish",
                        Integer.parseInt(tvBox7.getText().toString()),
                        getResources().getColor(R.color.Box7)));
        pieChart.startAnimation();
    }
}