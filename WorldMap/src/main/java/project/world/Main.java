package project.world;

import project.world.mapCreator.Map;
import project.world.visualizer.PanelVisualizer;

public class Main {
    public static void main(String[] args) {
        PanelVisualizer visualizer = new PanelVisualizer(new Map());
        visualizer.init();
    }
}
