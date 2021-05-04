package it.polimi.ingsw.server.model;

/*Last Edit: William Zeni*/

import it.polimi.ingsw.constant.enumeration.ResourceType;

public class WhiteAbility implements Ability {

    private final ResourceType typeOfRes;

    /*Default constructor*/
    public WhiteAbility(ResourceType type) {
        this.typeOfRes=type;
    }

    /*Getter*/
    public ResourceType getTypeOfRes() {
        return this.typeOfRes;
    }

    @Override
    public void RunAbility(Player owner) {
        owner.getConverter().setWhiteAbility(typeOfRes);
    }

    @Override
    public boolean equals(Object o){
        if(o == this)
            return true;

        if(!(o instanceof WhiteAbility))
            return false;

        WhiteAbility other = (WhiteAbility) o;

        return other.typeOfRes.equals(this.typeOfRes);
    }
}