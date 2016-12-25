package collision;

import actors.HeroShip;
import actors.InvaderShip;
import actors.Projectile;
import utilities.GraphicalShape;

import java.awt.geom.Area;
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
                if(areTwoShapesInCollision(invaderShip, projectile))
                    collisionResolution.InvaderIsHitByProjectile(invaderShip, projectile);

    }

    private static boolean areTwoShapesInCollision(GraphicalShape firstShape, GraphicalShape secondShape){
        return areTwoShapesInCollision(firstShape.GetGraphicalShape(), secondShape.GetGraphicalShape());
    }
    private static boolean areTwoShapesInCollision(Area firstShape, Area secondShape){
        Area firstShapeCopy = new Area(firstShape);
        firstShapeCopy.intersect(secondShape);
        return !firstShapeCopy.isEmpty();
    }
}
