package collision.commands;

import actors.InvaderShip;
import actors.Projectile;

import java.util.List;

public class MoveInvadersToNextLineAndChangeDirectionOfMovement implements Command {
    private final List<InvaderShip> allInvaders;

    public MoveInvadersToNextLineAndChangeDirectionOfMovement(
        List<InvaderShip> allInvaders){
        this.allInvaders = allInvaders;
    }

    @Override
    public void Execute() {
        allInvaders.stream()
        .forEach(invader -> {
            invader.ChangeDirectionOfMovement();
            invader.MoveToNextLine();
        });
    }
}
