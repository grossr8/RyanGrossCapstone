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

import com.example.ryangrosscapstone.BoxSizeHelper;
import com.example.ryangrosscapstone.FishSizeClass;
import com.example.ryangrosscapstone.R;
import com.example.ryangrosscapstone.DBHandler;
import com.example.ryangrosscapstone.databinding.FragmentEnterDataBinding;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

public class EnterDataFragment extends Fragment {

    private EnterDataViewModel enterDataViewModel;
    private FragmentEnterDataBinding binding;
    private DBHandler dbHandler;
    Spinner fishSpinner;
    EditText editTextFishWeight;
    Button enterFishButton;
    TextView displayLengthTextView;
    TextView boxSizeTextView;
    ArrayList<FishSizeClass> fishSizeClassArrayList = new ArrayList<>();
    ArrayList<double[]> collection = new ArrayList<>();
    SimpleRegression regression = new SimpleRegression();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        enterDataViewModel =
                new ViewModelProvider(this).get(EnterDataViewModel.class);

        binding = FragmentEnterDataBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

         dbHandler = new DBHandler(getContext());

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
        boxSizeTextView = view.findViewById(R.id.boxSizeTextView);
        fishSizeClassArrayList = dbHandler.readFishSizeFromDatabase();
        enterFishButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(TextUtils.isEmpty(editTextFishWeight.getText())){
                    editTextFishWeight.setError(getString(R.string.empty_field));
                } else{
                    addSelectedFishToCollection(fishSpinner.getSelectedItem().toString());
                    setUpLinearRegression();
                    String fishWeightString = editTextFishWeight.getText().toString();
                    Double fishLengthPrediction = regression.predict(Integer.parseInt(fishWeightString));
                    DecimalFormat f = new DecimalFormat("##.00");
                    String formattedValue = f.format(fishLengthPrediction);
                    displayLengthTextView.setText(formattedValue + " cm");
                    Integer boxSize = BoxSizeHelper.BoxSelector(fishLengthPrediction);
                    boxSizeTextView.setText("Please use box " + boxSize + " for shipping!");
                    dbHandler.addNewFishSize(new FishSizeClass(fishSpinner.getSelectedItem().toString(), Double.parseDouble(fishWeightString),
                            Double.parseDouble(formattedValue), null, boxSize));
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
    }

    public void addSelectedFishToCollection(String fishSelected)
    {
        for (FishSizeClass fish : fishSizeClassArrayList) {
            if (fishSelected.equalsIgnoreCase(fish.getFishName())) {
                double[] arrayFish = {fish.getFishWeight(), fish.getFishDiagonalLength()};
                collection.add(arrayFish);
            }
        }
    }
}