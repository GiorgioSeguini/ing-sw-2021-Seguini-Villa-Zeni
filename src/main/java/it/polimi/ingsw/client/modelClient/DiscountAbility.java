package it.polimi.ingsw.client.modelClient;

/*Last Edit: Gio*/

import it.polimi.ingsw.client.modelClient.enumeration.ResourceType;

public class DiscountAbility implements Ability {

    private final ResourceType resourceType;
    private final int discount;

    /*Default constructor*/
    public DiscountAbility(ResourceType resourceType, int discount) {
        this.resourceType = resourceType;
        this.discount = discount;

    }

    /*Getter*/
    public ResourceType getTypeOfRes() {
        return resourceType;
    }

    public int getDiscountAmount() {
        return discount;
    }


    @Override
    public boolean equals(Object o){
        if(o == this)
            return true;

        if(!(o instanceof DiscountAbility))
            return false;

        DiscountAbility other = (DiscountAbility) o;

        if(this.discount != other.discount)
            return false;

        return other.resourceType.equals(this.resourceType);
    }
}