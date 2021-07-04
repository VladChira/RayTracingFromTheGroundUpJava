import RayTracer.Utilities.RGBColor;
import RayTracer.World;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Viewport extends JPanel {

    public World w;

    private BufferedImage frameBuffer = null;

    Viewport(JFrame frame) {
        w = new World();
        w.build(RGBColor.black);
    }


    public void runMainLoop() {
        //not a loop yet, we only render once
        long startTime = System.currentTimeMillis();
        System.out.println("Rendering...");
        frameBuffer = w.renderScene();
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("Render finished. Elapsed time: " + elapsedTime/1000.0 + " seconds");
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(frameBuffer, 0, 0, this);
    }
}
