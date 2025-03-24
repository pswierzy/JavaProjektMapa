package project.world.mapCreator;

import project.world.creatures.BasicCreature;

import javax.swing.*;
import java.awt.*;

import static project.world.mapCreator.Map.SIZE;

public class MapVisualizer extends JPanel {

    private final Map map;

    public MapVisualizer(Map map) {
        this.map = map;
    }

    private void paintCreatures(Graphics g) {
        int i=0;
        for(BasicCreature creature: map.getCreatureList()) {
            System.out.println("tworzenie stworzenia nr: " + i);
            i++;
            g.setColor(creature.getColor());
            g.fillRect(creature.getPosition().getX(), creature.getPosition().getY(), 10,10);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(map.getMapImage(), 0, 0, null);
        paintCreatures(g);
    }

    protected void updateMap() {
        repaint();
    }

    public void init() {
        JFrame frame = new JFrame("Perlin Noise");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(SIZE, SIZE);
        frame.add(this);
        frame.setVisible(true);
    }
}
