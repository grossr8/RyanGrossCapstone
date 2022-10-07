package com.example.ryangrosscapstone;

import androidx.appcompat.app.AppCompatActivity;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String line = "";
    String splitBy = ",";
    //ArrayList<double[]> collection = new ArrayList<>();
    //ArrayList<FishSizeClass> fishSizeClassArrayList = new ArrayList<FishSizeClass>();

    //private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //dbHandler = new DBHandler(MainActivity.this);
        //readFromFile();
        //setUpLinearRegression();
        //addFishDataToDatabase(fishSizeClassArrayList);
    }


    /*private void setUpLinearRegression() {
        SimpleRegression regression = new SimpleRegression();

        for (double[] fish : collection) {
            regression.addData(fish[0], fish[1]);
            FishSizeClass fishSizeClass = new FishSizeClass("", fish[0], fish[1]);
            fishSizeClassArrayList.add(fishSizeClass);
        }

        Log.i("InterceptTag", String.valueOf(regression.getIntercept()));
        Log.i("SlopeTag", String.valueOf(regression.getSlope()));
        Log.i("SlopeStdErrTag", String.valueOf(regression.getSlopeStdErr()));
        Log.i("PredictTag", String.valueOf(regression.predict(1400)));
    }

    public void readFromFile(){
        String line = "";
        String splitBy = ",";

        try {
            // parsing a CSV file into BufferedReader class constructor
            InputStreamReader inputStream = new InputStreamReader(getAssets().open("fish_raw_data.csv"));
            BufferedReader br = new BufferedReader(inputStream);
            while ((line = br.readLine()) != null) {
                String[] fish = line.split(splitBy);
                Log.i("FishInfoFromFileTag", "fish[weight=" + fish[0] + ", diagonal length=" + fish[1] + "]");
                double weight = Double.parseDouble(fish[0]);
                double diagonalLength = Double.parseDouble(fish[1]);
                double[] arrayFish = {weight, diagonalLength};
                collection.add(arrayFish);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addFishDataToDatabase(ArrayList<FishSizeClass> fishArrayList) {
        for (FishSizeClass fish: fishArrayList) {
            dbHandler.addNewFishSize(fish);
        }
    }*/
}