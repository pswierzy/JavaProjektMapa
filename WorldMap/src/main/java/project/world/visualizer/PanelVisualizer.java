package project.world.visualizer;

import project.world.listeners.MapListener;
import project.world.mapGenerator.Map;
import project.world.simulation.SimulationParameters;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class PanelVisualizer extends JPanel {

    public static int SIZE = 800;
    private MapVisualizer mapVisualizer;
    private double zoomLevel = 1.0;
    private int offsetX = 0, offsetY = 0;
    private int lastMouseX, lastMouseY;

    private JButton simulationButton;
    private boolean isSimulationRunning = false;

    private ParametersPanel paramsPanel;

    public PanelVisualizer() {
        this(new Map());
    }

    public PanelVisualizer(Map map) {
        map.addListener(new MapListener(this));
        this.mapVisualizer = new MapVisualizer(map);
        addMapControlListeners();
    }

    private void addMapControlListeners() {
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

        addMouseWheelListener(e -> {
            double scaleFactor = 1.1;
            int scroll = e.getWheelRotation();
            double oldZoom = zoomLevel;

            if (scroll < 0) {
                zoomLevel *= scaleFactor;
            } else {
                zoomLevel /= scaleFactor;
            }

            zoomLevel = Math.max(0.5, Math.min(5.0, zoomLevel));

            int mouseX = e.getX();
            int mouseY = e.getY();

            offsetX = (int) (mouseX - (mouseX - offsetX) * (zoomLevel / oldZoom));
            offsetY = (int) (mouseY - (mouseY - offsetY) * (zoomLevel / oldZoom));

            repaint();
        });
    }

    private void createNewMap() {
        mapVisualizer.map().stopSimulation();
        Map map = new Map(paramsPanel.getAltitudeLines(), paramsPanel.getGridLines(), paramsPanel.getParameters());
        map.addListener(new MapListener(this));
        mapVisualizer = new MapVisualizer(map);
        isSimulationRunning = false;
        simulationButton.setText("ZACZNIJ SYMULACJĘ");
        repaint();
    }

    public void updateMap() {
        repaint();
    }

    private void toggleSimulation() {
        if (isSimulationRunning) {
            mapVisualizer.map().stopSimulation();
            simulationButton.setText("ZACZNIJ SYMULACJĘ");
        } else {
            mapVisualizer.map().startSimulation();
            simulationButton.setText("ZATRZYMAJ SYMULACJĘ");
        }
        isSimulationRunning = !isSimulationRunning;
    }

    private void downloadMap() {
        BufferedImage image = mapVisualizer.map().getMapImage();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Zapisz mapę jako PNG");
        fileChooser.setSelectedFile(new File("mapa.png"));

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            try {
                ImageIO.write(image, "png", fileToSave);
                JOptionPane.showMessageDialog(this, "Mapa zapisana: " + fileToSave.getAbsolutePath());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Błąd zapisu pliku!", "Błąd", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void addButtons(JPanel panel) {
        JButton newMapButton = new JButton("NOWA MAPA");
        simulationButton = new JButton("ZACZNIJ SYMULACJĘ");
        JButton downloadButton = new JButton("POBIERZ MAPĘ");

        panel.add(newMapButton);
        newMapButton.addActionListener(e -> createNewMap());

        panel.add(simulationButton);
        simulationButton.addActionListener(e -> toggleSimulation());

        panel.add(downloadButton);
        downloadButton.addActionListener(e -> downloadMap());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        BufferedImage combinedImage = mapVisualizer.createCombinedImage();

        AffineTransform transform = new AffineTransform();
        transform.translate(offsetX, offsetY);
        transform.scale(zoomLevel, zoomLevel);
        g2d.drawImage(combinedImage, transform, null);
    }

    public void init() {
        JFrame frame = new JFrame("Perlin Noise");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel wizualizacji
        this.setPreferredSize(new Dimension(SIZE, SIZE));
        mainPanel.add(this, BorderLayout.CENTER);

        // Panel boczny
        JPanel sidePanel = new JPanel();
        paramsPanel = new ParametersPanel(800);
        sidePanel.add(paramsPanel);


        sidePanel.setPreferredSize(new Dimension(300, SIZE));
        sidePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mainPanel.add(sidePanel, BorderLayout.EAST);

        // Panel dolny z przyciskami
        JPanel bottomPanel = new JPanel();
        addButtons(bottomPanel);
        bottomPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }
}
