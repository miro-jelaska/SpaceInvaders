package actors;

import utilities.*;
import game.Game;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import java.awt.*;
import java.awt.geom.Area;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;

public class HeroShip implements GraphicalShape {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 80;
    private static final double DRAWING_SCALE = 0.5;
    private static final int DELTA_X = 5;
    private static final int SHOOT_COOLDOWN_UPDATE_TIME = 30;

    private Point location = new Point((int)(Game.CANVAS_WIDTH/2 - (WIDTH/2 * DRAWING_SCALE)), Game.CANVAS_HEIGHT - HEIGHT);

    public void MoveLeft(){
        boolean isOnTheLeftEdgeOfCanvas = this.GetGraphicalShape().getBounds2D().getMinX() <= 0;
        if(isOnTheLeftEdgeOfCanvas)
            this.location.setLocation(this.GetGraphicalShape().getBounds2D().getWidth()*DRAWING_SCALE, this.location.getY());
        else
            this.location.setLocation(this.location.getX() - DELTA_X, this.location.getY());
    }
    public void MoveRight(){
        boolean isOnTheRightEdgeOfCanvas = this.GetGraphicalShape().getBounds2D().getMaxX() >= Game.CANVAS_WIDTH;
        if(isOnTheRightEdgeOfCanvas)
            this.location.setLocation(Game.CANVAS_WIDTH - this.GetGraphicalShape().getBounds2D().getWidth() * DRAWING_SCALE, this.location.getY());
        else
            this.location.setLocation(this.location.getX() + DELTA_X, this.location.getY());
    }

    private long lastTimeShoot;
    public Projectile Shoot(){
        if(Game.GetCurrentUpateCount() - lastTimeShoot < SHOOT_COOLDOWN_UPDATE_TIME)
            return null;

        lastTimeShoot = Game.GetCurrentUpateCount();
        this.playSound_shoot();

        return new Projectile(new Point((int)(location.getX() + WIDTH/2*DRAWING_SCALE), (int)(location.getY())));
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
        Area heroShipDrawingShape = GetGraphicalShape();
        g2.fill(heroShipDrawingShape);
    }



    @Override
    public Area GetGraphicalShape() {
        Area area = new Area(new Rectangle(
                this.location.x, this.location.y,
                (int)(WIDTH*DRAWING_SCALE),(int)(HEIGHT*DRAWING_SCALE)));

        Arrays.stream(new Area[]{
                getSingleShapePeace(40, 0, 20, 10),

                getSingleShapePeace(0, 0, 20, 20),
                getSingleShapePeace((WIDTH - 20), 0, 20, 20),

                getSingleShapePeace(0, 6, 25, 2),
                getSingleShapePeace((WIDTH - 25), 6, 25, 2),
                getSingleShapePeace(0, 10, 25, 2),
                getSingleShapePeace((WIDTH - 25), 10, 25, 2),
                getSingleShapePeace(0, 14, 25, 2),
                getSingleShapePeace((WIDTH - 25), 14, 25, 2),

                getSingleShapePeace(0, 20, 30, 20),
                getSingleShapePeace((WIDTH - 30), 20, 30, 20),
                getSingleShapePeace(0, 40, 20, 10),
                getSingleShapePeace((WIDTH - 20), 40, 20, 10),
                getSingleShapePeace(0, 50, 10, 10),
                getSingleShapePeace((WIDTH - 10), 50, 10, 10),

                getSingleShapePeace(0, 76, 16, 5),
                getSingleShapePeace((WIDTH - 16), 76, 16, 5),
                getSingleShapePeace(40, 76, 20, 8),

        }).forEach(area::subtract);

        return area;
    }
    private Area getSingleShapePeace(int xPosition, int yPosition, int width, int height){
        return new Area(new Rectangle(
                (int)(xPosition*DRAWING_SCALE) + this.location.x, (int)(yPosition*DRAWING_SCALE) + this.location.y,
                (int)(width*DRAWING_SCALE), (int)(height*DRAWING_SCALE)));
    }
}
