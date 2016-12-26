package collision;

import actors.InvaderProjectile;
import actors.InvaderShip;
import actors.HeroProjectile;
import events.EventResolution;
import events.commands.*;

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

    public void HeroProjectileOutOfWindow(HeroProjectile heroProjectile){
        eventResolution.Push(new RemoveHeroProjectileOutOfWindow(heroProjectile));
    }

    public void InvaderProjectileOutOfWindow(InvaderProjectile invaderProjectile){
        eventResolution.Push(new RemoveInvaderProjectileOutOfWindow(invaderProjectile));
    }

    public void HeroIsHitByProjectile(){
        eventResolution.Push(new EndGame(false));
    }
}
