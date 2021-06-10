package it.polimi.ingsw.client.modelClient;

import it.polimi.ingsw.constant.model.FaithTrack;
import it.polimi.ingsw.constant.model.LorenzoSoloPlayer;

public class LorenzoSoloPlayerClient extends LorenzoSoloPlayer {

    private transient TokenType revealed;

    public LorenzoSoloPlayerClient(FaithTrack faithTrack) {
        super(faithTrack);
    }


    public TokenType getRevealed(){
        return revealed;
    }

}
