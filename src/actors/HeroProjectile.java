package actors;

import utilities.GraphicalShape;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

public class HeroProjectile implements GraphicalShape {
    private static final int WIDTH = 4;
    private static final int HEIGHT = 6;
    private static final double DRAWING_SCALE = 1;
    private static final int DELTA_Y = 5;
    private static final Color COLOR = Color.decode("#F92672");

    private final Area shape;

    public HeroProjectile(Point location){
        this.shape = new Area(new Rectangle(
                location.x, location.y,
                (int)(WIDTH * DRAWING_SCALE), (int)(HEIGHT * DRAWING_SCALE)));
    }

    public void Update(){
        AffineTransform transformation = new AffineTransform();
        transformation.translate(0, - DELTA_Y);
        this.shape.transform(transformation);
    }

    @Override
    public void Paint(Graphics2D graphics) {
        graphics.setColor(COLOR);
        graphics.fill(GetGraphicalShape());
    }

    @Override
    public Area GetGraphicalShape() {
        return this.shape;
    }
}
