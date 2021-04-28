package it.polimi.ingsw.client.modelClient;

import it.polimi.ingsw.client.modelClient.enumeration.TokenType;

import java.util.ArrayList;
import java.util.Collections;

/*Last Edit: Fabio*/
public class LorenzoSoloPlayer {

    private final FaithTrack faithTrack;
    private final Game game;
    private boolean isWinner;
    TokenType revealed;


    /*default constructor*/
    public LorenzoSoloPlayer(Game game, TokenType revealed) {
        this.game = game;
        faithTrack = new FaithTrack();
        this.revealed = revealed;
    }


    /*Getter*/
    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

    public Game getGame() {
        return game;
    }

    public boolean isWinner(){
        return this.isWinner;
    }

    public TokenType getRevealed(){
        return revealed;
    }

}