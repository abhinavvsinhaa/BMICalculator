package com.abhinavsinha.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private TextView resultText;
    private Button calculateButton;
    private EditText weight;
    private EditText feet;
    private EditText inches;
    private EditText age;
    private RadioButton radioButtonMale;
    private RadioButton radioButtonFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        handleButtonPress();
    }

    private void findViews() {
        resultText = findViewById(R.id.text_view_result);

        weight = findViewById(R.id.edit_text_weight);
        feet = findViewById(R.id.edit_text_feet);
        inches = findViewById(R.id.edit_text_inches);
        age = findViewById(R.id.edit_text_age);

        radioButtonMale = findViewById(R.id.radio_button_male);
        radioButtonFemale = findViewById(R.id.radio_button_female);

        calculateButton = findViewById(R.id.button_calculate);
    }

    private void handleButtonPress() {
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double bmi = calculateBMI();

                String sAge = age.getText().toString();
                int age = Integer.parseInt(sAge);

                if (age < 18) displayGuidance(bmi);
                else displayResult(bmi);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void displayGuidance(double bmi) {
        DecimalFormat decimalFormatter = new DecimalFormat("0.00");
        String resultBMI = decimalFormatter.format(bmi);

        if (radioButtonMale.isChecked()) {
            resultText.setText(resultBMI + " - As your age is under 18, consult your doctor for healthy range for boys");
        } else if (radioButtonFemale.isChecked()) {
            resultText.setText(resultBMI + " - As your age is under 18, consult your doctor for healthy range for girls");
        } else {
            resultText.setText(resultBMI + " - As your age is under 18, consult your doctor for healthy range");
        }
    }

    private double calculateBMI() {
        String sFeet = feet.getText().toString();
        String sInches = inches.getText().toString();
        String sWeight = weight.getText().toString();

        // converting strings to integers
        int iWeight = Integer.parseInt(sWeight);

        int totalHeightInInches = (Integer.parseInt(sFeet) * 12) + Integer.parseInt(sInches);

        // convert height into meters
        double totalHeightInMeters = totalHeightInInches * 0.0254;

        // BMI formula
        return iWeight / (totalHeightInMeters * totalHeightInMeters);
    }
    private void displayResult(double bmi) {
        DecimalFormat decimalFormatter = new DecimalFormat("0.00");
        String resultBMI = decimalFormatter.format(bmi);
        String fullResultString;

        if (bmi < 18.5) {
            // underweight
            fullResultString = resultBMI + " - You're underweight.";
        } else if (bmi > 25) {
            // overweight
            fullResultString = resultBMI + " - You're overweight.";
        } else {
            // healthy
            fullResultString = resultBMI + " - You're healthy!";
        }

        // set value of resultText
        resultText.setText(fullResultString);
    }
}