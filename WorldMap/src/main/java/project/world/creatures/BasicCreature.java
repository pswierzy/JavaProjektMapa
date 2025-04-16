package project.world.creatures;

import project.world.Vector2d;
import project.world.creatures.movers.BasicMover;
import project.world.creatures.movers.Mover;
import project.world.mapGenerator.Map;

import java.awt.*;


public class BasicCreature extends AbstractCreature {

    public BasicCreature(int maxHealth, int strength, int speed, int speciesOldAge, Vector2d position, int sight, Map map, Mover mover, Color color) {
        super(maxHealth, strength, speed, speciesOldAge, position, sight, map, mover, color);
    }

    public BasicCreature(int maxHealth, int strength, int speed, int speciesOldAge, Vector2d position, int sight, Map map) {
        this(maxHealth, strength, speed, speciesOldAge, position, sight, map, new BasicMover(), Color.MAGENTA);
    }



}
