package project.world.simulation;

import project.world.mapGenerator.Map;

import java.util.Random;

import static java.lang.Thread.sleep;

public class Simulation implements Runnable {
    private final Random rand = new Random();
    private final Map map;
    private final double speed;

    private volatile boolean running = false;

    public Simulation(Map map) {
        this(map, new SimulationParameters());
    }
    public Simulation(Map map, SimulationParameters params) {
        this.map = map;
        speed = params.getSpeed();
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
                sleep((long) (100/speed));
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
