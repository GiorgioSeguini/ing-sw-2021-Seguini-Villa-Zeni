package it.polimi.ingsw.server.model;


/**
 * Move 2 class.
 * Lorenzo solo Player gains 2 faithPoints
 * Implements SoloActionTokens interface.
 */
public class Move2 implements SoloActionTokens {

    public static final String name = "MOVE2";

    /**
     * Instantiates a new Move 2.
     */
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
     * @return True if param o is equals to this. False if param o isn't an instance of Move2 or o isn't equals to this.
     */
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
