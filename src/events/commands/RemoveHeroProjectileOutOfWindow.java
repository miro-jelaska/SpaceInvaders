package events.commands;

import actors.HeroProjectile;
import game.Game;
import utilities.Command;

public class RemoveHeroProjectileOutOfWindow implements Command {
    private final HeroProjectile heroProjectileOutOfWindow;

    public RemoveHeroProjectileOutOfWindow(HeroProjectile heroProjectileOutOfWindow){
        this.heroProjectileOutOfWindow = heroProjectileOutOfWindow;
    }

    @Override
    public void Apply(Game game) {
        int indexOfDeadProjectile = game.allHeroProjectiles.indexOf(heroProjectileOutOfWindow);
        game.allHeroProjectiles.remove(indexOfDeadProjectile);
    }
}
