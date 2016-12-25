package collision;
import collision.commands.*;

import actors.InvaderShip;
import actors.Projectile;
import game.Game;
import utilities.Command;

import java.util.ArrayList;
import java.util.List;

public class CollisionResolution {
    private final List<Command>    actions = new ArrayList<Command>();
    private final Game game;

    public CollisionResolution(Game game) {
        this.game = game;
    }

    public void Resolve(){
        actions.forEach(action -> action.Apply(game));
        actions.clear();
    }

    public void MoveInvadersToNextLine(){
        actions.add(new MoveInvadersToNextLineAndChangeDirectionOfMovement());
    }

    public void InvaderIsHitByProjectile(InvaderShip invader, Projectile projectile){
        actions.add(new ExplodeInvaderShip(invader));
        actions.add(new AbsorbProjectile(projectile));
    }

    public void ProjectileOutOfWindow(Projectile projectile){
        actions.add(new RemoveProjectileOutOfWindow(projectile));
    }
}
