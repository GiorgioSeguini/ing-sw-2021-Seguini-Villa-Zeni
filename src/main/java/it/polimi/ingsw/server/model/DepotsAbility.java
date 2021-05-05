package it.polimi.ingsw.server.model;

/*Last Edit: Gio*/

import it.polimi.ingsw.constant.enumeration.ResourceType;

public class DepotsAbility implements Ability {

    public static final int extraSize = 2;
    private final ResourceType typeOfRes;
    public static final String name="DepotsAbility";

    @Override
    public String getName() {
        return name;
    }

    /*Default constructor*/
    public DepotsAbility(ResourceType typeOfRes) {
        this.typeOfRes=typeOfRes;
    }

    /*Getter*/
    public ResourceType getTypeOfRes() {
        return this.typeOfRes;
    }

    @Override
    public void RunAbility(Player owner){
        Shelf shelf =  new Shelf(extraSize);
        shelf.setResType(typeOfRes);
        owner.getDepots().addExtraShelf(shelf);
    }

    @Override
    public boolean equals(Object o){
        if(o == this)
            return true;

        if(!(o instanceof DepotsAbility))
            return false;

        DepotsAbility other = (DepotsAbility) o;

        return other.typeOfRes.equals(this.typeOfRes);
    }

}