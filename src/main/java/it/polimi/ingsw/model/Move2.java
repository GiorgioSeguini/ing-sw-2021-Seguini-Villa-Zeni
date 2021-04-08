package it.polimi.ingsw.model;

/*Last Edit: Fabio*/
public class Move2 implements SoloActionTokens {

    /*Default constructor*/
    public Move2() {
    }

    /**This apply the effect of a Move2Token**/
    @Override
    public void ActivateToken(Game game) {
        for(int i=0; i<2; i++){
            game.getSoloGame().getFaithTrack().addPoint();
        }
        game.popesInspection();
    }
}
