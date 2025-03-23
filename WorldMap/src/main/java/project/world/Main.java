package project.world;

import project.world.mapCreator.Map;

public class Main {
    public static void main(String[] args) {
        Map map = new Map(true, true, 5);
        map.showMap();
    }
}
