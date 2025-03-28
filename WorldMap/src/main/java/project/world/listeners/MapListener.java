package project.world.listeners;

import project.world.visualizer.PanelVisualizer;

public class MapListener {
    private final PanelVisualizer visualizer;

    public MapListener(PanelVisualizer visualizer) {
        this.visualizer = visualizer;
    }
    public void mapChanged() {
        visualizer.updateMap();
    }
}
