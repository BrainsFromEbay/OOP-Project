package com.example.oopproject;

import com.example.oopproject.models.Lutemon;

import java.util.ArrayList;
import java.util.HashMap;

public class LutemonStorage {
    private static LutemonStorage instance;
    private HashMap<Integer, Lutemon> lutemons;
    private int currentId;

    private LutemonStorage() {
        lutemons = new HashMap<>();
        currentId = 0;
    }

    // Singleton
    public static LutemonStorage getInstance() {
        if (instance == null) {
            instance = new LutemonStorage();
        }
        return instance;
    }

    public int addLutemon(Lutemon lutemon) {
        int id = currentId++;
        lutemons.put(id, lutemon);
        return id;
    }

    public Lutemon getLutemon(int id) {
        return lutemons.get(id);
    }

    public void removeLutemon(int id) {
        lutemons.remove(id);
    }

    public HashMap<Integer, Lutemon> getAllLutemons() {
        return lutemons;
    }


    // Lutemons should move to either battle from training only from home.
    public void moveToBattle(int id) {
        Lutemon lutemon = lutemons.get(id);
        if (lutemon != null) {
            lutemon.setLocation(Location.BATTLE);
            System.out.println(lutemon.getName() + " was moved to battle");
        } else {
            System.out.println("This Lutemon was not found");
        }
    }

    public void moveToTraining(int id) {
        Lutemon lutemon = lutemons.get(id);
        if (lutemon != null) {
            lutemon.setLocation(Location.TRAINING);
            System.out.println(lutemon.getName() + " was moved to training");
        } else {
            System.out.println("This Lutemon was not found");
        }
    }

    public void moveToHome(int id) {
        Lutemon lutemon = lutemons.get(id);
        if (lutemon != null) {
            lutemon.setLocation(Location.HOME);
            lutemon.restoreHealth();
            System.out.println(lutemon.getName() + " was moved to home");
        }
    }
}