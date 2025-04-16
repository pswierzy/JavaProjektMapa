package project.world.creatures;

import project.world.Vector2d;
import project.world.creatures.movers.Mover;
import project.world.mapGenerator.Map;

import java.awt.*;
import java.util.Random;

public abstract class AbstractCreature implements Creature {
    protected Random rand = new Random();
    protected int health;
    protected final int maxHealth;
    protected final int strength;
    protected final int speed;
    protected int age = 0;
    protected final int speciesOldAge;
    protected final int sight;
    protected final Color color;

    protected final Map map;
    protected Vector2d position;

    protected final  Mover mover;

    public AbstractCreature(int maxHealth, int strength, int speed, int speciesOldAge, Vector2d position, int sight, Map map, Mover mover, Color color) {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.strength = strength;
        this.speed = speed;
        this.speciesOldAge = speciesOldAge;
        this.position = position;
        this.sight = sight;
        this.color = color;
        this.map = map;
        this.mover = mover;
    }

    public Map getMap() {
        return map;
    }

    public boolean isDead() {
        return health>0;
    }
    public void heal(int amount) {
        health+=amount;
    }

    public int getHealth() {
        return health;
    }
    public int getStrength() {
        return strength;
    }
    public int getSpeed() {
        return speed;
    }
    public int getAge(){
        return age;
    }
    public int getSight(){
        return sight;
    }

    public Color getColor() {
        return color;
    }
    public Vector2d getPosition() {
        return position;
    }

    public void goTo(Vector2d position) {
        this.position = position;
    }

    public void move(){
        mover.move(this);
    }
}
