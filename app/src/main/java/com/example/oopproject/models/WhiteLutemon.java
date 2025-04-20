package com.example.oopproject.models;

import com.example.oopproject.StatusEffect;

public class WhiteLutemon extends Lutemon {
    private int icePower;

    public WhiteLutemon(String name) {
        super(name, "White", 5, 100);
        this.icePower = 20;
    }

    @Override
    public void useAbility(Lutemon target) {
        System.out.println(getName() + " used ice spikes! " + target.getName() + " is frozen!");
        target.addStatusEffect(new StatusEffect(StatusEffect.Effect.WEAK, 1));
    }

    public int getIcePower() {
        return icePower;
    }

    @Override
    public String getAbilityName() {
        return "Ice Spikes";
    }
}