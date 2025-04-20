package com.example.oopproject;

// Status effects affect a Lutemon in combat, for example by reducing their damage or completely preventing them from attacking.
public class StatusEffect {
    public enum Effect {
        WEAK, // Reduces damage dealt
        HURT // Takes increased damage
    }

    private Effect effect;
    private int duration;

    public StatusEffect(Effect effect, int duration) {
        this.effect = effect;
        this.duration = duration;
    }

    public Effect getEffect() {
        return effect;
    }

    public int getDuration() {
        return duration;
    }

    // After each round we reduce the remaining duration by one
    public void reduceDuration() {
        duration--;
    }
}
