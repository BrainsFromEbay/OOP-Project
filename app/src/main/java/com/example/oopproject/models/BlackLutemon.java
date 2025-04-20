package com.example.oopproject.models;

import com.example.oopproject.StatusEffect;

public class BlackLutemon extends Lutemon {
    private int shadowPower;

    public BlackLutemon(String name) {
        super(name, "Black", 5, 100);
        this.shadowPower = 5;
    }

    @Override
    public void useAbility(Lutemon target) {
        System.out.println(getName() + " used shadowball!");
        target.addStatusEffect(new StatusEffect(StatusEffect.Effect.HURT, 1));
    }

    public int getShadowPower() {
        return shadowPower;
    }

    @Override
    public String getAbilityName() {
        return "Shadowball";
    }
}
