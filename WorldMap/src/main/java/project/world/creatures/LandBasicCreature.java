package project.world.creatures;

import project.world.Vector2d;
import project.world.creatures.movers.LandMover;
import project.world.mapGenerator.Map;

import java.awt.*;

public class LandBasicCreature extends BasicCreature {
    public LandBasicCreature(int maxHealth, int strength, int speed, int speciesOldAge, Vector2d position, int sight, Map map) {
        super(maxHealth, strength, speed, speciesOldAge, position, sight, map, new LandMover(), Color.CYAN);
    }
}
