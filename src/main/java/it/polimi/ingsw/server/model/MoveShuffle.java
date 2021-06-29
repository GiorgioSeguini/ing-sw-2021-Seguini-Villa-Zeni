package it.polimi.ingsw.server.model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Move shuffle class.
 * Implements SoloActionTokens interface.
 * Lorenzo solo Player gains 1 faithPoints. Tokens' deck is shuffled
 */
public class MoveShuffle implements SoloActionTokens {

    public static final String name = "MOVE_SHUFFLE";
    private transient ArrayList<SoloActionTokens> shuffledDeck;

    /**
     * Instantiates a new Move shuffle.
     */
    /*Default constructor*/
    public MoveShuffle() {
        shuffledDeck = new ArrayList<>(7);
    }


    /**This apply the effect of a MoveShuffleToken**/
    @Override
    public void ActivateToken(GameExt game) {
        game.getSoloGame().getFaithTrack().addPoint();
        game.popesInspection();

        shuffledDeck = new ArrayList<>(7);
        shuffledDeck = game.getSoloGame().getCopyOfSoloActionTokensInit();
        Collections.shuffle(shuffledDeck);
        game.getSoloGame().setSoloActionTokens(shuffledDeck);
    }

    /**
     *
     * @return of type String: the name.
     */
    @Override
    public String getName(){
        return name;
    }

    /**
     *
     * @return of type String: the name.
     */
    @Override
    public String getEnum() {
        return name;
    }

    /**
     *
     * @param o of type Object.
     * @return True if param o is equals to this. False if param o isn't an instance of MoveShuffle or o isn't equals to this.
     */
    @Override
    public boolean equals(Object o){
        if(o==this)
            return true;

        if(!(o instanceof MoveShuffle))
            return false;

        MoveShuffle other = (MoveShuffle) o;

        return this.getName().equals(other.getName());
    }

}
