package actors;

import java.awt.*;
import java.awt.geom.Area;
import java.util.Arrays;
import game.*;
import utilities.GraphicalShape;

public class InvaderShip implements GraphicalShape {
    private static final double DRAWING_SCALE = 0.20;
    private static final int MOVEMENT_COOLDOWN_UPDATE_TIME = 5;
    private static final int WIDTH = 110;
    private static final int HEIGHT = 80;
    private int delta_X = 1;
    private long lastTimeShoot = 0;
    private Point location;

    public InvaderShip(int row, int column){
        location = new Point(
            column * Game.INVADER_COLUMN_WIDTH,
            row * Game.INVADER_ROW_HEIGHT + Game.INVADER_WINDOW_MARGIN_TOP);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.green);
        Area heroShipDrawingPoints = GetGraphicalShape();
        g2.fill(heroShipDrawingPoints);
    }

    public void Update(){
        if((Game.GetCurrentUpateCount() - lastTimeShoot) < MOVEMENT_COOLDOWN_UPDATE_TIME)
            return;

        lastTimeShoot = Game.GetCurrentUpateCount();
        location.setLocation(location.getX() + delta_X, location.getY());
    }

    public void MoveToNextLine(){
        this.location.setLocation(this.location.getX(), this.location.getY() + Game.INVADER_NEXT_LINE_HEIGHT);
    }
    public void ChangeDirectionOfMovement(){
        this.delta_X = - delta_X;
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