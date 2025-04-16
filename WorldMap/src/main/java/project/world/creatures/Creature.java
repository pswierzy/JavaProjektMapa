package project.world.creatures;

import project.world.Vector2d;
import project.world.mapGenerator.Map;

import java.awt.*;

public interface Creature {
    public Map getMap();

    public boolean isDead();
    public void heal(int amount);

    public int getHealth();
    public int getStrength();
    public int getSpeed();
    public int getAge();
    public int getSight();

    public Color getColor();
    public Vector2d getPosition();

    public void move();
    public void goTo(Vector2d position);
}