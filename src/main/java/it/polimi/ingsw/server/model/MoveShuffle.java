package it.polimi.ingsw.server.model;

import java.util.ArrayList;
import java.util.Collections;

/*Last Edit: Fabio*/
public class MoveShuffle implements SoloActionTokens {

    public static final String name = "MoveShuffle";
    private transient ArrayList<SoloActionTokens> shuffledDeck;

    /*Default constructor*/
    public MoveShuffle() {
        shuffledDeck = new ArrayList<>(7);
    }


    /**This apply the effect of a MoveShuffleToken**/
    @Override
    public void ActivateToken(Game game) {
        game.getSoloGame().getFaithTrack().addPoint();
        game.popesInspection();

        shuffledDeck = new ArrayList<>(7);
        shuffledDeck = game.getSoloGame().getCopyOfSoloActionTokensInit();
        Collections.shuffle(shuffledDeck);
        //shuffledDeck = Shuffle(game.getSoloGame().getCopyOfSoloActionTokensInit());
        game.getSoloGame().setSoloActionTokens(shuffledDeck);
    }

    @Override
    public String getName(){
        return name;
    }

    /**This shuffle randomly the SoloActionToken's stack**/
    /*private ArrayList<SoloActionTokens> Shuffle(ArrayList<SoloActionTokens> soloActionTokens){
        for(int i=0; i<7; i++) {
            int n = (int) (Math.random() * 6.1);
            while(shuffledDeck.get(n) != null) {
                n = (int) (Math.random() * 6.1);
            }
            shuffledDeck.add(n, soloActionTokens.get(i));
        }
        return shuffledDeck;
    }*/

    @Override
    public boolean equals(Object o){
        if(o==this)
            return true;

        if(!(o instanceof MoveShuffle))
            return false;

        MoveShuffle other = (MoveShuffle) o;

        if(!this.getName().equals(other.getName())){
            return false;
        }

        return true;
    }

}
