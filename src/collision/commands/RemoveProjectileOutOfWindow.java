package collision.commands;

import actors.Projectile;
import game.Game;

public class RemoveProjectileOutOfWindow implements Command {
    private final Projectile projectileOutOfWindow;

    public RemoveProjectileOutOfWindow(Projectile projectileOutOfWindow){
        this.projectileOutOfWindow = projectileOutOfWindow;
    }

    @Override
    public void Apply(Game game) {
        int indexOfDeadProjectile = game.projectiles.indexOf(projectileOutOfWindow);
        game.projectiles.remove(indexOfDeadProjectile);
    }
}
