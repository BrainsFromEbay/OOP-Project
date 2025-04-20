package com.example.oopproject.models;

public class GreenLutemon extends Lutemon{
    private int leafPower;

    public GreenLutemon(String name) {
        super(name, "Green", 5, 100);
        this.leafPower = 20;
    }

    @Override
    public void useAbility(Lutemon target) {
        System.out.println(getName() + " used vines!");
        int damage = leafPower * 5;
        System.out.println("Vines deal " +  damage + " damage!");
        target.takeDamage(damage);
    }

    public int getLeafPower() {
        return leafPower;
    }

    @Override
    public String getAbilityName() {
        return "Vines";
    }
}
