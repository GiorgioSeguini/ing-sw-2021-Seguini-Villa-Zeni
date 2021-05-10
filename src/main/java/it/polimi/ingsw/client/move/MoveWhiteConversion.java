package it.polimi.ingsw.client.move;

import it.polimi.ingsw.constant.enumeration.ResourceType;

import java.util.ArrayList;

public class MoveWhiteConversion extends MoveType {
    private final ArrayList<ResourceType> whiteMarbles;
    public static final String className= "MoveWhiteConversion";

    public MoveWhiteConversion(int idPlayer, ArrayList<ResourceType> whitemarbles){
        super(idPlayer, className);
        this.whiteMarbles=whitemarbles;
    }

}
