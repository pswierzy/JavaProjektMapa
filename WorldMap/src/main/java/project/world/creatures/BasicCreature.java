package project.world.creatures;

import project.world.Vector2d;

import java.awt.*;

public class BasicCreature {

    private int health;
    private final int maxHealth;
    private final int strength;
    private final int speed;
    private int age = 0;
    private final int speciesOldAge;
    private final int sight;
    private final Color color;

    private Vector2d position;

    public BasicCreature(int maxHealth, int strength, int speed, int speciesOldAge, Vector2d position, int sight) {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.strength = strength;
        this.speed = speed;
        this.speciesOldAge = speciesOldAge;
        this.position = position;
        this.sight = sight;
        this.color = Color.MAGENTA;
    }

    public boolean isDead() {
        return health>0;
    }
    public void heal(int amount) {
        health+=amount;
    }
    public int getStrength() {
        return strength;
    }
    public int getSpeed() {
        return speed;
    }
    public Color getColor() {
        return color;
    }
    public Vector2d getPosition() {
        return position;
    }

    public void move(Vector2d direction) {
        position = position.add(direction);
    }

}
