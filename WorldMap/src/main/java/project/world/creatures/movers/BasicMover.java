package project.world.creatures.movers;

import project.world.Vector2d;
import project.world.creatures.Creature;
import project.world.mapGenerator.Map;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import static project.world.visualizer.PanelVisualizer.SIZE;

public class BasicMover implements Mover {
    protected Random rand = new Random();

    public boolean canMoveTo(Map map, Vector2d position) {
        return position.getX() > 0 && position.getX() < SIZE && position.getY() > 0 && position.getY() < SIZE;
    }

    public void move(Creature creature) {
        HashSet<Vector2d> possibleMoves = new HashSet<>();
        List<Vector2d> possibleMovesToRemove = new ArrayList<>();
        for(int x = -creature.getSpeed(); x <= creature.getSpeed(); x++) {
            for(int y = -creature.getSpeed(); y <= creature.getSpeed(); y++) {
                Vector2d vec = new Vector2d(x, y);
                possibleMoves.add(vec);
                possibleMovesToRemove.add(vec);
            }
        }

        while(!possibleMoves.isEmpty()) {
            System.out.println(possibleMoves.size());
            System.out.println(possibleMovesToRemove.size());
            Vector2d moveTo = possibleMovesToRemove.get(rand.nextInt(possibleMoves.size()));
            possibleMoves.remove(moveTo);
            possibleMovesToRemove.remove(moveTo);
            if(canMoveTo(creature.getMap(), creature.getPosition().add(moveTo))) {
                creature.goTo(creature.getPosition().add(moveTo));
                break;
            }
        }
    }
}
