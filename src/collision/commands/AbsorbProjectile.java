package collision.commands;

import actors.Projectile;
import game.Game;

public class AbsorbProjectile implements Command {
    private final Projectile projectileThatHit;

    public AbsorbProjectile(Projectile projectileThatHit){
        this.projectileThatHit = projectileThatHit;
    }

    @Override
    public void Apply(Game game) {
        int indexOfDeadProjectile = game.allHeroProjectiles.indexOf(projectileThatHit);
        game.allHeroProjectiles.remove(indexOfDeadProjectile);
    }
}
