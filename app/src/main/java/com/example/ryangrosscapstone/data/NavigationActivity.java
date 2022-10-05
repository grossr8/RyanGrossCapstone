package com.example.ryangrosscapstone.data;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.ryangrosscapstone.DBHandler;
import com.example.ryangrosscapstone.FishSizeClass;
import com.example.ryangrosscapstone.MainActivity;
import com.example.ryangrosscapstone.R;
import com.example.ryangrosscapstone.databinding.ActivityNavigationBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class NavigationActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavigationBinding binding;

    //ArrayList<double[]> collection = new ArrayList<>();
    ArrayList<FishSizeClass> fishSizeClassArrayList = new ArrayList<FishSizeClass>();
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.appBarNavigation.toolbar);
        binding.appBarNavigation.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        dbHandler = new DBHandler(NavigationActivity.this);
        readFromFile();
        setUpLinearRegression();
        addFishDataToDatabase(fishSizeClassArrayList);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void setUpLinearRegression() {
        SimpleRegression regression = new SimpleRegression();

        for (FishSizeClass fish : fishSizeClassArrayList) {
            regression.addData(fish.getFishWeight(), fish.getFishDiagonalLength());

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
            double weight = 0;
            double diagonalLength = 0;
            while ((line = br.readLine()) != null) {
                String[] fish = line.split(splitBy);
                //Log.i("FishInfoFromFileTag", "fish[weight=" + fish[0] + ", diagonal length=" + fish[1] + "]");
                try {
                    weight = Double.parseDouble(fish[1]);
                    diagonalLength = Double.parseDouble(fish[2]);
                } catch(Exception ex)
                {
                    String error = ex.getMessage();
                }

                //double[] arrayFish = {weight, diagonalLength};
                FishSizeClass fishSizeClass = new FishSizeClass(fish[0], weight, diagonalLength);
                fishSizeClassArrayList.add(fishSizeClass);
                //collection.add(arrayFish);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*public void readFromFile(String fishName){
        String line = "";
        String splitBy = ",";
        String fishFileBream = "fish_data_bream.csv";
        String fishFileParkki = "fish_data_parkki.csv";
        String fishFilePerch = "fish_data_perch.csv";
        String fishFilePike = "fish_data_pike.csv";
        String fishFileRoach = "fish_data_roach.csv";
        String fishFileSmelt = "fish_data_smelt.csv";
        String fishFileWhitefish = "fish_data_whitefish.csv";

        String fishFile = "fish_data_bream";


        switch(fishName){
            case "Bream":
                fishFile = fishFileBream;
                break;
            case "Parkki":
                fishFile = fishFileParkki;
                break;
            case "Perch":
                fishFile = fishFilePerch;
                break;
            case "Pike":
                fishFile = fishFilePike;
                break;
            case "Roach":
                fishFile = fishFileRoach;
                break;
            case "Smelt":
                fishFile = fishFileSmelt;
                break;
            case "Whitefish":
                fishFile = fishFileWhitefish;
                break;
        }
        try {
            // parsing a CSV file into BufferedReader class constructor
            InputStreamReader inputStream = new InputStreamReader(getContext().getAssets().open(fishFile));
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
    }*/

    private void addFishDataToDatabase(ArrayList<FishSizeClass> fishArrayList) {
        for (FishSizeClass fish: fishArrayList) {
            dbHandler.addNewFishSize(fish);
        }
    }
}