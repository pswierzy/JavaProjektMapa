package project.world.mapCreator;

import javax.swing.*;
import java.awt.*;

import static project.world.mapCreator.Map.SIZE;

public class MapVisualizer extends JPanel {

    private final Map map;

    public MapVisualizer(Map map) {
        this.map = map;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(map.getMapImage(), 0, 0, null);
    }

    public void init() {
        JFrame frame = new JFrame("Perlin Noise");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(SIZE, SIZE);
        frame.add(this);
        frame.setVisible(true);
    }
}
