package com.example.oopproject.models;

public class PinkLutemon extends Lutemon {
    private int healPower;

    public PinkLutemon(String name) {
        super(name, "Pink", 5, 100);
        this.healPower = 20;
    }

    @Override
    public void useAbility(Lutemon target) {
        System.out.println(getName() + " healed itself!");
        System.out.println("Healing restores " + healPower + "HP!");

        // If the healing would go over the max health, only heal so that the lutemon is at max health
        if (this.health + healPower > getMaxHealth()) {
            this.health = getMaxHealth();
        } else {
            this.health += healPower;
        }
    }

    public int getHealPower() {
        return healPower;
    }

    @Override
    public String getAbilityName() {
        return "Heal";
    }
}

