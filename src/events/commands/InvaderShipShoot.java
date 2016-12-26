package events.commands;

import actors.InvaderProjectile;
import actors.InvaderShip;
import game.Game;
import utilities.Command;
import utilities.SoundEffectPlayer;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class InvaderShipShoot implements Command {
    @Override
    public void Apply(Game game) {
        if(game.allInvaderShips.isEmpty())
            return;

        InvaderShip randomInvader = game.allInvaderShips.get(new Random().nextInt(game.allInvaderShips.size()));
        Rectangle2D randomInvaderBounds2D = randomInvader.GetGraphicalShape().getBounds2D();
        Point projectileLocation = new Point(
            (int)(randomInvaderBounds2D.getX() + InvaderShip.WIDTH / 2 * InvaderShip.DRAWING_SCALE),
            (int)(randomInvaderBounds2D.getY()));
        game.allInvaderProjectiles.add(new InvaderProjectile(projectileLocation));
        SoundEffectPlayer.Play("src/resources/laser.wav");
    }
}
