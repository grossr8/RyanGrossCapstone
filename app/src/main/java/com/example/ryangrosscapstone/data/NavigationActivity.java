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

import com.example.ryangrosscapstone.BoxSize;
import com.example.ryangrosscapstone.BoxSizeHelper;
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

    ArrayList<FishSizeClass> fishSizeClassArrayList = new ArrayList<FishSizeClass>();
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.appBarNavigation.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_enter_data, R.id.nav_report_1, R.id.nav_report_2, R.id.nav_report_3)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        dbHandler = new DBHandler(NavigationActivity.this);
        //dbHandler.dropAllTables(); //TODO: Remove later
        //dbHandler.createTablesIfNotExists(); //TODO: Remove later
        if(dbHandler.tableNotEmpty(dbHandler.getFishSizeTableName())){
            fishSizeClassArrayList = dbHandler.readFishSizeFromDatabase();
        }else {
            readFromFile();
        }
        if(!dbHandler.tableNotEmpty(dbHandler.getBoxSizeTableName())){
            dbHandler.addNewBoxSize(new BoxSize(1, null));
            dbHandler.addNewBoxSize(new BoxSize(2, null));
            dbHandler.addNewBoxSize(new BoxSize(3, null));
            dbHandler.addNewBoxSize(new BoxSize(4, null));
            dbHandler.addNewBoxSize(new BoxSize(5, null));
            dbHandler.addNewBoxSize(new BoxSize(6, null));
            dbHandler.addNewBoxSize(new BoxSize(7, null));
        }
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
                try {
                    weight = Double.parseDouble(fish[1]);
                    diagonalLength = Double.parseDouble(fish[2]);
                } catch(Exception ex)
                {
                    String error = ex.getMessage();
                }
                FishSizeClass fishSizeClass = new FishSizeClass(fish[0], weight, diagonalLength, null, BoxSizeHelper.BoxSelector(diagonalLength));
                fishSizeClassArrayList.add(fishSizeClass);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addFishDataToDatabase(ArrayList<FishSizeClass> fishArrayList) {
        for (FishSizeClass fish: fishArrayList) {
            dbHandler.addNewFishSize(fish);
        }
    }
}