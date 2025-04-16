package project.world.creatures.movers;

import project.world.Vector2d;
import project.world.creatures.Creature;
import project.world.mapGenerator.Map;

public interface Mover {
    public boolean canMoveTo(Map map, Vector2d position);
    public void move(Creature creature);
}
