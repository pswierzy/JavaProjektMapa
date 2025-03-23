package project.world.mapCreator;
import java.util.Random;

import static java.lang.Math.*;

public class PerlinNoise {
    private final int SIZE;
    private final float[][] gradients;

    public PerlinNoise(int size) {
        this.SIZE = size;
        gradients = new float[SIZE][SIZE];

        generateGradients();
    }

    public void generateGradients() {
        Random rand = new Random();
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                gradients[x][y] = rand.nextFloat() * (float)(2 * PI);
            }
        }
    }

    private double dot(float angle, double x, double y) {
        double gx = cos(angle);
        double gy = sin(angle);
        return gx * x + gy * y;
    }
    private double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }
    private double interpolate(double a, double b, double t) {
        return a + t * (b - a);
    }

    public double noise(float x, float y) {
        int x0 = (int) Math.floor(x);
        int y0 = (int) Math.floor(y);
        int x1 = x0 + 1;
        int y1 = y0 + 1;

        double dx = x - x0;
        double dy = y - y0;

        int x0Wrap = x0 % SIZE;
        int y0Wrap = y0 % SIZE;
        int x1Wrap = x1 % SIZE;
        int y1Wrap = y1 % SIZE;

        double dot00 = dot(gradients[x0Wrap][y0Wrap], dx, dy);
        double dot10 = dot(gradients[x1Wrap][y0Wrap], dx - 1, dy);
        double dot01 = dot(gradients[x0Wrap][y1Wrap], dx, dy - 1);
        double dot11 = dot(gradients[x1Wrap][y1Wrap], dx - 1, dy - 1);
        double u = fade(dx);
        double v = fade(dy);

        double lerpX0 = interpolate(dot00, dot10, u);
        double lerpX1 = interpolate(dot01, dot11, u);
        return interpolate(lerpX0, lerpX1, v);
    }
}
