package project.world.creatures.movers;

import project.world.Vector2d;
import project.world.mapGenerator.Biome;
import project.world.mapGenerator.Map;


public class LandMover extends BasicMover {

    @Override
    public boolean canMoveTo(Map map, Vector2d position) {
        return (super.canMoveTo(map, position) && map.getBiome(position) != Biome.WATER);
    }

}
