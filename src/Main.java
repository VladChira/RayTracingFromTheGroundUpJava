import GUI.SettingsPanel;
import GUI.Viewport;
import RayTracer.World;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Ray Tracer");
        frame.setSize(World.WINDOW_SIZE, World.WINDOW_SIZE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

        Viewport viewport = new Viewport(frame);
        frame.add(viewport);
        frame.setVisible(true);

        //Coming Soon

        //JDialog settingsDialog = new JDialog(frame, "Settings");
        //SettingsPanel settingsPanel = new SettingsPanel(viewport);
        //settingsDialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        //settingsDialog.setSize(200, 200);
        //settingsDialog.add(settingsPanel);
        //settingsDialog.setVisible(true);

        viewport.runMainLoop();
    }
}