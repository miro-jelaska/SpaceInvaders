package collision.commands;

import actors.HeroProjectile;
import game.Game;
import utilities.Command;

public class RemoveProjectileOutOfWindow implements Command {
    private final HeroProjectile heroProjectileOutOfWindow;

    public RemoveProjectileOutOfWindow(HeroProjectile heroProjectileOutOfWindow){
        this.heroProjectileOutOfWindow = heroProjectileOutOfWindow;
    }

    @Override
    public void Apply(Game game) {
        int indexOfDeadProjectile = game.allHeroProjectiles.indexOf(heroProjectileOutOfWindow);
        game.allHeroProjectiles.remove(indexOfDeadProjectile);
    }
}
