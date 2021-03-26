package it.polimi.ingsw.model;

public class Move2 extends SoloActionTokens {

    /*Default constructor*/
    public Move2() {
    }

    /*Abstract class to implement*/
    public void ActivateToken(Game game) {
        final int point=2;
        game.getSoloGame().getFaithTrack().addPoint(point);
    }
}