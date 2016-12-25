package collision.commands;

import game.Game;
import utilities.Command;

public class MoveInvadersToNextLineAndChangeDirectionOfMovement implements Command {
    @Override
    public void Apply(Game game) {
        game.allInvaderShips.forEach(invader -> {
            invader.ChangeDirectionOfMovement();
            invader.MoveToNextLine();
        });
    }
}
