package collision.commands;

import actors.Projectile;
import game.Game;
import utilities.Command;

public class RemoveProjectileOutOfWindow implements Command {
    private final Projectile projectileOutOfWindow;

    public RemoveProjectileOutOfWindow(Projectile projectileOutOfWindow){
        this.projectileOutOfWindow = projectileOutOfWindow;
    }

    @Override
    public void Apply(Game game) {
        int indexOfDeadProjectile = game.allHeroProjectiles.indexOf(projectileOutOfWindow);
        game.allHeroProjectiles.remove(indexOfDeadProjectile);
    }
}
