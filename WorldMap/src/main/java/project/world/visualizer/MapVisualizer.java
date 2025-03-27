package project.world.visualizer;

import project.world.creatures.BasicCreature;
import project.world.mapCreator.Map;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MapVisualizer {
    private final Map map;

    public MapVisualizer(Map map) {
        this.map = map;
    }

    public BufferedImage createCombinedImage() {
        BufferedImage combinedImage = map.getMapImage();

        Graphics2D gImage = combinedImage.createGraphics();
        gImage.drawImage(map.getMapImage(), 0, 0, null);

        for (BasicCreature creature : map.getCreatureList()) {
            int x = creature.getPosition().getX();
            int y = creature.getPosition().getY();
            gImage.setColor(creature.getColor());
            gImage.fillRect(x, y, 10, 10);
        }
        gImage.dispose();

        return combinedImage;
    }
}
