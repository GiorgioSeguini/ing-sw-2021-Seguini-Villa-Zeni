package it.polimi.ingsw.model;
 import java.lang.Math;
import java.util.ArrayList;

/*Last Edit: Fabio*/
public class MoveShuffle extends SoloActionTokens {

    private ArrayList<SoloActionTokens> shuffledDeck;

    /*Default constructor*/
    public MoveShuffle() {
        shuffledDeck = new ArrayList<>(7);
    }

    /*Additional methods*/
    /**This apply the effect of a MoveShuffleToken**/
    public void ActivateToken(Game game) {
        game.getSoloGame().getFaithTrack().addPoint();
        shuffledDeck = new ArrayList<>(7);
        shuffledDeck = Shuffle(game.getSoloGame().getCopyOfSoloActionTokensInit());
        game.getSoloGame().setSoloActionTokens(shuffledDeck);
    }

    /**This shuffle randomly the SoloActionToken's stack**/
    public ArrayList<SoloActionTokens> Shuffle(ArrayList<SoloActionTokens> soloActionTokens){
        for(int i=0; i<7; i++) {
            int n = (int) (Math.random() * 6.1);
            while(shuffledDeck.get(n) != null) {
                n = (int) (Math.random() * 6.1);
            }
            shuffledDeck.add(n, soloActionTokens.get(i));
        }
        return shuffledDeck;
    }

}