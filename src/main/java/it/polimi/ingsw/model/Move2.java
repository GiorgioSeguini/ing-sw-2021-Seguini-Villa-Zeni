package it.polimi.ingsw.model;

/*Last Edit: Fabio*/
public class Move2 extends SoloActionTokens {

    /*Default constructor*/
    public Move2() {
    }

    /*Additional methods*/
    /**This apply the effect of a Move2Token**/
    public void ActivateToken(Game game) {
        for(int i=0; i<2; i++){
        game.getSoloGame().getFaithTrack().addPoint();
        }
        game.popesIspection();
    }
}