package actors;

import utilities.GraphicalShape;

import java.awt.*;
import java.awt.geom.Area;

public class HeroProjectile implements GraphicalShape {
    private static final int WIDTH = 4;
    private static final int HEIGHT = 6;
    private static final double DRAWING_SCALE = 1;
    private static final int DELTA_Y = 5;
    private final Point location;

    public HeroProjectile(Point location){
        this.location = location;
    }

    public void Update(){
        this.location.setLocation(this.location.getX(), this.location.getY() - DELTA_Y);
    }

    @Override
    public void Paint(Graphics2D graphics) {
        graphics.setColor(Color.red);
        graphics.fill(GetGraphicalShape());
    }

    @Override
    public Area GetGraphicalShape() {
        return new Area(new Rectangle(
                location.x, location.y,
                (int)(WIDTH * DRAWING_SCALE), (int)(HEIGHT * DRAWING_SCALE)));
    }
}
