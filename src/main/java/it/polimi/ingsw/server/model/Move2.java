package it.polimi.ingsw.server.model;

/*Last Edit: Fabio*/
public class Move2 implements SoloActionTokens {

    public static final String name = "MOVE2";

    /*Default constructor*/
    public Move2() {
    }

    /**This apply the effect of a Move2Token**/
    @Override
    public void ActivateToken(GameExt game) {
        for(int i=0; i<2; i++){
            game.getSoloGame().getFaithTrack().addPoint();
        }
        game.popesInspection();
    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public String getEnum() {
        return name;
    }

    @Override
    public boolean equals(Object o){
        if(o==this)
            return true;

        if(!(o instanceof Move2))
            return false;

        Move2 other = (Move2) o;

        return this.getName().equals(other.getName());
    }
}
