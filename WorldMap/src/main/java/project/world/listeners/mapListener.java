package project.world.listeners;

import project.world.visualizer.PanelVisualizer;

public class mapListener {
    private final PanelVisualizer visualizer;

    public mapListener(PanelVisualizer visualizer) {
        this.visualizer = visualizer;
    }
    public void mapChanged() {
        visualizer.updateMap();
    }
}
