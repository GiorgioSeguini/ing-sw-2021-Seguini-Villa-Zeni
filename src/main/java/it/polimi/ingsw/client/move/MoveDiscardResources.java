package it.polimi.ingsw.client.move;

import it.polimi.ingsw.client.modelClient.NumberOfResources;

public class MoveDiscardResources extends MoveType {
    private final NumberOfResources toDiscard;
    public static final String className= "MoveDiscardResources";

    public MoveDiscardResources(int idPlayer, NumberOfResources toDiscard){
        super(idPlayer);
        this.toDiscard=toDiscard;
    }

    @Override
    public String getClassName() {
        return className;
    }
}
