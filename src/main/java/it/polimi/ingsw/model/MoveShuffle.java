package it.polimi.ingsw.model;
 import java.lang.Math;
import java.util.ArrayList;

public class MoveShuffle extends SoloActionTokens {

    public MoveShuffle() {
    }

    /*Abstract class to implement*/
    public void ActivateToken(Game game) {
        game.getSoloGame().getFaithTrack().addPoint();
        //game.getSoloGame().getSoloActionTokens().shuffle;                 //TODO
    }

    public void shuffle(ArrayList<SoloActionTokens> soloActionTokens){
        int n = (int) (Math.random() * 7.1);

    }
}