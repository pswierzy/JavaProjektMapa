package project.world.visualizer;

import project.world.mapCreator.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import static project.world.mapCreator.Map.SIZE;

public class PanelVisualizer extends JPanel {

    private final MapVisualizer mapVisualizer;
    private double zoomLevel = 1.0;
    private int offsetX = 0, offsetY = 0;
    private int lastMouseX, lastMouseY;

    public PanelVisualizer(Map map) {
        this.mapVisualizer = new MapVisualizer(map);

        // Obsługa przesuwania myszy
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

        // Obsługa zoomowania kółkiem myszy
        addMouseWheelListener(e -> {
            double scaleFactor = 1.1;
            int scroll = e.getWheelRotation();
            double oldZoom = zoomLevel;

            if (scroll < 0) {
                zoomLevel *= scaleFactor;
            } else {
                zoomLevel /= scaleFactor;
            }

            // Zapewnienie minimalnego i maksymalnego zoomu
            zoomLevel = Math.max(0.2, Math.min(5.0, zoomLevel));

            // Obliczanie nowego offsetu, aby zoom był względem kursora myszy
            int mouseX = e.getX();
            int mouseY = e.getY();

            offsetX = (int) (mouseX - (mouseX - offsetX) * (zoomLevel / oldZoom));
            offsetY = (int) (mouseY - (mouseY - offsetY) * (zoomLevel / oldZoom));

            repaint();
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Pobranie obrazu z MapVisualizer
        BufferedImage combinedImage = mapVisualizer.createCombinedImage();

        // Skalowanie i przesuwanie całego obrazu
        AffineTransform transform = new AffineTransform();
        transform.translate(offsetX, offsetY);
        transform.scale(zoomLevel, zoomLevel);
        g2d.drawImage(combinedImage, transform, null);
    }

    public void init() {
        JFrame frame = new JFrame("Perlin Noise");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(SIZE, SIZE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(this, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);
    }
}
