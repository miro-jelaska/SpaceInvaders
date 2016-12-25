package collision;

import actors.InvaderShip;
import actors.HeroProjectile;
import game.Game;
import utilities.GraphicalShape;

import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

public class CollisionDetection {
    private final Game game;
    private final CollisionResolution collisionResolution;

    public CollisionDetection(
        Game game,
        CollisionResolution collisionResolution) {
        this.game = game;
        this.collisionResolution = collisionResolution;
    }

    public void Detect(){
        game.allHeroProjectiles
            .stream()
            .filter(CollisionDetection::IsShapeOutsideWindow)
            .forEach(collisionResolution::ProjectileOutOfWindow);

        for (InvaderShip invaderShip: game.allInvaderShips)
            for (HeroProjectile heroProjectile : game.allHeroProjectiles)
                if(areTwoShapesInCollision(invaderShip, heroProjectile))
                    collisionResolution.InvaderIsHitByProjectile(invaderShip, heroProjectile);

        boolean isAnyInvaderAtLeftOrRightEdge =
            game.allInvaderShips.stream().anyMatch(invader -> CollisionDetection.IsShapeAtEdge_Left(invader) || CollisionDetection.IsShapeAtEdge_Right(invader));
        if(isAnyInvaderAtLeftOrRightEdge)
            collisionResolution.MoveInvadersToNextLine();
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
