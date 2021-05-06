package it.polimi.ingsw.server.model;

/*Last Edit: Gio*/

import it.polimi.ingsw.constant.enumeration.ResourceType;

public class DiscountAbility implements Ability{

    private final ResourceType typeOfRes;
    private final int discount;
    public static final String name="DiscountAbility";

    @Override
    public String getName() {
        return name;
    }

    /*Default constructor*/
    public DiscountAbility(ResourceType resourceType, int discount) {
        this.typeOfRes = resourceType;
        this.discount = discount;

    }

    /*Getter*/
    public ResourceType getTypeOfRes() {
        return typeOfRes;
    }

    public int getDiscountAmount() {
        return discount;
    }

    @Override
    public void RunAbility(Player owner){
        owner.addDiscount(typeOfRes, discount);
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

        return other.typeOfRes.equals(this.typeOfRes);
    }
}