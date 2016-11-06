import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.awt.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;

public class HeroShip {
    private Point location = new Point(150, 475);
    private double drawingScale = 0.5;

    public void MoveLeft(){
        this.location.setLocation(this.location.getX() - 10, this.location.getY());
    }
    public void MoveRight(){
        this.location.setLocation(this.location.getX() + 10, this.location.getY());
    }

    public Projectile Shoot(){
        try {
            InputStream in = new FileInputStream("src/resources/laser.wav");
            AudioStream audioStream = new AudioStream(in);
            AudioPlayer.player.start(audioStream);
        }
        catch (Exception e){
            System.out.println("Sound error");
        }


        return new Projectile(new Point((int)location.getX(), (int)(location.getY() - 60 * drawingScale)));
    }


    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.white);
        Point[] heroShipDrawingPoints = getShapePoints();
        g2.fillPolygon(
                Arrays.stream(heroShipDrawingPoints).mapToInt(point -> (int)(point.getX() * drawingScale) + location.x).toArray(),
                Arrays.stream(heroShipDrawingPoints).mapToInt(point -> (int)(point.getY() * drawingScale) + location.y).toArray(),
                heroShipDrawingPoints.length);
    }

    private static Point[] getShapePoints(){
        return new Point[]{
                new Point(-10, -60),
                new Point(-10, -70),
                new Point(-30, -70),
                new Point(-30, -50),
                new Point(-20, -50),
                new Point(-20, -30),
                new Point(-40, -30),
                new Point(-40, -20 ),
                new Point(-50, -20),
                new Point(-50, -10),
                new Point(-40, -10),
                new Point(-40, 0),
                new Point(40, 0),
                new Point(40, -10),
                new Point(50, -10),
                new Point(50, -20),
                new Point(40, -20),
                new Point(40, -30),
                new Point(20, -30),
                new Point(20, -50),
                new Point(30, -50),
                new Point(30, -70),
                new Point(10, -70),
                new Point(10, -60)

        };
    }
}
