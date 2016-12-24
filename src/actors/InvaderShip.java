package actors;

import java.awt.*;
import java.awt.geom.Area;
import java.util.Arrays;
import game.*;

public class InvaderShip {
    private static final double drawingScale = 0.20;
    private static final int deltaForXMovement = 1;
    private static final int movementSpeed = 10;
    private long lastTimeShoot = 0;
    private Point location;

    public InvaderShip(int row, int column){
        location = new Point(column * Game.INVADER_COLUMN_WIDTH, row * Game.INVADER_ROW_HEIGHT + Game.INVADER_WINDOW_MARGIN_TOP);
    }

    public boolean IsHitByProjectile(Projectile projectile){
        Area shipArea = this.getShape();
        shipArea.intersect(projectile.getArea());
        return !shipArea.isEmpty();
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.green);
        Area heroShipDrawingPoints = getShape();
        g2.fill(heroShipDrawingPoints);
    }

    public void Update(){
        if((Game.GetCurrentUpateCount() - lastTimeShoot) < movementSpeed)
            return;

        lastTimeShoot = Game.GetCurrentUpateCount();
        location.setLocation(location.getX() + deltaForXMovement, location.getY());
    }

    private Area getShape(){

        Area area = new Area(new Rectangle(
            this.location.x, this.location.y,
            (int)(110*drawingScale),(int)(80*drawingScale)));

        Arrays.stream(new Area[]{
            getSingleShapePeace(30, 40, 10, 10),
            getSingleShapePeace(70, 40, 10, 10),

            getSingleShapePeace(30, 0, 50, 10),
            getSingleShapePeace(40, 10, 30, 10),
            getSingleShapePeace(30, 60, 50, 10),
            getSingleShapePeace(50, 70, 10, 10),

            getSingleShapePeace(0, 0, 20, 10),
            getSingleShapePeace((110 - 20), 0, 20, 10),

            getSingleShapePeace(0, 10, 30, 10),
            getSingleShapePeace((110 - 30), 10, 30, 10),

            getSingleShapePeace(0, 20, 20, 10),
            getSingleShapePeace((110 - 20), 20, 20, 10),

            getSingleShapePeace(0, 30, 10, 10),
            getSingleShapePeace((110 - 10), 30, 10, 10),

            getSingleShapePeace(10, 50, 10, 10),
            getSingleShapePeace((110 - 10 - 10), 50, 10, 10),

            getSingleShapePeace(10, 60, 10, 10),
            getSingleShapePeace((110 - 10 - 10), 60, 10, 10),

            getSingleShapePeace(0, 70, 30, 10),
            getSingleShapePeace((110 - 30), 70, 30, 10),
        }).forEach(area::subtract);

        return area;
    }
    private Area getSingleShapePeace(int xPosition, int yPosition, int width, int height){
        return new Area(new Rectangle(
                (int)(xPosition*drawingScale) + this.location.x, (int)(yPosition*drawingScale) + this.location.y,
                (int)(width*drawingScale), (int)(height*drawingScale)));
    }
}