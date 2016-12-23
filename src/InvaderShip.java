import java.awt.*;
import java.awt.geom.Area;

public class InvaderShip {
    private static final double drawingScale = 0.30;
    private static final int deltaForXMovement = 10;
    private static int currentColumn = 2;
    private static int currentRow = 2;

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.green);
        Area heroShipDrawingPoints = getShapePoints();
        g2.fill(heroShipDrawingPoints);
    }

    private Area getShapePoints(){
        int xTranslation = currentColumn*Game.INVADER_COLUMN_WIDTH;
        int yTranslation = currentRow*Game.INVADER_ROW_HEIGHT;
        Area a = new Area(new Rectangle(xTranslation, yTranslation, (int)(110*drawingScale),(int)(80*drawingScale)));
        Area leftEye = new Area(new Rectangle(
            (int)(30*drawingScale) + xTranslation, (int)(40*drawingScale) + yTranslation,
            (int)(10*drawingScale), (int)(10*drawingScale)));
        Area rightEye = new Area(new Rectangle(
            (int)(70*drawingScale) + xTranslation, (int)(40*drawingScale) + yTranslation,
            (int)(10*drawingScale),(int)(10*drawingScale)));
        a.subtract(leftEye);
        a.subtract(rightEye);
        a.subtract(getSingleShapePeace(30, 0, 50, 10));
        a.subtract(getSingleShapePeace(40, 10, 30, 10));
        a.subtract(getSingleShapePeace(30, 60, 50, 10));
        a.subtract(getSingleShapePeace(50, 70, 10, 10));

        a.subtract(getSingleShapePeace(0, 0, 20, 10));
        a.subtract(getSingleShapePeace((110 - 20), 0, 20, 10));

        a.subtract(getSingleShapePeace(0, 10, 30, 10));
        a.subtract(getSingleShapePeace((110 - 30), 10, 30, 10));

        a.subtract(getSingleShapePeace(0, 20, 20, 10));
        a.subtract(getSingleShapePeace((110 - 20), 20, 20, 10));

        a.subtract(getSingleShapePeace(0, 30, 10, 10));
        a.subtract(getSingleShapePeace((110 - 10), 30, 10, 10));

        a.subtract(getSingleShapePeace(10, 50, 10, 10));
        a.subtract(getSingleShapePeace((110 - 10 - 10), 50, 10, 10));

        a.subtract(getSingleShapePeace(10, 60, 10, 10));
        a.subtract(getSingleShapePeace((110 - 10 - 10), 60, 10, 10));

        a.subtract(getSingleShapePeace(0, 70, 30, 10));
        a.subtract(getSingleShapePeace((110 - 30), 70, 30, 10));
        return a;
    }
    private Area getSingleShapePeace(int xPosition, int yPosition, int width, int height){
        int xTranslation = currentColumn*Game.INVADER_COLUMN_WIDTH;
        int yTranslation = currentRow*Game.INVADER_ROW_HEIGHT;

        return new Area(new Rectangle(
                (int)(xPosition*drawingScale) + xTranslation, (int)(yPosition*drawingScale) + yTranslation,
                (int)(width*drawingScale), (int)(height*drawingScale)));
    }
}