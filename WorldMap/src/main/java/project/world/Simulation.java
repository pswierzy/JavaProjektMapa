package project.world;

import project.world.mapGenerator.Map;

import java.util.Random;

import static java.lang.Thread.sleep;

public class Simulation implements Runnable {
    private final Random rand = new Random();
    private final Map map;

    private volatile boolean running = false;

    public Simulation(Map map) {
        this.map = map;
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
                map.handleMoving();
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
