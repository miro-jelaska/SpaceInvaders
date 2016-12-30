package collision;

import actors.*;
import events.EventResolution;
import events.commands.*;
import game.Game;
import utilities.CollisionalShape;

import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

public class CollisionDetection {
    private final Game game;
    private final EventResolution eventResolution;

    public CollisionDetection(
            Game game,
            EventResolution eventResolution) {
        this.game = game;
        this.eventResolution = eventResolution;
    }

    public void Detect(){
        for (InvaderProjectile invaderProjectile : game.allInvaderProjectiles){
            if(IsShapeOutsideWindow(invaderProjectile))
                eventResolution.Push(new RemoveInvaderProjectileOutOfWindow(invaderProjectile));
            else if(areTwoShapesInCollision(game.heroShip, invaderProjectile))
                eventResolution.Push(new EndGame(false));
        }

        for (HeroProjectile heroProjectile : game.allHeroProjectiles){
            if(IsShapeOutsideWindow(heroProjectile))
                eventResolution.Push(new RemoveHeroProjectileOutOfWindow(heroProjectile));
            else for (InvaderShip invaderShip: game.allInvaderShips)
                if(areTwoShapesInCollision(invaderShip, heroProjectile)){
                    eventResolution.Push(new ExplodeInvaderShip(invaderShip, eventResolution));
                    eventResolution.Push(new AbsorbProjectile(heroProjectile));
                }
        }

        boolean isAnyInvaderAtLeftOrRightEdge =
            game.allInvaderShips.stream()
            .anyMatch(invader -> CollisionDetection.IsShapeAtEdge_Left(invader) || CollisionDetection.IsShapeAtEdge_Right(invader));
        if(isAnyInvaderAtLeftOrRightEdge)
            eventResolution.Push(new MoveInvadersToNextLineAndChangeDirectionOfMovement());

        boolean isAnyInvaderAtBottomEdge = game.allInvaderShips.stream().anyMatch(CollisionDetection::IsShapeAtEdge_Bottom);
        if(isAnyInvaderAtBottomEdge)
            eventResolution.Push(new EndGame(false));

        boolean isInvaderInCollisionWithHero = game.allInvaderShips.stream().anyMatch(invader -> areTwoShapesInCollision(invader, game.heroShip));
        if(isInvaderInCollisionWithHero)
            eventResolution.Push(new EndGame(false));
    }

    public static boolean IsShapeAtEdge_Left(CollisionalShape shape){
        return shape.GetCollisionArea().getBounds2D().getMinX() <= 0;
    }
    public static boolean IsShapeAtEdge_Right(CollisionalShape shape){
        return shape.GetCollisionArea().getBounds2D().getMaxX() >= Game.CANVAS_WIDTH;
    }
    public static boolean IsShapeAtEdge_Bottom(CollisionalShape shape){
        return shape.GetCollisionArea().getBounds2D().getMaxY() >= Game.CANVAS_HEIGHT;
    }
    public static boolean IsShapeOutsideWindow(CollisionalShape shape){
        Rectangle2D bounds2D = shape.GetCollisionArea().getBounds2D();
        return
            bounds2D.getMaxX() < 0 ||
            bounds2D.getMinX() > Game.CANVAS_WIDTH ||
            bounds2D.getMaxY() < 0 ||
            bounds2D.getMinY() > Game.CANVAS_HEIGHT;
    }
    private static boolean areTwoShapesInCollision(CollisionalShape firstShape, CollisionalShape secondShape){
        return areTwoShapesInCollision(firstShape.GetCollisionArea(), secondShape.GetCollisionArea());
    }
    private static boolean areTwoShapesInCollision(Area firstShape, Area secondShape){
        Area firstShapeCopy = new Area(firstShape);
        firstShapeCopy.intersect(secondShape);
        return !firstShapeCopy.isEmpty();
    }
}
