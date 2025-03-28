package project.world.simulation;

public class SimulationParameters {
    private int amountOfCreatures = 10;
    private double speed = 1;


    public int getAmountOfCreatures() {
        return amountOfCreatures;
    }
    public double getSpeed() {
        return speed;
    }

    public void setAmountOfCreatures(int amountOfCreatures) {
        this.amountOfCreatures = amountOfCreatures;
    }
    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
