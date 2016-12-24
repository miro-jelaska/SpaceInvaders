package actors;

import java.awt.*;
import java.awt.geom.Area;


public class Projectile {
    private static final int width = 10;
    private static final int height = 15;
    private static final double scale = 0.5;
    private static int deltaY = 5;

    public Projectile(Point location){
        this.location = location;
    }
    private Point location = new Point();

    public void Update(){
        this.location.setLocation(this.location.getX(), this.location.getY() - deltaY);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.red);
        g2.fill(getArea());
    }

    public Area getArea(){
        return new Area(new Rectangle(
            location.x, location.y,
            (int)(width * scale), (int)(height * scale)));
    }

    public boolean IsOutsideWindow(){
        return this.getArea().getBounds2D().getMaxY() < 0;
    }
}
