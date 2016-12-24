package collision.commands;

import actors.InvaderShip;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class ExplodeInvaderShip implements Command {
    private final List<InvaderShip> allInvaders;
    private final InvaderShip invaderShipThatIsHit;

    public ExplodeInvaderShip(
            List<InvaderShip> allInvaders,
            InvaderShip invaderShipThatIsHit){
        this.allInvaders = allInvaders;
        this.invaderShipThatIsHit = invaderShipThatIsHit;
    }

    @Override
    public void Execute() {
        int indexOfExplodedShip = allInvaders.indexOf(invaderShipThatIsHit);
        if(indexOfExplodedShip >= 0){
            allInvaders.remove(indexOfExplodedShip);
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
