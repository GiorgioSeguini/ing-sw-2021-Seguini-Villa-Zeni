package it.polimi.ingsw.model;

/*Last Edit: Gio*/

import it.polimi.ingsw.model.enumeration.ResourceType;

public class DepotsAbility extends Ability {

    public static final int extraSize = 2;
    private final ResourceType typeOfRes;

    /*Default constructor*/
    public DepotsAbility(ResourceType typeOfRes) {
        this.typeOfRes=typeOfRes;
    }

    /*Getter*/
    public ResourceType getTypeOfRes() {
        return this.typeOfRes;
    }

    public void RunAbility(Player owner){
        Shelf shelf =  new Shelf(extraSize);
        shelf.setResType(typeOfRes);
        owner.getDepots().addExtraShelf(shelf);
    }


}