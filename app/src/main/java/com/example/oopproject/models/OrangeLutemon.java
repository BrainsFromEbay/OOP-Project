package com.example.oopproject.models;

public class OrangeLutemon extends Lutemon {
    private int sunpower;
    public OrangeLutemon(String name) {
        super(name, "White", 10, 100);
        this.sunpower = 5;
    }

    @Override
    public void useAbility(Lutemon target) {
        System.out.println(getName() + " used Sunshine!");
        int damage = sunpower * 3;
        System.out.println("Sunshine deals" + damage + " damage!");
        target.takeDamage(damage);
    }

    public int getSunpower() {
        return sunpower;
    }

    @Override
    public String getAbilityName() {
        return "Sunshine";
    }
}
