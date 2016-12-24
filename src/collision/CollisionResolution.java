package collision;
import collision.commands.*;

import actors.HeroShip;
import actors.InvaderShip;
import actors.Projectile;

import java.util.ArrayList;
import java.util.List;

public class CollisionResolution {
    private final HeroShip heroShip;
    private final List<Projectile>  allProjectiles;
    private final List<InvaderShip> allInvaders;
    private final List<Command>    actions = new ArrayList<Command>();

    public CollisionResolution(
        HeroShip heroShip,
        List<Projectile>  allProjectiles,
        List<InvaderShip> allInvaders) {
        this.heroShip    = heroShip;
        this.allProjectiles = allProjectiles;
        this.allInvaders    = allInvaders;
    }

    public void Resolve(){
        actions.forEach(action -> action.Execute());
        actions.clear();
    }

    public void InvaderIsHitByProjectile(InvaderShip invader, Projectile projectile){
        actions.add(new ExplodeInvaderShip(allInvaders, invader));
        actions.add(new AbsorbProjectile(allProjectiles, projectile));
    }

    public void ProjectileOutOfWindow(Projectile projectile){
        actions.add(new RemoveProjectileOutOfWindow(allProjectiles, projectile));
    }
}
