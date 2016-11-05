import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Projectile extends JComponent {
    public Projectile(Point location){
        this.location = location;
    }
    private Point location = new Point();

    public void Update(){
        int delta = 5;
        this.location.setLocation(this.location.getX(), this.location.getY() - delta);
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.blue);
        double scale = 0.2;
        Point[] projectalDrawingPoints = getShapePoints();
        g2.fillPolygon(
                Arrays.stream(projectalDrawingPoints).mapToInt(point -> (int)(point.getX() * scale) + location.x).toArray(),
                Arrays.stream(projectalDrawingPoints).mapToInt(point -> (int)(point.getY() * scale) + location.y).toArray(),
                projectalDrawingPoints.length);
    }

    private static Point[] getShapePoints(){
        return new Point[]{
                new Point( -10,  0),
                new Point( 10,   0),
                new Point( 10, -20),
                new Point(-10, -20),
        };
    }
}
