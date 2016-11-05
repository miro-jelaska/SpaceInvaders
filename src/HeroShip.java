import java.awt.*;
import java.util.Arrays;

public class HeroShip {
    private Point location = new Point(150, 375);

    public void MoveLeft(){
        this.location.setLocation(this.location.getX() - 10, this.location.getY());
    }
    public void MoveRight(){
        this.location.setLocation(this.location.getX() + 10, this.location.getY());
    }

    public Projectile Shoot(){
        return new Projectile(new Point(location));
    }


    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.red);
        double scale = 0.5;
        Point[] heroShipDrawingPoints = getShapePoints();
        g2.fillPolygon(
                Arrays.stream(heroShipDrawingPoints).mapToInt(point -> (int)(point.getX() * scale) + location.x).toArray(),
                Arrays.stream(heroShipDrawingPoints).mapToInt(point -> (int)(point.getY() * scale) + location.y).toArray(),
                heroShipDrawingPoints.length);
    }

    private static Point[] getShapePoints(){
        return new Point[]{
                new Point(  0, -30),
                new Point( 10, -20),
                new Point( 10, -10),
                new Point( 40, -10),
                new Point( 40,  20),
                new Point(-40,  20),
                new Point(-40, -10),
                new Point(-10, -10),
                new Point(-10, -20),
        };
    }
}
