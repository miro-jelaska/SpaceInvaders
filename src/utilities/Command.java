package utilities;

import game.Game;

public interface Command {
    void Apply(Game game);
}
