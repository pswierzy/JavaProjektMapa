package project.world.visualizer;

import project.world.simulation.SimulationParameters;

import javax.swing.*;
import java.awt.*;

import static project.world.visualizer.PanelVisualizer.SIZE;

public class ParametersPanel extends JPanel {
    private final JSpinner mapSizeSpinner;
    private final JCheckBox altitudeLinesBox;
    private final JCheckBox gridLinesBox;
    private final JSpinner simulationSpeedSpinner;
    private final JSpinner amountOfCreaturesSpinner;
    private SimulationParameters parameters = new SimulationParameters();

    /*
    SIZE
    altitudeLines
    gridLines
    speed
    amountOfCreatures
     */

    public ParametersPanel(int initialSize) {
        setLayout(new GridLayout(6, 2));
        setBorder(BorderFactory.createTitledBorder("Parametry Symulacji"));

        add(new JLabel("Rozmiar mapy:"));
        mapSizeSpinner = new JSpinner(new SpinnerNumberModel(initialSize, 100, 2000, 50));
        add(mapSizeSpinner);

        add(new JLabel("Linie wysokości:"));
        altitudeLinesBox = new JCheckBox();
        altitudeLinesBox.setSelected(true);
        add(altitudeLinesBox);

        add(new JLabel("Siatka mapy:"));
        gridLinesBox = new JCheckBox();
        gridLinesBox.setSelected(true);
        add(gridLinesBox);

        add(new JLabel("Prędkość symulacji:"));
        simulationSpeedSpinner = new JSpinner(new SpinnerNumberModel(parameters.getSpeed(), 1, 10, 1));
        add(simulationSpeedSpinner);

        add(new JLabel("Ilość stworzeń na start:"));
        amountOfCreaturesSpinner = new JSpinner(new SpinnerNumberModel(parameters.getAmountOfCreatures(), 1, 100, 1));
        add(amountOfCreaturesSpinner);
    }

    public void applyParameters() {
        parameters.setSpeed((double) simulationSpeedSpinner.getValue());
        parameters.setAmountOfCreatures((int) amountOfCreaturesSpinner.getValue());
        SIZE = (int) mapSizeSpinner.getValue();
    }

    public boolean getAltitudeLines() {
        return altitudeLinesBox.isSelected();
    }
    public boolean getGridLines() {
        return gridLinesBox.isSelected();
    }

    public SimulationParameters getParameters() {
        applyParameters();
        return parameters;
    }
}
