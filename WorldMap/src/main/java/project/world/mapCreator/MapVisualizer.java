package project.world.mapCreator;

import project.world.creatures.BasicCreature;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;

import static project.world.mapCreator.Map.SIZE;

public class MapVisualizer extends JPanel {

    private final Map map;
    private final JSlider zoomSlider;
    private double zoomLevel = 1.0;
    private int offsetX = 0, offsetY = 0;
    private int lastMouseX, lastMouseY;

    public MapVisualizer(Map map) {
        this.map = map;

        zoomSlider = new JSlider(JSlider.VERTICAL, 100, 500, 100);
        zoomSlider.setMajorTickSpacing(10);
        zoomSlider.setPaintTicks(true);
        zoomSlider.setPreferredSize(new Dimension(30, 100));

        zoomSlider.addChangeListener(e -> {
            double oldZoom = zoomLevel;
            zoomLevel = zoomSlider.getValue() / 100.0;

            // Środek ekranu w oknie
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;

            // Poprawione obliczenia offsetu - idealne wycentrowanie
            offsetX = (int) (centerX - (centerX - offsetX) * (zoomLevel / oldZoom));
            offsetY = (int) (centerY - (centerY - offsetY) * (zoomLevel / oldZoom));

            repaint();
        });


        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastMouseX = e.getX();
                lastMouseY = e.getY();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int dx = e.getX() - lastMouseX;
                int dy = e.getY() - lastMouseY;

                offsetX += dx;
                offsetY += dy;

                lastMouseX = e.getX();
                lastMouseY = e.getY();

                repaint();
            }
        });
    }

    private void paintCreatures(Graphics2D g2d) {
        for (BasicCreature creature : map.getCreatureList()) {
            int x = (int) ((creature.getPosition().getX() * zoomLevel) + offsetX);
            int y = (int) ((creature.getPosition().getY() * zoomLevel) + offsetY);
            g2d.setColor(creature.getColor());
            g2d.fillRect(x, y, (int) (10 * zoomLevel), (int) (10 * zoomLevel));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        AffineTransform transform = new AffineTransform();
        transform.translate(offsetX, offsetY);
        transform.scale(zoomLevel, zoomLevel);
        g2d.drawImage(map.getMapImage(), transform, null);

        paintCreatures(g2d);
    }

    public void updateMap() {
        repaint();
    }

    public void init() {
        JFrame frame = new JFrame("Perlin Noise");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(SIZE + 200, SIZE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(this, BorderLayout.CENTER);

        mainPanel.add(zoomSlider, BorderLayout.EAST);

        frame.add(mainPanel);
        frame.setVisible(true);
    }
}
