package it.polimi.ingsw.client.modelClient;

import it.polimi.ingsw.constant.enumeration.ResourceType;

/**
 * The ability of a leaderCardClient
 * NOTE: this is very different from server side modelling of this part of the game
 */
public class Ability {

    private final ResourceType resource;
    private final AbilityType abilityType;

    /**
     * Instantiates a new Ability.
     *
     * @param resource    the resource
     * @param abilityType of type Ability type
     */
    public Ability(ResourceType resource, AbilityType abilityType) {
        this.resource = resource;
        this.abilityType = abilityType;
    }

    /**
     * Gets the resources involved in the ability, as follows:
     * DepotsAbility: extra shelf of size 2 of the specified ResourceType
     * DiscountAbility: discount of 1 from the specified ResourceType cost when buying some new development card from the DashBoard
     * PowerProductionPlusAbility: add an extra production that take 1 Resource of the specified ResourceType and get 1 faith points and 1 of choice resource
     * WhiteAbility: add the conversion of white Marbles in the specified ResourceType
     * @return the resource of type ResourceType
     */
    public ResourceType getResource() {
        return resource;
    }

    /**
     * Gets the ability type.
     *
     * @return the ability type of type AbilityType
     */
    public AbilityType getAbilityType() {
        return abilityType;
    }

    @Override
    public boolean equals(Object o){
        if(o==this)
            return true;

        if(!(o instanceof Ability))
            return false;

        Ability other = (Ability) o;

        if(other.getAbilityType()!=this.getAbilityType())
            return false;

        return other.getResource()==this.getResource();
    }

}