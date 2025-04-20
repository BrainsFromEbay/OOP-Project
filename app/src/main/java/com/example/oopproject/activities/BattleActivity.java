package com.example.oopproject.activities;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oopproject.Location;
import com.example.oopproject.LutemonStorage;
import com.example.oopproject.R;
import com.example.oopproject.models.*;
import com.example.oopproject.StatusEffect;

import java.util.ArrayList;
import java.util.HashMap;

public class BattleActivity extends AppCompatActivity{
    private LutemonStorage storage;
    private TextView textViewLutemonDownName, textViewLutemonUpName, textViewBattleLog;
    private Button btnAttack, btnAbility;
    private ImageView imageViewLutemonDown, imageViewLutemonUp;
    private ProgressBar hpBarLutemonDown, hpBarLutemonUp;
    private Lutemon lutemonDown, lutemonUp;
    // We use this "isLutemonDownAttacking" flag/boolean to determine which lutemon is attacking.
    private boolean isLutemonDownAttacking = true;
    private boolean lutemonUpAbilityUsed = false;
    private boolean lutemonDownAbilityUsed = false;

    private HashMap<Lutemon, ArrayList<StatusEffect>> lutemonStatusEffects = new HashMap<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.battle);

        storage = LutemonStorage.getInstance();

        textViewLutemonDownName = findViewById(R.id.textViewLutemonDownName);
        textViewLutemonUpName = findViewById(R.id.textViewLutemonUpName);
        btnAttack = findViewById(R.id.btnAttack);
        btnAbility = findViewById(R.id.btnAbility);
        imageViewLutemonDown = findViewById(R.id.imageViewLutemonDown);
        imageViewLutemonUp = findViewById(R.id.imageViewLutemonUp);
        hpBarLutemonDown = findViewById(R.id.hpBarLutemonDown);
        hpBarLutemonUp = findViewById(R.id.hpBarLutemonUp);
        textViewBattleLog = findViewById(R.id.textViewBattleLog);
        textViewBattleLog.setMovementMethod(new ScrollingMovementMethod());

        ArrayList<Lutemon> lutemonsInBattle = new ArrayList<>();
        for (Lutemon lutemon : storage.getAllLutemons().values()) {
            if (lutemon.getLocation() == Location.BATTLE) {
                lutemonsInBattle.add(lutemon);
            }
        }

        if (lutemonsInBattle.size() >= 2) {
            lutemonUp = lutemonsInBattle.get(0);
            lutemonDown = lutemonsInBattle.get(1);

            lutemonStatusEffects.put(lutemonUp, new ArrayList<>());
            lutemonStatusEffects.put(lutemonDown, new ArrayList<>());

            startBattle();
        } else {
            System.out.println("Not enough Lutemons in battle");
        }

        btnAttack.setOnClickListener(v -> {
            if (isLutemonDownAttacking) {
                lutemonDown.attack(lutemonUp);
                logBattle(lutemonDown.getName() + " attacks " + lutemonUp.getName());
                isLutemonDownAttacking = false;
            } else {
                lutemonUp.attack(lutemonDown);
                logBattle(lutemonUp.getName() + " attacks " + lutemonDown.getName());
                isLutemonDownAttacking = true;
            }

            checkBattleResult();
            updateUI();
            lutemonDown.updateStatusEffect();
            lutemonUp.updateStatusEffect();
        });

        btnAbility.setOnClickListener(v -> {
            if (isLutemonDownAttacking && !lutemonDownAbilityUsed) {
                lutemonDown.useAbility(lutemonDown);
                logBattle(lutemonDown.getName() + " uses ability " + lutemonDown.getAbilityName());
                isLutemonDownAttacking = false;
                lutemonDownAbilityUsed = true;
            } else if (!isLutemonDownAttacking && !lutemonUpAbilityUsed) {
                lutemonUp.useAbility(lutemonUp);
                logBattle(lutemonUp.getName() + " uses ability " + lutemonUp.getAbilityName());
                isLutemonDownAttacking = true;
                lutemonUpAbilityUsed = true;
            } else {
                logBattle("Ability already used!");
            }
        });
    }

    // Sets the lutemon's name, image and hpbar
    private void startBattle() {
        textViewLutemonDownName.setText(lutemonDown.getName());
        textViewLutemonUpName.setText(lutemonUp.getName());

        hpBarLutemonDown.setMax(lutemonDown.getMaxHealth());
        hpBarLutemonUp.setMax(lutemonUp.getMaxHealth());

        updateUI();
    }

    private void checkBattleResult() {
        if (lutemonDown.getHealth() <= 0) {
            logBattle(lutemonDown.getName() + " has fainted!");
            int id = getLutemonId(lutemonDown);
            lutemonDown.addExperience(1);
            storage.moveToHome(id);
            endBattle();
        } else if (lutemonUp.getHealth() <= 0) {
            logBattle(lutemonUp.getName() + " has fainted!");
            lutemonUp.restoreHealth();
            lutemonUp.setLocation(Location.HOME);
            int id = getLutemonId(lutemonUp);
            lutemonUp.addExperience(1);
            storage.moveToHome(id);
            endBattle();
        }
    }

    private void endBattle() {
        btnAttack.setEnabled(false);
        btnAbility.setEnabled(false);
        logBattle("Battle ended!");
        finish();
    }

    private void updateUI() {
        hpBarLutemonDown.setProgress(lutemonDown.getHealth());
        hpBarLutemonUp.setProgress(lutemonUp.getHealth());
    }

    private void logBattle(String msg) {
        textViewBattleLog.append(msg + "\n");
    }

    // This method shouldn't be in this class, but I'm running ot of time and putting it here temporarily.
    private int getLutemonId(Lutemon lutemon) {
        for (int id : storage.getAllLutemons().keySet()) {
            if (storage.getLutemon(id) == lutemon) {
                return id;
            }
        }
        return -1;
    }
}
