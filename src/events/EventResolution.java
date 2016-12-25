package events;

import game.Game;
import utilities.Command;

import java.util.ArrayList;
import java.util.List;

public class EventResolution {
    private final List<Command> actions = new ArrayList<Command>();
    private final Game game;

    public EventResolution(Game game) {
        this.game = game;
    }

    public void Push(Command command){
        actions.add(command);
    }

    public void Resolve(){
        actions.forEach(action -> action.Apply(game));
        actions.clear();
    }
}
