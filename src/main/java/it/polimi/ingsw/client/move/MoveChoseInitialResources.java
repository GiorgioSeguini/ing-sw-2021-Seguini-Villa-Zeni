package it.polimi.ingsw.client.move;

import it.polimi.ingsw.client.modelClient.NumberOfResources;

public class MoveChoseInitialResources extends MoveType {

    NumberOfResources resources;
    public static final String className= "MoveChoseInitialResources";

    public MoveChoseInitialResources(int idPlayer, NumberOfResources resources) {
        super(idPlayer, className);
        this.resources = resources;
    }

}
