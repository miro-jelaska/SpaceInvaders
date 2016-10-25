import javax.swing.*;
import java.awt.*;

public class HeroShip extends JComponent {
    public void MoveLeft(){
        this.location.setLocation(this.location.getX() - 1, this.location.getY());
    }
    public void MoveRight(){
        this.location.setLocation(this.location.getX() + 1, this.location.getY());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.red);
        int x = (int) this.location.getX() ;
        int y = (int) this.location.getY()+ 350;
        g2.fillPolygon(new int[]{ 0 + x, 10 + x, 10 + x, 40 + x, 40 + x, -40 + x, -40 + x, -10 + x, -10  + x},
                       new int[]{-30 + y, -20 + y, -10 + y, -10 + y, 20 + y, 20 + y, -10 + y, -10 + y, -20 + y},
                       9);
    }

    private Point location = new Point();
}
