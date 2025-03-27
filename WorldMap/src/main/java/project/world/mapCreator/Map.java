package project.world.mapCreator;

import project.world.Simulation;
import project.world.Vector2d;
import project.world.creatures.BasicCreature;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;

public class Map {
    public static final int SIZE = 800;

    private final PerlinNoise perlin1;
    private final PerlinNoise perlin2;
    private final PerlinNoise perlin3;
    private final PerlinNoise perlin4;

    private final Simulation simulation = new Simulation(this);

    private final boolean altitudeLines;
    private final boolean gridLines;

    private BufferedImage mapImage;

    private final LinkedList<BasicCreature> creatureList = new LinkedList<>();

    public Map(){
        this(true, true);
    }
    public Map(boolean altitudeLines, boolean gridLines) {
        this(altitudeLines, gridLines, 0);
    }
    public Map(boolean altitudeLines, boolean gridLines, int amountOfRandomCreatures) {
        this.altitudeLines = altitudeLines;
        this.gridLines = gridLines;
        perlin1 = new PerlinNoise(SIZE);
        perlin2 = new PerlinNoise(SIZE);
        perlin3 = new PerlinNoise(SIZE);
        perlin4 = new PerlinNoise(SIZE);
        generateMapImage();
        generateRandomCreatures(amountOfRandomCreatures);
    }

    public BufferedImage getMapImage() {
        return mapImage;
    }
    public LinkedList<BasicCreature> getCreatureList() {
        return creatureList;
    }

    public Color getColor(double value) {
        int scaledValue = (int) ((value + 1) * 500);

        if(scaledValue%10 == 0 && altitudeLines) return Color.BLACK;

        if(scaledValue <= 500) return new Color(0, scaledValue/4, 255);
        scaledValue -= 500;
        if(scaledValue <= 100) return new Color(0, 255, 100-scaledValue);
        scaledValue -= 100;
        if(scaledValue <= 50) return new Color(scaledValue * 5, 255, 0);
        scaledValue -= 50;
        return new Color(255, 255-scaledValue *5/7, 0);
    }
    public double getPointValue(int x, int y) {
        double value = perlin1.noise(x * 0.001f, y * 0.001f);
        value += perlin2.noise(x * 0.01f, y * 0.01f) * 0.7;
        value += perlin3.noise(x * 0.05f, y * 0.05f) * 0.05;
        value += perlin4.noise(x * 0.1f, y * 0.1f) * 0.005;
        return value/1.755;
    }
    public Biome getBiome(Vector2d v) {
        double pointValue = getPointValue(v.getX(), v.getY());
        int scaledValue = (int) ((pointValue + 1) * 500);

        if (scaledValue <= 500) return Biome.WATER;
        if (scaledValue <= 600) return Biome.GRASSLAND;
        if (scaledValue <= 650) return Biome.LOWER_MOUNTAINS;
        return Biome.MOUNTAINS;
    }

    public void generateMapImage() {
        int SQUARE_SIZE = SIZE/20;
        mapImage = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                if((x%SQUARE_SIZE == 0 || y%SQUARE_SIZE == 0) && gridLines) {
                    mapImage.setRGB(x, y, Color.black.getRGB());
                }
                else {
                    double value = getPointValue(x, y);
                    Color color = getColor(value);
                    mapImage.setRGB(x, y, color.getRGB());
                }
            }
        }
    }

    public void generateRandomCreatures(int amountOfRandomCreatures) {
        Random rand = new Random();
        for (int i = 0; i < amountOfRandomCreatures; i++) {
            Vector2d position = new Vector2d(rand.nextInt(SIZE), rand.nextInt(SIZE));
            BasicCreature creature = new BasicCreature(10, 10, 10, 10, position, 10);
            spawnCreature(creature);
        }
    }

    public void spawnCreature(BasicCreature creature) {
        creatureList.add(creature);
    }

    public void stopSimulation() {
        simulation.stopSimulation();
    }
    public void startSimulation() {
        simulation.startSimulation();
    }
}
