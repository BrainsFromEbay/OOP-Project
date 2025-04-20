package com.example.oopproject.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oopproject.Location;
import com.example.oopproject.LutemonStorage;
import com.example.oopproject.R;
import com.example.oopproject.models.Lutemon;

public class TrainingActivity extends AppCompatActivity {

    private LutemonStorage storage;
    private Button btnTrain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training);

        storage = LutemonStorage.getInstance();

        btnTrain = findViewById(R.id.btnTrain);

        btnTrain.setOnClickListener(v -> trainLutemons());
    }

    private void trainLutemons() {
        for (Lutemon l : storage.getAllLutemons().values()) {
            if (l.getLocation() == Location.TRAINING) {
                l.addExperience(1); // You could randomize this or allow manual repeat
                Toast.makeText(this, l.getName() + " trained successfully!", Toast.LENGTH_SHORT).show();
                int id = getLutemonId(l);
                storage.moveToHome(id);
                finish();
            }
        }
    }

    private int getLutemonId(Lutemon lutemon) {
        for (int id : storage.getAllLutemons().keySet()) {
            if (storage.getLutemon(id) == lutemon) {
                return id;
            }
        }
        return -1;
    }

}
