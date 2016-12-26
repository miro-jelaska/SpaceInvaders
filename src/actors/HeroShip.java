package actors;

import collision.CollisionDetection;
import events.EventResolution;
import events.commands.HeroShipShoot;
import utilities.*;
import game.Game;
import java.awt.*;
import java.awt.geom.Area;
import java.util.Arrays;

public class HeroShip implements GraphicalShape {
    public static final int WIDTH = 100;
    public static final int HEIGHT = 80;
    public static final double DRAWING_SCALE = 0.5;

    private static final int DELTA_X = 5;
    private static final int SHOOT_COOLDOWN_UPDATE_TIME = 30;
    private final EventResolution eventResolution;
    private final GameTimer gameTimer;

    private Point location = new Point((int)(Game.CANVAS_WIDTH/2 - (WIDTH/2 * DRAWING_SCALE)), Game.CANVAS_HEIGHT - HEIGHT);

    public HeroShip(EventResolution eventResolution, GameTimer gameTimer) {
        this.eventResolution = eventResolution;
        this.gameTimer = gameTimer;
    }

    public void MoveLeft(){
        if(CollisionDetection.IsShapeAtEdge_Left(this))
            this.location.setLocation(1, this.location.getY());
        else
            this.location.setLocation(this.location.getX() - DELTA_X, this.location.getY());
    }
    public void MoveRight(){
        if(CollisionDetection.IsShapeAtEdge_Right(this))
            this.location.setLocation(Game.CANVAS_WIDTH - WIDTH * DRAWING_SCALE, this.location.getY());
        else
            this.location.setLocation(this.location.getX() + DELTA_X, this.location.getY());
    }

    private long lastTimeShoot = 0;
    public void Shoot(){
        if(gameTimer.GetCurrentUpdateCount() - lastTimeShoot > SHOOT_COOLDOWN_UPDATE_TIME){
            eventResolution.Push(new HeroShipShoot(this.location));
            lastTimeShoot = gameTimer.GetCurrentUpdateCount();
        }
    }

    @Override
    public void Paint(Graphics2D graphics){
        graphics.setColor(Color.white);
        Area heroShipDrawingShape = GetGraphicalShape();
        graphics.fill(heroShipDrawingShape);
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
