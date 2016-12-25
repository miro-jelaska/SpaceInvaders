package collision;

import actors.InvaderShip;
import actors.HeroProjectile;
import events.EventResolution;
import events.commands.AbsorbProjectile;
import events.commands.ExplodeInvaderShip;
import events.commands.MoveInvadersToNextLineAndChangeDirectionOfMovement;
import events.commands.RemoveProjectileOutOfWindow;

public class CollisionResolution {
    private final EventResolution eventResolution;

    public CollisionResolution(EventResolution eventResolution) {
        this.eventResolution = eventResolution;
    }

    public void MoveInvadersToNextLine(){
        eventResolution.Push(new MoveInvadersToNextLineAndChangeDirectionOfMovement());
    }

    public void InvaderIsHitByProjectile(InvaderShip invader, HeroProjectile heroProjectile){
        eventResolution.Push(new ExplodeInvaderShip(invader));
        eventResolution.Push(new AbsorbProjectile(heroProjectile));
    }

    public void ProjectileOutOfWindow(HeroProjectile heroProjectile){
        eventResolution.Push(new RemoveProjectileOutOfWindow(heroProjectile));
    }
}
