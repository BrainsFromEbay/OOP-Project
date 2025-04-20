package com.example.oopproject.models;

import java.util.ArrayList;
import java.util.Random;

import com.example.oopproject.StatusEffect;
import com.example.oopproject.Location;

public abstract class Lutemon {
    protected ArrayList<StatusEffect> statusEffects;
    protected String name;
    protected String color;
    protected int attack;
    protected int maxHealth;
    protected int health;
    protected int experience;
    protected int level;
    protected Location location;
    protected Random random;

    public Lutemon(String name, String color, int attack, int health) {
        this.name = name;
        this.color = color;
        this.attack = attack;
        this.maxHealth = 100;
        this.health = health;
        this.experience = 0;
        this.level = 1;
        this.location = Location.HOME;
        this.statusEffects = new ArrayList<>();
        this.random = new Random();
    }

    // getters
    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getAttack() {
        return attack;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getHealth() {
        return health;
    }

    public int getExperience() {
        return experience;
    }

    public int getLevel() {
        return level;
    }

    public Location getLocation() {
        return location;
    }

    public ArrayList<StatusEffect> getEffect() {
        return statusEffects;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void restoreHealth() {
        this.health = this.maxHealth;
    }


    public void addStatusEffect(StatusEffect effect) {
        statusEffects.add(effect);
        System.out.println(name + " is now affected by " + effect.getEffect() + "!");
    }

    public void updateStatusEffect() {
        for (int i = 0; i < statusEffects.size(); i++) {
            StatusEffect effect = statusEffects.get(i);
            effect.reduceDuration();

            if (effect.getDuration() <= 0) {
                System.out.println(name + " is no longer affected by " + effect.getEffect() + "!");
                statusEffects.remove(i);
                i--;
            }
        }
    }

    // XP and levels
    public void addExperience(int xp) {
        this.experience += xp;
        if (experience >= 100) {
            experience -= 100;
            levelUp();
        }
    }

    // TODO: make something cool here :)
    public void levelUp() {
        level ++;
        attack += 1;
        health += 10;
        System.out.println(name + "leveled up!");
    }

    // Combat stuff
    public void attack(Lutemon target) {
        boolean isWeak = false;
        for (StatusEffect effect : statusEffects) {
            if (effect.getEffect() == StatusEffect.Effect.WEAK) {
                isWeak = true;
                break;
            }
        }
        System.out.println(this.name + " attacked " + target.getName() + "!");
        int damage = this.attack;

        if (isWeak) {
            damage = (int) (damage / 2);
            System.out.println(target.getName() + " is weak! Damage is reduced!");
        }

        int randomDamage = random.nextInt(damage);
        damage += randomDamage;

        System.out.println(this.name + " dealt " + damage + " damage!");
        target.takeDamage(damage);
    }

    public void takeDamage(int damage) {
        boolean isHurt = false;

        for (StatusEffect effect : statusEffects) {
            if (effect.getEffect() == StatusEffect.Effect.HURT) {
                isHurt = true;
                break;
            }
        }

        if (isHurt) {
            damage = (int) (damage * 1.5);
            System.out.println(this.name + " is hurt! Damage is increased!");
        }

        this.health -= damage;

        if (health <= 0) {
            health = 0;
            System.out.println(name + " fainted!");
        }
    }

    /*
    Each Lutemon (color) has its own ability. Abilities are:
    White: Ice (slows)
    Black: Shadowball (damage)
    Pink: Heal
    Green: Vines (damage and maybe something?)
    Orange: Sun (damage)
     */
    public abstract void useAbility(Lutemon target);

    public abstract String getAbilityName();
}
