package project.world;

import project.world.creatures.BasicCreature;
import project.world.mapCreator.Map;

import java.util.Random;

import static java.lang.Thread.sleep;

public class Simulation {
    private final Random rand = new Random();
    private final Map map = new Map(true, true, 1, 10);

    private void handleMoving() {
        for(BasicCreature creature: map.getCreatureList()) {
            int x = rand.nextInt(creature.getSpeed() * 2) - creature.getSpeed();
            int y = rand.nextInt(creature.getSpeed() * 2) - creature.getSpeed();
            System.out.println(creature.getPosition());
            creature.move(new Vector2d(x, y));
            System.out.println(creature.getPosition());
        }
    }

    public void run() {
        try{
            while(true) {
                handleMoving();
                map.updateMap();
                sleep(50);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
