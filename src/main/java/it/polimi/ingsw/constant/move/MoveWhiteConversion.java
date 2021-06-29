package it.polimi.ingsw.constant.move;

import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.model.Game;

import java.util.ArrayList;

/**
 * The type Move white conversion. Move to select how to convert white marbles bought from the market if more than one option is available
 * If a player has active two leader card, both with the ability to convert a white marble into a resources, each type one is bought she must chose between the two option.
 * If player has just one leader card active with the ability to convert a white marble into a resources this option is forced, so this move is not needed, neither accepted
 */
public class MoveWhiteConversion extends MoveType {
    
    /**
     * The constant className.
     */
    public static final String className= "MoveWhiteConversion";
    private static final PlayerStatus[] allowedStatus = new PlayerStatus[]{PlayerStatus.NeedToConvert};
    private ArrayList<ResourceType> whiteMarbles;
    /**
     * Instantiates a new Move white conversion.
     *
     * @param idPlayer the id of the player
     */
    public MoveWhiteConversion(int idPlayer){
        super(idPlayer);
    }

    /**
     * @see MoveType#getClassName() 
     */
    @Override
    public String getClassName() {
        return className;
    }

    /**
     * Gets the list of the player choice in how to convert white marbles, eaach element represent a single marble conversion
     * In order to be accepted by the server list length must be equal to the number of white marbles bought
     * Same resource can be repeated
     * @return the white marbles of type ArrayList<ResourcesType>
     */
    public ArrayList<ResourceType> getWhiteMarbles() {
        return whiteMarbles;
    }

    /**
     * Sets the list of the player choice in how to convert white marbles, eaach element represent a single marble conversion
     * In order to be accepted by the server list length must be equal to the number of white marbles bought
     * Same resource can be repeated
     * @param whiteMarbles the white marbles of type ArrayList<ResourcesType>
     */
    public void setWhiteMarbles(ArrayList<ResourceType> whiteMarbles) {
        this.whiteMarbles = new ArrayList<>();
        this.whiteMarbles.addAll(whiteMarbles);
    }

    /**
     * @see MoveType#simpleCheck(Game, PlayerStatus[]) called
     * @see MoveType#canPerform(Game) 
     */
    @Override
    public boolean canPerform(Game game) {
        return simpleCheck(game, allowedStatus);
    }
}
