package it.polimi.ingsw.server.model;
/*Last Edit: William Zeni*/

import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.model.ProductionPower;

public class ProductionPowerPlusAbility implements Ability {

    private final ResourceType typeOfRes;
    public static final String name="ProductionPowerPlusAbility";

    @Override
    public String getName() {
        return name;
    }

    /* Default constructor*/
    public ProductionPowerPlusAbility(ResourceType type) {
        this.typeOfRes=type;
    }

    /*Getter*/
    public ResourceType getTypeOfRes() {
        return typeOfRes;
    }

    @Override
    public void RunAbility(PlayerExt owner) {
        NumberOfResources input = new NumberOfResources();
        input=input.add(typeOfRes, 1);
        owner.getPersonalBoard().addExtraProduction(new ProductionPower(0, input, new NumberOfResources(), 1, 1));
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