package events.commands;

import actors.InvaderShip;
import game.Game;
import utilities.Command;
import utilities.SoundEffectPlayer;

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
            SoundEffectPlayer.Play("src/resources/jm-fx-boom-01a_by_julien-matthey.wav");
            game.Score = game.Score + 100 + bonusPointsWithExponentialDecay(game.GetRuntimeInSeconds());
        }
    }
    private static final int TotalBonusPoints = 1000;
    private static final double ExponentialDecayConstant = -0.1;
    private int bonusPointsWithExponentialDecay(long time){
        return (int)(TotalBonusPoints * Math.exp(ExponentialDecayConstant * time));
    }
}
