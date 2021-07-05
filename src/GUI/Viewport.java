package GUI;

import RayTracer.Utilities.RGBColor;
import RayTracer.World;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Viewport extends JPanel {

    public World w;

    private BufferedImage frameBuffer = null;

    public Viewport(JFrame frame) {
        w = new World();
        w.build(RGBColor.black);
        frameBuffer = new BufferedImage(World.WINDOW_WIDTH, World.WINDOW_HEIGHT,BufferedImage.TYPE_INT_RGB);
    }

    public void runMainLoop() {
        w.renderScene();
        while (true) {
            //while(true) is temporary
            frameBuffer = World.render;
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(frameBuffer, 0, 0, this);
    }
}
