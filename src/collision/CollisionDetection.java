package collision;

import actors.HeroShip;
import actors.InvaderShip;
import actors.Projectile;

import java.util.List;

public class CollisionDetection {
    HeroShip heroShip;
    List<Projectile>  allProjectiles;
    List<InvaderShip> allInvaders;
    CollisionResolution collisionResolution;

    public CollisionDetection(
        HeroShip heroShip,
        List<Projectile>  allProjectiles,
        List<InvaderShip> allInvaders,
        CollisionResolution collisionResolution) {
        this.heroShip    = heroShip;
        this.allProjectiles = allProjectiles;
        this.allInvaders    = allInvaders;
        this.collisionResolution = collisionResolution;
    }

    public void Detect(){
        this.allProjectiles
            .stream()
            .filter(projectile -> projectile.IsOutsideWindow())
            .forEach(projectile -> collisionResolution.ProjectileOutOfWindow(projectile));

        for (InvaderShip invaderShip: allInvaders)
            for (Projectile projectile: allProjectiles)
                if(invaderShip.IsHitByProjectile(projectile))
                    collisionResolution.InvaderIsHitByProjectile(invaderShip, projectile);
        
    }
}
