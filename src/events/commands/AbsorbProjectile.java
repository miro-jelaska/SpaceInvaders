package events.commands;

import actors.HeroProjectile;
import game.Game;
import utilities.Command;

public class AbsorbProjectile implements Command {
    private final HeroProjectile heroProjectileThatHit;

    public AbsorbProjectile(HeroProjectile heroProjectileThatHit){
        this.heroProjectileThatHit = heroProjectileThatHit;
    }

    @Override
    public void Apply(Game game) {
        int indexOfDeadProjectile = game.allHeroProjectiles.indexOf(heroProjectileThatHit);
        game.allHeroProjectiles.remove(indexOfDeadProjectile);
    }
}
