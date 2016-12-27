package resources;

import java.util.HashMap;
import java.util.Map;

public class SoundEffectTracks {
    public enum Track {
        IntroSound,
        HeroShoot,
        InvaderShoot,
        InvaderExplosion
    }

    private static Map<Track, String> trackToPathMap =
        new HashMap<Track, String>(){{
            put(Track.IntroSound, "resources/inception-sound-3_by__thatjeffcarter.wav");
            put(Track.HeroShoot, "resources/organic-laser-shot_by_qubodup.wav");
            put(Track.InvaderShoot, "resources/laser.wav");
            put(Track.InvaderExplosion, "resources/jm-fx-boom-01a_by_julien-matthey.wav");
        }};
    public static String GetTrackPath(Track track){
        return trackToPathMap.get(track);
    }
}
