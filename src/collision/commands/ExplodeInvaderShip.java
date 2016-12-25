package collision.commands;

import actors.InvaderShip;
import game.Game;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import java.io.FileInputStream;
import java.io.InputStream;

public class ExplodeInvaderShip implements Command {
    private final InvaderShip invaderShipThatIsHit;

    public ExplodeInvaderShip(InvaderShip invaderShipThatIsHit){
        this.invaderShipThatIsHit = invaderShipThatIsHit;
    }

    @Override
    public void Apply(Game game) {
        int indexOfExplodedShip = game.allInvaderShips.indexOf(invaderShipThatIsHit);
        if(indexOfExplodedShip >= 0){
            game.allInvaderShips.remove(indexOfExplodedShip);
            playSound_explosion();
        }
    }

    private void playSound_explosion(){
        try {
            InputStream in = new FileInputStream("src/resources/jm-fx-boom-01a_by_julien-matthey.wav");
            AudioStream audioStream = new AudioStream(in);
            AudioPlayer.player.start(audioStream);
        }
        catch (Exception e){
            System.out.println("Sound error");
        }
    }
}
