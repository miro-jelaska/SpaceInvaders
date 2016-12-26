package events.commands;

import actors.HeroShip;
import actors.HeroProjectile;
import game.Game;
import utilities.Command;
import utilities.SoundEffectPlayer;

import java.awt.*;

public class HeroShipShoot implements Command {
    private final Point heroShipLocation;
    public HeroShipShoot(Point heroShipLocation) {
        this.heroShipLocation = heroShipLocation;
    }

    @Override
    public void Apply(Game game) {
        Point projectileLocation = new Point(
            (int)(heroShipLocation.getX() + HeroShip.WIDTH / 2 * HeroShip.DRAWING_SCALE),
            (int)(heroShipLocation.getY()));
        game.allHeroProjectiles.add(new HeroProjectile(projectileLocation));
        SoundEffectPlayer.Play("src/resources/organic-laser-shot_by_qubodup.wav");
    }
}
