package it.polimi.ingsw.model;

/*Last Edit: Gio*/

import it.polimi.ingsw.model.enumeration.ResourceType;

public class DepotsAbility extends Ability {

    private ResourceType typeOfRes;

    /*Default constructor*/
    public DepotsAbility() {
        //TODO
    }

    /*Getter*/
    public ResourceType getTypeOfRes() {
        return this.typeOfRes;
    }

    public void RunAbility(Player owner){
        Shelf shelf =  new Shelf(2);
        shelf.setResType(typeOfRes);
        owner.getDepots().addExtraShelf(shelf);
    }


}