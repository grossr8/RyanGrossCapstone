package com.example.ryangrosscapstone.data.ui.enterData;

import android.app.Application;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ryangrosscapstone.R;
import com.example.ryangrosscapstone.databinding.FragmentEnterDataBinding;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class EnterDataFragment extends Fragment {

    private EnterDataViewModel enterDataViewModel;
    private FragmentEnterDataBinding binding;

    Spinner fishSpinner;
    EditText editTextFishWeight;
    Button enterFishButton;
    TextView displayLengthTextView;

    String line = "";
    String splitBy = ",";
    ArrayList<double[]> collection = new ArrayList<>();
    SimpleRegression regression = new SimpleRegression();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        enterDataViewModel =
                new ViewModelProvider(this).get(EnterDataViewModel.class);

        binding = FragmentEnterDataBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textGallery;
//        enterDataViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fishSpinner = view.findViewById(R.id.fishSpinner);
        ArrayAdapter<CharSequence> fishAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.fish_array, android.R.layout.simple_spinner_item);
        fishAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fishSpinner.setAdapter(fishAdapter);
        editTextFishWeight = view.findViewById(R.id.editTextFishWeight);
        enterFishButton = view.findViewById(R.id.fishGetLengthButton);
        displayLengthTextView = view.findViewById(R.id.displayLengthTextView);
        enterFishButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(TextUtils.isEmpty(editTextFishWeight.getText())){
                    editTextFishWeight.setError(getString(R.string.empty_field));
                } else{
                    readFromFile(fishSpinner.getSelectedItem().toString());
                    setUpLinearRegression();
                    String displaylengthText = String.valueOf(regression.predict(Integer.parseInt(editTextFishWeight.getText().toString())));
                    displayLengthTextView.setText(displaylengthText);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setUpLinearRegression() {

        for (double[] fish : collection) {
            regression.addData(fish[0], fish[1]);
        }

        /*Log.i("InterceptTag", String.valueOf(regression.getIntercept()));
        Log.i("SlopeTag", String.valueOf(regression.getSlope()));
        Log.i("SlopeStdErrTag", String.valueOf(regression.getSlopeStdErr()));
        Log.i("PredictTag", String.valueOf(regression.predict(1400)));*/
    }

    public void readFromFile(String fishName){
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
    }
}