package project.world.visualizer;

import project.world.creatures.Creature;
import project.world.mapGenerator.Map;

import java.awt.*;
import java.awt.image.BufferedImage;

import static project.world.visualizer.PanelVisualizer.SIZE;

public record MapVisualizer(Map map) {

    public BufferedImage createCombinedImage() {
        BufferedImage combinedImage = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics2D gImage = combinedImage.createGraphics();

        gImage.drawImage(map.getMapImage(), 0, 0, null);

        for (Creature creature : map.getCreatureList()) {
            int x = creature.getPosition().getX();
            int y = creature.getPosition().getY();
            gImage.setColor(creature.getColor());
            gImage.fillRect(x, y, 10, 10);
        }
        gImage.dispose();

        return combinedImage;
    }
}
