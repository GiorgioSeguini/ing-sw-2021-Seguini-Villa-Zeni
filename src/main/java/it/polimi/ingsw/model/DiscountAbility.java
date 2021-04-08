package it.polimi.ingsw.model;

/*Last Edit: Gio*/

import it.polimi.ingsw.model.enumeration.ResourceType;

public class DiscountAbility implements Ability{

    private final ResourceType resourceType;
    private final int discount;

    /*Default constructor*/
    public DiscountAbility(ResourceType resourceType, int discount) {
        this.resourceType = resourceType;
        this.discount = discount;

    }

    /*Getter*/
    public ResourceType getDiscountType() {
        return resourceType;
    }

    public int getDiscountAmount() {
        return discount;
    }

    @Override
    public void RunAbility(Player owner){
        owner.addDiscount(resourceType, discount);
    }

}