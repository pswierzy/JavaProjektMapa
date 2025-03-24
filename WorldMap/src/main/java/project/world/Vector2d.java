package project.world;

import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.abs;

public class Vector2d {
    int x;
    int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public Vector2d add(Vector2d v) {
        return new Vector2d(x + v.getX(), y + v.getY());
    }
    public List<Vector2d> getNearbyTiles(int radius) {
        List<Vector2d> nearbyTiles = new LinkedList<Vector2d>();
        for(int i=x-radius; i<=x+radius; i++) {
            for(int j=y-radius; j<=y+radius; j++) {
                int dx = abs(x-i);
                int dy = abs(y-j);
                if(dx*dx + dy*dy <= radius*radius) {
                    nearbyTiles.add(new Vector2d(i, j));
                }
            }
        }
        return nearbyTiles;
    }
}
