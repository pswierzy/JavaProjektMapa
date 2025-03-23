package project.world.mapCreator;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Math.floor;

public class Map {
    public static final int SIZE = 1024;
    private final int zoomIn;

    private final PerlinNoise perlin1;
    private final PerlinNoise perlin2;
    private final PerlinNoise perlin3;
    private final PerlinNoise perlin4;

    private final boolean altitudeLines;
    private final boolean gridLines;

    private BufferedImage mapImage;

    public Map(boolean altitudeLines, boolean gridLines) {
        this(altitudeLines, gridLines, 1);
    }
    public Map(boolean altitudeLines, boolean gridLines, int zoomIn) {
        this.altitudeLines = altitudeLines;
        this.gridLines = gridLines;
        this.zoomIn = zoomIn;
        perlin1 = new PerlinNoise(SIZE/zoomIn);
        perlin2 = new PerlinNoise(SIZE/zoomIn);
        perlin3 = new PerlinNoise(SIZE/zoomIn);
        perlin4 = new PerlinNoise(SIZE/zoomIn);
        generateMapImage();
    }

    public BufferedImage getMapImage() {
        return mapImage;
    }

    private Color getColor(double value) {
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

    public void generateMapImage() {
        int SQUARE_SIZE = SIZE/20;
        mapImage = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                if((x%SQUARE_SIZE == 0 || y%SQUARE_SIZE == 0) && gridLines) {
                    mapImage.setRGB(x, y, Color.black.getRGB());
                }
                else {
                    double value = getPointValue(x / zoomIn, y / zoomIn);
                    Color color = getColor(value);
                    mapImage.setRGB(x, y, color.getRGB());
                }
            }
        }
    }

    public void showMap() {
        MapVisualizer mapVisualizer = new MapVisualizer(this);
        mapVisualizer.init();
    }

}
