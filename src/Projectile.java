import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
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
        g2.setColor(Color.red);
        g2.fill(getArea());
    }

    public Area getArea(){
        int width = 10;
        int height = 15;
        double scale = 0.5;
        return new Area(new Rectangle(location.x, location.y, (int)(width * scale), (int)(height * scale)));
    }

    public boolean IsOutsideWindow(){
        return this.getArea().getBounds2D().getMaxY() < 0;
    }
}
