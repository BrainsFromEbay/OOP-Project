package com.example.oopproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oopproject.LutemonStorage;
import com.example.oopproject.R;
import com.example.oopproject.adapters.HomeAdapter;
import com.example.oopproject.models.*;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity implements HomeAdapter.OnLutemonClickListener {
    private HomeAdapter adapter;
    private LutemonStorage storage;
    // Lutemons selected for training or battle
    private final HashMap<Integer, Boolean> selectedLutemons = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        storage = LutemonStorage.getInstance();

        // Initialize RecyclerView
        RecyclerView recyclerViewHome = findViewById(R.id.recyclerViewHome);
        recyclerViewHome.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HomeAdapter(this, this);
        recyclerViewHome.setAdapter(adapter);

        // Initialize buttons
        Button btnCreate = findViewById(R.id.btnCreate);
        Button btnTraining = findViewById(R.id.btnTraining);
        Button btnBattle = findViewById(R.id.btnBattle);

        // Set listeners for buttons
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CreateLutemonActivity.class);
                startActivity(intent);
            }
        });

        // Move selected Lutemons to training or battle
        btnTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveSelectedLutemonsToTraining();
            }
        });

        // Move selected Lutemons to battle
        btnBattle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveSelectedLutemonsToBattle();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Update the adapter when returning to this activity
        adapter.updateLutemonList();
    }

    // Handle Lutemon click events
    @Override
    public void onLutemonClick(int id) {
        Lutemon lutemon = storage.getLutemon(id);
        if (lutemon != null) {
            Toast.makeText(this, "Selected: " + lutemon.getName(), Toast.LENGTH_SHORT).show();
        }
    }

    // Store the selected state of the Lutemon
    @Override
    public void onLutemonSelected(int id, boolean isSelected) {
        selectedLutemons.put(id, isSelected);
    }

    private void moveSelectedLutemonsToTraining() {
        boolean anyMoved = false;

        for (Integer id : selectedLutemons.keySet()) {
            if (selectedLutemons.get(id)) {
                storage.moveToTraining(id);
                anyMoved = true;
            }
        }

        if (anyMoved) {
            adapter.updateLutemonList();
            Intent intent = new Intent(HomeActivity.this, TrainingActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Lutemons moved to training", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No Lutemons selected", Toast.LENGTH_SHORT).show();
        }

        // Clear selections
        selectedLutemons.clear();
    }

    private void moveSelectedLutemonsToBattle() {
        boolean anyMoved = false;

        for (Integer id : selectedLutemons.keySet()) {
            if (selectedLutemons.get(id)) {
                storage.moveToBattle(id);
                anyMoved = true;
            }
        }

        if (anyMoved) {
            adapter.updateLutemonList();
            Intent intent = new Intent(HomeActivity.this, BattleActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Lutemons moved to battle", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No Lutemons selected", Toast.LENGTH_SHORT).show();
        }

        // Clear selections
        selectedLutemons.clear();
    }
}