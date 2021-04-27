package it.polimi.ingsw.client.modelClient;
/*Last Edit: William Zeni*/

import it.polimi.ingsw.client.modelClient.enumeration.ResourceType;

public class ProductionPowerPlusAbility implements Ability {

    private final ResourceType typeOfRes;

    /* Default constructor*/
    public ProductionPowerPlusAbility(ResourceType type) {
        this.typeOfRes=type;
    }

    /*Getter*/
    public ResourceType getTypeOfRes() {
        return typeOfRes;
    }


    @Override
    public boolean equals(Object o){
        if(o == this)
            return true;

        if(!(o instanceof ProductionPowerPlusAbility))
            return false;

        ProductionPowerPlusAbility other = (ProductionPowerPlusAbility) o;

        return other.typeOfRes.equals(this.typeOfRes);
    }
}