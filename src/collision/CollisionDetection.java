package collision;

import actors.HeroShip;
import actors.InvaderShip;
import actors.Projectile;
import game.Game;
import utilities.GraphicalShape;

import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Optional;

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
            .filter(CollisionDetection::IsShapeOutsideWindow)
            .forEach(projectile -> collisionResolution.ProjectileOutOfWindow(projectile));

        for (InvaderShip invaderShip: allInvaders)
            for (Projectile projectile: allProjectiles)
                if(areTwoShapesInCollision(invaderShip, projectile))
                    collisionResolution.InvaderIsHitByProjectile(invaderShip, projectile);

    }

    public static boolean IsShapeAtEdge_Left(GraphicalShape shape){
        return shape.GetGraphicalShape().getBounds2D().getMinX() <= 0;
    }
    public static boolean IsShapeAtEdge_Right(GraphicalShape shape){
        return shape.GetGraphicalShape().getBounds2D().getMaxX() >= Game.CANVAS_WIDTH;
    }
    public static boolean IsShapeOutsideWindow(GraphicalShape shape){
        Rectangle2D bounds2D = shape.GetGraphicalShape().getBounds2D();
        return
            bounds2D.getMaxX() < 0 ||
            bounds2D.getMinX() > Game.CANVAS_WIDTH ||
            bounds2D.getMaxY() < 0 ||
            bounds2D.getMinY() > Game.CANVAS_HEIGHT;
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
