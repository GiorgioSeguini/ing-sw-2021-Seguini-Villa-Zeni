package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.enumeration.ResourceType;

/**
 * Discount ability class.
 * Implements Ability interface.
 * DiscountAbility: discount of 1 from the specified ResourceType cost when buying some new development card from the DashBoard
 */
public class DiscountAbility implements Ability{

    private final ResourceType typeOfRes;
    private final int discount;
    public static final String name="DiscountAbility";

    /**
     *
     * @return of type String: the name.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Instantiates a new Discount ability.
     *
     * @param resourceType of type ResourceType: the ability's resource type.
     * @param discount of type int: the ability's discount.
     */
    /*Default constructor*/
    public DiscountAbility(ResourceType resourceType, int discount) {
        this.typeOfRes = resourceType;
        this.discount = discount;

    }

    /**
     *
     * @return the ability's type of res.
     */

    /*Getter*/
    public ResourceType getTypeOfRes() {
        return typeOfRes;
    }

    /**
     * Gets discount amount.
     *
     * @return the ability's discount amount.
     */
    public int getDiscountAmount() {
        return discount;
    }

    /**
     * Add the discount to the owner.
     *
     * @param owner the ability's owner
     */
    @Override
    public void RunAbility(PlayerExt owner){
        owner.addDiscount(typeOfRes, discount);
    }

    /**
     *
     * @param o of type Object.
     * @return True if param o  is equals to this. False if param o isn't an instance of DiscountAbility or o isn't equals to this.
     */
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