package project.world;

import project.world.creatures.BasicCreature;
import project.world.mapCreator.Map;

import java.util.Random;

import static java.lang.Thread.sleep;

public class Simulation implements Runnable {
    private final Random rand = new Random();
    private final Map map;

    private volatile boolean running = false;

    public Simulation(Map map) {
        this.map = map;
    }

    private void handleMoving() {
        for (BasicCreature creature : map.getCreatureList()) {
            int x = rand.nextInt(creature.getSpeed() * 2) - creature.getSpeed();
            int y = rand.nextInt(creature.getSpeed() * 2) - creature.getSpeed();
            System.out.println(creature.getPosition());
            creature.move(new Vector2d(x, y));
            System.out.println(creature.getPosition());
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                synchronized (this) {
                    while (!running) {
                        wait();
                    }
                }
                handleMoving();
                sleep(50);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void startSimulation() {
        running = true;
        notify();
    }

    public synchronized void stopSimulation() {
        running = false;
    }
}
