package actors;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.Arrays;
import game.*;
import utilities.CollisionalShape;
import utilities.DynamicElement;
import utilities.GraphicalShape;

public class InvaderShip implements GraphicalShape, CollisionalShape, DynamicElement {
    public static final int WIDTH = 110;
    public static final int HEIGHT = 80;
    public static final double DRAWING_SCALE = 0.20;

    private static final int MOVEMENT_COOLDOWN_UPDATE_TIME = 2;
    private static final Color COLOR = Color.decode("#A6E22E");
    private static final double CONSTANT_SPEED_UP = 0.001;

    private Area currentShape;
    private double delta_X = 0.5;
    private boolean willChangeDirectionAfterCooldown = false;
    private long timeUntilNextMoveAvailable = 0;

    public InvaderShip(int row, int column){
        Point location = new Point(
            column * Game.INVADER_COLUMN_WIDTH +  Game.INVADER_WINDOW_MARGIN_LEFT,
            row * Game.INVADER_ROW_HEIGHT + Game.INVADER_WINDOW_MARGIN_TOP);
        this.currentShape = GenerateShape(location);
    }

    public void Update(){
        this.delta_X = this.delta_X + Math.signum(this.delta_X)*CONSTANT_SPEED_UP;
        if(timeUntilNextMoveAvailable <= 0){
            if(this.willChangeDirectionAfterCooldown){
                this.delta_X = - this.delta_X;
                this.willChangeDirectionAfterCooldown = false;
            }

            AffineTransform transform = new AffineTransform();
            transform.translate(delta_X, 0);
            currentShape.transform(transform);
            timeUntilNextMoveAvailable = MOVEMENT_COOLDOWN_UPDATE_TIME + 1;
        }
        timeUntilNextMoveAvailable = timeUntilNextMoveAvailable - 1;
    }

    public void MoveToNextLine(){
        AffineTransform transform = new AffineTransform();
        transform.translate(0, Game.INVADER_NEXT_LINE_HEIGHT);
        currentShape.transform(transform);
    }
    public void ChangeDirectionOfMovement(){
        this.willChangeDirectionAfterCooldown = true;
    }

    public boolean IsGoingToChangeDirection(){
        return this.willChangeDirectionAfterCooldown;
    }

    public Point GetLocation(){
        return new Point((int)currentShape.getBounds2D().getCenterX(), (int)currentShape.getBounds2D().getCenterY());
    }
    @Override
    public void Paint(Graphics2D graphics) {
        graphics.setColor(COLOR);
        Area heroShipDrawingPoints = currentShape;
        graphics.fill(heroShipDrawingPoints);
    }

    @Override
    public Area GetCollisionArea() {
        return currentShape;
    }

    private static Area GenerateShape(Point location){
        Area area = new Area(new Rectangle(
                location.x, location.y,
                (int)(WIDTH*DRAWING_SCALE),(int)(HEIGHT*DRAWING_SCALE)));

        Arrays.stream(new Area[]{
                getSingleShapePeace(location, 30, 40, 10, 10),
                getSingleShapePeace(location, 70, 40, 10, 10),

                getSingleShapePeace(location, 30, 0, 50, 10),
                getSingleShapePeace(location, 40, 10, 30, 10),
                getSingleShapePeace(location, 30, 60, 50, 10),
                getSingleShapePeace(location, 50, 70, 10, 10),

                getSingleShapePeace(location, 0, 0, 20, 10),
                getSingleShapePeace(location, (WIDTH - 20), 0, 20, 10),

                getSingleShapePeace(location, 0, 10, 30, 10),
                getSingleShapePeace(location, (WIDTH - 30), 10, 30, 10),

                getSingleShapePeace(location, 0, 20, 20, 10),
                getSingleShapePeace(location, (WIDTH - 20), 20, 20, 10),

                getSingleShapePeace(location, 0, 30, 10, 10),
                getSingleShapePeace(location, (WIDTH - 10), 30, 10, 10),

                getSingleShapePeace(location, 10, 50, 10, 10),
                getSingleShapePeace(location, (WIDTH - 10 - 10), 50, 10, 10),

                getSingleShapePeace(location, 10, 60, 10, 10),
                getSingleShapePeace(location, (WIDTH - 10 - 10), 60, 10, 10),

                getSingleShapePeace(location, 0, 70, 30, 10),
                getSingleShapePeace(location, (WIDTH - 30), 70, 30, 10),
        }).forEach(area::subtract);

        return area;
    }
    private static Area getSingleShapePeace(Point location, int xPosition, int yPosition, int width, int height){
        return new Area(new Rectangle(
                (int)(xPosition*DRAWING_SCALE) + location.x, (int)(yPosition*DRAWING_SCALE) + location.y,
                (int)(width*DRAWING_SCALE), (int)(height*DRAWING_SCALE)));
    }
}