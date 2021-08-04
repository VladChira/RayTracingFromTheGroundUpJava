import GUI.Viewport;
import RayTracer.Materials.Matte;
import RayTracer.Objects.Sphere;
import RayTracer.SceneManager;
import RayTracer.Utilities.Point3D;
import RayTracer.Utilities.RGBColor;

import javax.swing.*;

public class Main {

    public static final int num_samples = 64;

    public static void main(String[] args) {
        SceneManager sm = new SceneManager(num_samples);
        //sm.singleSphereSetup();
        //sm.manySpheresSetup();
        sm.areaLightSetup();
        //sm.cornellBoxSetup();

        setupGUI(sm);
    }

    public static void setupGUI(SceneManager sm) {
        JFrame frame = new JFrame("Ray Tracer");
        frame.setSize(SceneManager.WINDOW_SIZE, SceneManager.WINDOW_SIZE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(700, 70);

        Viewport viewport = new Viewport(frame, sm);
        frame.add(viewport);
        frame.setVisible(true);

        //Coming Soon

//        JDialog settingsDialog = new JDialog(frame, "Settings");
//        settingsDialog.setLocation(100,70);
//        SettingsPanel settingsPanel = new SettingsPanel(viewport);
//        settingsDialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
//        settingsDialog.setSize(500, 500);
//        settingsDialog.add(settingsPanel);
//        settingsDialog.setVisible(true);

        viewport.runMainLoop();
    }
}