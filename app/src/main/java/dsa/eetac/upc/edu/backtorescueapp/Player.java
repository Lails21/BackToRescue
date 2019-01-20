package dsa.eetac.upc.edu.backtorescueapp;

import java.util.List;

public class Player {
    String username;
    int health;
    int mana;
    int damage;
    int defense;
    int level;
    double money;

    public Player(String username, int health, int mana, int damage, int defense, int level, double money) {
        this.username = username;
        this.health = health;
        this.mana = mana;
        this.damage = damage;
        this.defense = defense;
        this.level = level;
        this.money = money;
    }



    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Player(){}
}
