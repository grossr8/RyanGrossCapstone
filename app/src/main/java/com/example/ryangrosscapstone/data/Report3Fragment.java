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
    TextView tvBox1, tvBox2, tvBox3, tvBox4;
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
        pieChart = view.findViewById(R.id.piechart);

        setData();
    }
    private void setData(){
        // Set the percentage of language used
        tvBox1.setText(Integer.toString(40));
        tvBox2.setText(Integer.toString(30));
        tvBox3.setText(Integer.toString(5));
        tvBox4.setText(Integer.toString(25));

        // Set the data and color to the pie chart
        pieChart.addPieSlice(
                new PieModel(
                        "Bream",
                        Integer.parseInt(tvBox1.getText().toString()),
                        Color.parseColor("#FFA726")));
        pieChart.addPieSlice(
                new PieModel(
                        "Parkki",
                        Integer.parseInt(tvBox2.getText().toString()),
                        Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(
                new PieModel(
                        "Perch",
                        Integer.parseInt(tvBox3.getText().toString()),
                        Color.parseColor("#EF5350")));
        pieChart.addPieSlice(
                new PieModel(
                        "Pike",
                        Integer.parseInt(tvBox4.getText().toString()),
                        Color.parseColor("#29B6F6")));
        pieChart.startAnimation();
    }
}