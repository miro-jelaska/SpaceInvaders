package collision.commands;

import game.Game;

public class MoveInvadersToNextLineAndChangeDirectionOfMovement implements Command {
    @Override
    public void Apply(Game game) {
        game.invaderShips.forEach(invader -> {
            invader.ChangeDirectionOfMovement();
            invader.MoveToNextLine();
        });
    }
}
