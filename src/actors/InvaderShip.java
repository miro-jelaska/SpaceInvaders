package actors;

import java.awt.*;
import java.awt.geom.Area;
import java.util.Arrays;
import game.*;
import utilities.GraphicalShape;

public class InvaderShip implements GraphicalShape {
    public static final int WIDTH = 110;
    public static final int HEIGHT = 80;
    public static final double DRAWING_SCALE = 0.20;

    private static final int MOVEMENT_COOLDOWN_UPDATE_TIME = 5;

    private int delta_X = 1;
    private long lastTimeMove = 0;
    private Point location;
    private boolean willChangeDirectionAfterCooldown = false;

    public InvaderShip(int row, int column){
        this.location = new Point(
            column * Game.INVADER_COLUMN_WIDTH +  Game.INVADER_WINDOW_MARGIN_LEFT,
            row * Game.INVADER_ROW_HEIGHT + Game.INVADER_WINDOW_MARGIN_TOP);
    }

    public void Update(){
        if(IsInMovementCooldown()){
            if(this.willChangeDirectionAfterCooldown){
                this.delta_X = - this.delta_X;
                this.willChangeDirectionAfterCooldown = false;
            }
            location.setLocation(location.getX() + delta_X, location.getY());
            lastTimeMove = Game.GetCurrentUpateCount();
        }
    }

    public void MoveToNextLine(){
        this.location.setLocation(this.location.getX(), this.location.getY() + Game.INVADER_NEXT_LINE_HEIGHT);
    }
    public void ChangeDirectionOfMovement(){
        this.willChangeDirectionAfterCooldown = true;
    }

    public boolean IsInMovementCooldown(){
        return (Game.GetCurrentUpateCount() - lastTimeMove) >= MOVEMENT_COOLDOWN_UPDATE_TIME;
    }
    public boolean IsGoingToChangeDirection(){
        return this.willChangeDirectionAfterCooldown;
    }

    @Override
    public void Paint(Graphics2D graphics) {
        graphics.setColor(Color.green);
        Area heroShipDrawingPoints = GetGraphicalShape();
        graphics.fill(heroShipDrawingPoints);
    }

    @Override
    public Area GetGraphicalShape() {
        Area area = new Area(new Rectangle(
                this.location.x, this.location.y,
                (int)(WIDTH*DRAWING_SCALE),(int)(HEIGHT*DRAWING_SCALE)));

        Arrays.stream(new Area[]{
                getSingleShapePeace(30, 40, 10, 10),
                getSingleShapePeace(70, 40, 10, 10),

                getSingleShapePeace(30, 0, 50, 10),
                getSingleShapePeace(40, 10, 30, 10),
                getSingleShapePeace(30, 60, 50, 10),
                getSingleShapePeace(50, 70, 10, 10),

                getSingleShapePeace(0, 0, 20, 10),
                getSingleShapePeace((WIDTH - 20), 0, 20, 10),

                getSingleShapePeace(0, 10, 30, 10),
                getSingleShapePeace((WIDTH - 30), 10, 30, 10),

                getSingleShapePeace(0, 20, 20, 10),
                getSingleShapePeace((WIDTH - 20), 20, 20, 10),

                getSingleShapePeace(0, 30, 10, 10),
                getSingleShapePeace((WIDTH - 10), 30, 10, 10),

                getSingleShapePeace(10, 50, 10, 10),
                getSingleShapePeace((WIDTH - 10 - 10), 50, 10, 10),

                getSingleShapePeace(10, 60, 10, 10),
                getSingleShapePeace((WIDTH - 10 - 10), 60, 10, 10),

                getSingleShapePeace(0, 70, 30, 10),
                getSingleShapePeace((WIDTH - 30), 70, 30, 10),
        }).forEach(area::subtract);

        return area;
    }
    private Area getSingleShapePeace(int xPosition, int yPosition, int width, int height){
        return new Area(new Rectangle(
                (int)(xPosition*DRAWING_SCALE) + this.location.x, (int)(yPosition*DRAWING_SCALE) + this.location.y,
                (int)(width*DRAWING_SCALE), (int)(height*DRAWING_SCALE)));
    }
}