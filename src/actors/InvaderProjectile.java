package actors;

import game.Game;
import utilities.GraphicalShape;

import java.awt.*;
import java.awt.geom.Area;


public class InvaderProjectile implements GraphicalShape {
    private static final int WIDTH = 8;
    private static final int HEIGHT = 20;
    private static final double DRAWING_SCALE = 1;
    private static final int DELTA_Y = 5;
    private final Point location;

    public InvaderProjectile(Point location){
        this.location = location;
    }

    public void Update(){
        this.location.setLocation(this.location.getX(), this.location.getY() + DELTA_Y);
    }

    private final double animationOmega = 0.15;
    @Override
    public void Paint(Graphics2D graphics) {

        int signLeft = (int)Math.signum(Math.sin(animationOmega * Game.GetCurrentUpateCount()));
        signLeft = signLeft > 0 ? signLeft : 0;

        int signRight = (int)Math.signum(Math.sin(animationOmega * Game.GetCurrentUpateCount() + Math.PI));
        signRight = signRight > 0 ? signRight : 0;

        if(signLeft == 0 && signRight == 0)
            signLeft = 1;

        graphics.setColor(Color.magenta);
        graphics.drawLine(
            location.x + signLeft*(int)(WIDTH* DRAWING_SCALE),
            location.y,
            location.x + signRight*(int)(WIDTH* DRAWING_SCALE),
            location.y + (int)(HEIGHT*0.25* DRAWING_SCALE));
        graphics.drawLine(
            location.x + signRight*(int)(WIDTH* DRAWING_SCALE),
            location.y + (int)(HEIGHT*0.25* DRAWING_SCALE),
            location.x + signLeft*(int)(WIDTH* DRAWING_SCALE),
            location.y + (int)(HEIGHT*0.5* DRAWING_SCALE));
        graphics.drawLine(
            location.x + signLeft*(int)(WIDTH* DRAWING_SCALE),
            location.y + (int)(HEIGHT*0.5* DRAWING_SCALE),
            location.x + signRight*(int)(WIDTH* DRAWING_SCALE),
            location.y + (int)(HEIGHT*0.75* DRAWING_SCALE));
        graphics.drawLine(
            location.x + signRight*(int)(WIDTH* DRAWING_SCALE),
            location.y + (int)(HEIGHT*0.75* DRAWING_SCALE),
            location.x + signLeft*(int)(WIDTH* DRAWING_SCALE),
            location.y + (int)(HEIGHT*1.0* DRAWING_SCALE));

        graphics.drawLine(
            location.x + (int)(WIDTH/2 * DRAWING_SCALE), location.y,
            location.x + (int)(WIDTH/2 * DRAWING_SCALE), (int)(location.y + HEIGHT* DRAWING_SCALE));
    }

    @Override
    public Area GetGraphicalShape() {
        return new Area(new Rectangle(
                location.x, location.y,
                (int)(WIDTH * DRAWING_SCALE), (int)(HEIGHT * DRAWING_SCALE)));
    }
}
