package actors;

import utilities.CollisionalShape;
import utilities.DynamicElement;
import utilities.GraphicalShape;
import java.awt.*;
import java.awt.geom.*;

public class InvaderProjectile implements GraphicalShape, CollisionalShape, DynamicElement {
    private static final int WIDTH = 8;
    private static final int HEIGHT = 20;
    private static final double DRAWING_SCALE = 1;
    private static final int DELTA_Y = 5;
    private static final Color COLOR = Color.decode("#AE81FF");
    private final Point location;

    private final int FRAME_RATE = 2;
    private int timeUntilNextFrame = 0;
    private int currentFrameIndex = 0;
    private final Area[] frames;

    public InvaderProjectile(Point location){
        this.location = location;
        frames = new Area[]{
            getAnimationFrame(0, location),
            getAnimationFrame(1, location)
        };
    }

    @Override
    public void Update(){
        this.location.setLocation(this.location.getX(), this.location.getY() + DELTA_Y);
        this.timeUntilNextFrame = this.timeUntilNextFrame - 1;
        if(this.timeUntilNextFrame <= 0){
            currentFrameIndex = (currentFrameIndex + 1) % frames.length;
            timeUntilNextFrame = FRAME_RATE;
        }
        for (Area area : frames) {
            AffineTransform transformation = new AffineTransform();
            transformation.translate(0, DELTA_Y);
            area.transform(transformation);
        }
    }

    @Override
    public void Paint(Graphics2D graphics) {
        graphics.setColor(COLOR);
        graphics.fill(frames[currentFrameIndex]);
    }

    @Override
    public Area GetCollisionArea() {
        return frames[currentFrameIndex];
    }

    private static Area getAnimationFrame(int frameIndex, Point location){
        Area area = new Area();

        if(frameIndex == 0){
            area.add(new Area(new Rectangle(
                location.x, location.y + 2,
                1, (int)(HEIGHT*DRAWING_SCALE) - 4
            )));

            area.add(new Area(new Rectangle(
                location.x + 2, location.y,
                1, (int)(HEIGHT* DRAWING_SCALE)
            )));
            area.add(new Area(new Rectangle(
                    location.x + 4, location.y,
                    1, (int)(HEIGHT* DRAWING_SCALE)
            )));
        }

        if(frameIndex == 1){
            area.add(new Area(new Rectangle(
                    location.x + 3, location.y,
                    1, (int)(HEIGHT* DRAWING_SCALE)
            )));
            area.add(new Area(new Rectangle(
                    location.x + 5, location.y,
                    1, (int)(HEIGHT* DRAWING_SCALE)
            )));
            area.add(new Area(new Rectangle(
                    location.x + 7, location.y + 2,
                    1, (int)(HEIGHT* DRAWING_SCALE) - 4
            )));
        }

        return area;
    }
}
