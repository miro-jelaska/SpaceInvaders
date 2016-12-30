package vfx;


import utilities.CollisionalShape;
import utilities.DynamicElement;
import utilities.GraphicalShape;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;


public class Explosion implements GraphicalShape, CollisionalShape, DynamicElement {
    private static final Color COLOR = Color.decode("#AE81FF");

    private final int FRAME_RATE = 4;
    private int timeUntilNextFrame = 0;
    private int currentFrameIndex = 0;
    private boolean isFinished = false;
    private final Area[] frames;

    public Explosion(Point location){
        frames = new Area[]{
            getAnimationFrame(0, location),
            getAnimationFrame(1, location),
            getAnimationFrame(2, location),
            getAnimationFrame(3, location),
            getAnimationFrame(4, location),
        };
    }

    public boolean IsFinished(){
        return this.isFinished;
    }

    @Override
    public void Update(){
        this.timeUntilNextFrame = this.timeUntilNextFrame - 1;
        if(this.timeUntilNextFrame <= 0){
            int nextFrameIndex = (currentFrameIndex + 1) % frames.length;
            if(currentFrameIndex > 0 && nextFrameIndex == 0)
                this.isFinished = true;
            else {
                currentFrameIndex = nextFrameIndex;
                timeUntilNextFrame = FRAME_RATE;
            }
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
                    location.x - 2, location.y - 2,
                    4, 4
            )));
        }
        if(frameIndex == 1){
            area.add(new Area(new Rectangle(
                    location.x - 5, location.y - 5,
                    10, 10
            )));
            area.subtract(new Area(new Rectangle(
                    location.x - 2, location.y - 2,
                    4, 4
            )));
            AffineTransform transformation = new AffineTransform();
            Rectangle2D bounds = area.getBounds2D();
            transformation.rotate(1*Math.PI/4, bounds.getCenterX(), bounds.getCenterY());
            area.transform(transformation);
        }
        if(frameIndex == 2){
            area.add(new Area(new Rectangle(
                    location.x - 9, location.y - 9,
                    18, 18
            )));
            area.subtract(new Area(new Rectangle(
                    location.x - 5, location.y - 5,
                    10, 10
            )));
            AffineTransform transformation = new AffineTransform();
            Rectangle2D bounds = area.getBounds2D();
            transformation.rotate(2*Math.PI/4, bounds.getCenterX(), bounds.getCenterY());
            area.transform(transformation);
        }
        if(frameIndex == 3){
            area.add(new Area(new Rectangle(
                    location.x - 10, location.y - 10,
                    20, 20
            )));
            area.subtract(new Area(new Rectangle(
                    location.x - 8, location.y - 8,
                    16, 16
            )));
            AffineTransform transformation = new AffineTransform();
            Rectangle2D bounds = area.getBounds2D();
            transformation.rotate(3*Math.PI/4, bounds.getCenterX(), bounds.getCenterY());
            area.transform(transformation);
        }
        if(frameIndex == 4){
            area.add(new Area(new Rectangle(
                    location.x - 10, location.y - 10,
                    20, 20
            )));
            area.subtract(new Area(new Rectangle(
                    location.x - 9, location.y - 9,
                    18, 18
            )));
        }

        return area;
    }
}
