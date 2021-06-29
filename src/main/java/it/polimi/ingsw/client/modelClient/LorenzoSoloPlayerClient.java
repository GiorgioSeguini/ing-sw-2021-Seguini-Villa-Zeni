package it.polimi.ingsw.client.modelClient;

import it.polimi.ingsw.constant.model.FaithTrack;
import it.polimi.ingsw.constant.model.LorenzoSoloPlayer;

/**
 * The Lorenzo solo player client.
 * Contains just a reference to the last revealed tokens, all other tokens are unknowns
 */
public class LorenzoSoloPlayerClient extends LorenzoSoloPlayer {

    private TokenType revealed;

    /**
     * Instantiates a new Lorenzo solo player client.
     *
     * @param faithTrack the faith track
     */
    public LorenzoSoloPlayerClient(FaithTrack faithTrack) {
        super(faithTrack);
    }


    /**
     * Get revealed token type.
     *
     * @return the token type - of type TokenType
     */
    public TokenType getRevealed(){
        return revealed;
    }

}
