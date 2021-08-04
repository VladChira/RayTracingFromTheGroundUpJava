package GUI;

import RayTracer.SceneManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Viewport extends JPanel {

    private BufferedImage frameBuffer;

    public Viewport(JFrame frame, SceneManager sm) {
        sm.buildWorld();
        sm.renderWorld();
        frameBuffer = new BufferedImage(SceneManager.WINDOW_SIZE, SceneManager.WINDOW_SIZE, BufferedImage.TYPE_INT_RGB);
    }

    public void runMainLoop() {
        while (true) {
            //while(true) is temporary
            frameBuffer = SceneManager.render;
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(frameBuffer, 0, 0, this);
    }

}
