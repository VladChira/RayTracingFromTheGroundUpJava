import RayTracer.Cameras.Pinhole;
import RayTracer.Utilities.RGBColor;
import RayTracer.World;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.BorderPane;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(World.WINDOW_WIDTH, World.WINDOW_HEIGHT);

        stage.setTitle("Ray Tracer");
        stage.setScene(new Scene(new BorderPane(canvas)));
        stage.show();

        //TODO Render into a BufferedImage instead

        GraphicsContext gc = canvas.getGraphicsContext2D();
        PixelWriter pw = gc.getPixelWriter();

        World w = new World();
        w.build(RGBColor.black);

        Pinhole pinhole = new Pinhole();
        pinhole.set_eye(0, 0, 500);
        pinhole.set_lookat(5, 0, 0);
        pinhole.set_view_distance(850.0);
        pinhole.set_zoom(2);
        pinhole.compute_uvw();


        System.out.println("Rendering...");
        long startTime = System.currentTimeMillis();

        pinhole.render_scene(w, pw);

        if (w.wasOutOfGamut) System.out.println("Warning. Out of gamut colors");
        System.out.print("Render Finished. Time elapsed: ");
        System.out.print(System.currentTimeMillis() - startTime);
        System.out.println(" milliseconds");
    }
}