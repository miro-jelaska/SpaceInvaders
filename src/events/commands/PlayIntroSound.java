package events.commands;

import game.Game;
import resources.SoundEffectTracks;
import utilities.Command;
import utilities.SoundEffectPlayer;

public class PlayIntroSound implements Command {
    @Override
    public void Apply(Game game) {
        SoundEffectPlayer.Play(SoundEffectTracks.GetTrackPath(SoundEffectTracks.Track.IntroSound));
    }
}
