package com.example.oopproject.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oopproject.LutemonStorage;
import com.example.oopproject.R;
import com.example.oopproject.models.Lutemon;
import com.example.oopproject.models.*;

public class CreateLutemonActivity extends AppCompatActivity {

    private EditText editTextName;
    private RadioGroup radioGroupType;
    private RadioButton radioButtonOrange, radioButtonBlack, radioButtonWhite, radioButtonGreen, radioButtonPink;
    private TextView textViewAbilityDesc;
    private Button btnCreateLutemon;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create);

        editTextName = findViewById(R.id.editTextName);
        radioGroupType = findViewById(R.id.radioGroupType);
        textViewAbilityDesc = findViewById(R.id.textViewAbilityDesc);
        btnCreateLutemon = findViewById(R.id.btnCreateLutemon);

        // Listeners
        // This is part of changing the ability description based on Lutemon
        radioGroupType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                updateAbilityDesc(checkedId);
            }
        });

        // Listens for the create button
        btnCreateLutemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createLutemon();
            }
        });
    }

    private void updateAbilityDesc(int checkedId) {
        if (checkedId == R.id.radioButtonOrange) {
            textViewAbilityDesc.setText("Sunpower");
        } else if (checkedId == R.id.radioButtonBlack) {
            textViewAbilityDesc.setText("Shadowball");
        } else if (checkedId == R.id.radioButtonWhite) {
            textViewAbilityDesc.setText("Ice power");
        } else if (checkedId == R.id.radioButtonGreen) {
            textViewAbilityDesc.setText("Leaf power");
        } else if (checkedId == R.id.radioButtonPink) {
            textViewAbilityDesc.setText("Heal");
        } else {
            textViewAbilityDesc.setText("Show ability description");
        }
    }

    private void createLutemon() {
        String name = editTextName.getText().toString();
        if (name.isEmpty()) {
            editTextName.setError("Please enter a name");
            return;
        }

        int selectedId = radioGroupType.getCheckedRadioButtonId();
        Lutemon newLutemon = null;
        if (selectedId == R.id.radioButtonOrange) {
            newLutemon = new OrangeLutemon(name);
        } else if (selectedId == R.id.radioButtonBlack) {
            newLutemon = new BlackLutemon(name);
        } else if (selectedId == R.id.radioButtonWhite) {
            newLutemon = new WhiteLutemon(name);
        } else if (selectedId == R.id.radioButtonGreen) {
            newLutemon = new GreenLutemon(name);
        } else if (selectedId == R.id.radioButtonPink) {
            newLutemon = new PinkLutemon(name);
        }

        if (newLutemon != null) {
            LutemonStorage storage = LutemonStorage.getInstance();
            storage.addLutemon(newLutemon);
            finish();
        } else {
            editTextName.setError("Please select a Lutemon type");
        }
    }
}
