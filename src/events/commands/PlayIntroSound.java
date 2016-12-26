package events.commands;

import game.Game;
import utilities.Command;
import utilities.SoundEffectPlayer;

public class PlayIntroSound implements Command {
    @Override
    public void Apply(Game game) {
        SoundEffectPlayer.Play("src/resources/inception-sound-3_by__thatjeffcarter.wav");
    }
}
