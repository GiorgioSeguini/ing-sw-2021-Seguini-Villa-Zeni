package it.polimi.ingsw.client.move;

import it.polimi.ingsw.client.modelClient.NumberOfResources;

public class MoveChoseInitialResources extends MoveType {

    NumberOfResources resources;
    public static final String className= "MoveChoseInitialResources";

    public MoveChoseInitialResources(int idPlayer, NumberOfResources resources) {
        super(idPlayer);
        this.resources = resources;
    }

    @Override
    public String getClassName() {
        return className;
    }

}
