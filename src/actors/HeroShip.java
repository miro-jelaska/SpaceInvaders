package actors;

import collision.CollisionDetection;
import events.EventResolution;
import events.commands.HeroShipShoot;
import utilities.*;
import game.Game;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

public class HeroShip implements GraphicalShape, DynamicElement {
    public static final int WIDTH = 100;
    public static final int HEIGHT = 80;
    public static final double DRAWING_SCALE = 0.5;
    private static final int DELTA_X = 5;
    private static final int SHOOT_COOLDOWN_UPDATE_TIME = 30;

    private final EventResolution eventResolution;
    private final Area currentShape;
    private long timeUntilShootingAvailable = 0;

    public HeroShip(EventResolution eventResolution) {
        Point location = new Point((int)(Game.CANVAS_WIDTH/2 - (WIDTH/2 * DRAWING_SCALE)), Game.CANVAS_HEIGHT - HEIGHT);
        this.eventResolution = eventResolution;
        this.currentShape = generateShape(location);
    }

    public void MoveLeft(){
        AffineTransform transform = new AffineTransform();
        if(CollisionDetection.IsShapeAtEdge_Left(this))
            transform.translate(1 - this.currentShape.getBounds2D().getMinX(), 0);
        else
            transform.translate(- DELTA_X, 0);
        currentShape.transform(transform);
    }
    public void MoveRight(){
        AffineTransform transform = new AffineTransform();
        if(CollisionDetection.IsShapeAtEdge_Right(this))
            transform.translate(Game.CANVAS_WIDTH - this.currentShape.getBounds2D().getMaxX() - 1, 0);
        else
            transform.translate(DELTA_X, 0);
        currentShape.transform(transform);
    }

    public void Shoot(){
        if(timeUntilShootingAvailable <= 0){
            Rectangle2D shipBounds = this.currentShape.getBounds2D();
            Point projectileLocation = new Point((int)(shipBounds.getMinX()), (int)shipBounds.getMinY());
            eventResolution.Push(new HeroShipShoot(projectileLocation));
            timeUntilShootingAvailable = SHOOT_COOLDOWN_UPDATE_TIME;
        }
    }

    @Override
    public void Update(){
        timeUntilShootingAvailable = timeUntilShootingAvailable - 1;
    }

    @Override
    public void Paint(Graphics2D graphics){
        graphics.setColor(Color.white);
        Area heroShipDrawingShape = GetGraphicalShape();
        graphics.fill(heroShipDrawingShape);
    }

    @Override
    public Area GetGraphicalShape() {
        return currentShape;
    }

    private static Area generateShape(Point location){
        Area area = new Area(new Rectangle(
                location.x, location.y,
                (int)(WIDTH*DRAWING_SCALE),(int)(HEIGHT*DRAWING_SCALE)));

        Arrays.stream(new Area[]{
                getSingleShapePeace(location, 40, 0, 20, 10),

                getSingleShapePeace(location, 0, 0, 20, 20),
                getSingleShapePeace(location, (WIDTH - 20), 0, 20, 20),

                getSingleShapePeace(location, 0, 6, 25, 2),
                getSingleShapePeace(location, (WIDTH - 25), 6, 25, 2),
                getSingleShapePeace(location, 0, 10, 25, 2),
                getSingleShapePeace(location, (WIDTH - 25), 10, 25, 2),
                getSingleShapePeace(location, 0, 14, 25, 2),
                getSingleShapePeace(location, (WIDTH - 25), 14, 25, 2),

                getSingleShapePeace(location, 0, 20, 30, 20),
                getSingleShapePeace(location, (WIDTH - 30), 20, 30, 20),
                getSingleShapePeace(location, 0, 40, 20, 10),
                getSingleShapePeace(location, (WIDTH - 20), 40, 20, 10),
                getSingleShapePeace(location, 0, 50, 10, 10),
                getSingleShapePeace(location, (WIDTH - 10), 50, 10, 10),

                getSingleShapePeace(location, 0, 76, 16, 5),
                getSingleShapePeace(location, (WIDTH - 16), 76, 16, 5),
                getSingleShapePeace(location, 40, 76, 20, 8),
        }).forEach(area::subtract);

        return area;
    }
    private static Area getSingleShapePeace(Point location, int xPosition, int yPosition, int width, int height){
        return new Area(new Rectangle(
                (int)(xPosition*DRAWING_SCALE) + location.x, (int)(yPosition*DRAWING_SCALE) + location.y,
                (int)(width*DRAWING_SCALE), (int)(height*DRAWING_SCALE)));
    }
}
