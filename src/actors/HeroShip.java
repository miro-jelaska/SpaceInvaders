package actors;

import game.Game;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import game.*;


import java.awt.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;

public class HeroShip {
    private Point location = new Point(Game.CANVAS_WIDTH / 2, Game.CANVAS_HEIGHT - 30);
    private static final double drawingScale = 0.5;
    private static final int deltaForXMovement = 5;
    private static final int shootCooldownUpdateTime = 30;

    public void MoveLeft(){
        boolean isOnTheLeftEdgeOfCanvas = this.getPolygon().getBounds2D().getMinX() <= 0;
        if(isOnTheLeftEdgeOfCanvas)
            this.location.setLocation(this.getPolygon().getBounds2D().getWidth()*drawingScale, this.location.getY());
        else
            this.location.setLocation(this.location.getX() - deltaForXMovement, this.location.getY());
    }
    public void MoveRight(){
        boolean isOnTheRightEdgeOfCanvas = this.getPolygon().getBounds2D().getMaxX() >= Game.CANVAS_WIDTH;
        if(isOnTheRightEdgeOfCanvas)
            this.location.setLocation(Game.CANVAS_WIDTH - this.getPolygon().getBounds2D().getWidth() * drawingScale, this.location.getY());
        else
            this.location.setLocation(this.location.getX() + deltaForXMovement, this.location.getY());
    }

    private long lastTimeShoot;
    public Projectile Shoot(){
        if(Game.GetCurrentUpateCount() - lastTimeShoot < shootCooldownUpdateTime)
            return null;

        lastTimeShoot = Game.GetCurrentUpateCount();
        this.playSound_shoot();

        return new Projectile(new Point((int)location.getX(), (int)(location.getY() - 60 * drawingScale)));
    }
    private void playSound_shoot(){
        try {
            InputStream in = new FileInputStream("src/resources/laser.wav");
            AudioStream audioStream = new AudioStream(in);
            AudioPlayer.player.start(audioStream);
        }
        catch (Exception e){
            System.out.println("Sound error");
        }
    }


    public void paintComponent(Graphics g) {
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
    private Polygon getPolygon(){
        Point[] heroShipDrawingPoints = getShapePoints();
        return new Polygon(
                Arrays.stream(heroShipDrawingPoints).mapToInt(point -> (int)(point.getX() * drawingScale) + location.x).toArray(),
                Arrays.stream(heroShipDrawingPoints).mapToInt(point -> (int)(point.getY() * drawingScale) + location.y).toArray(),
                heroShipDrawingPoints.length);
    }
}
