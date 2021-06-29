package it.polimi.ingsw.client.modelClient;

/**
 * The enum Ability type.
 */
public enum AbilityType {
    /**
     * DepotsAbility: extra shelf of size 2 of the specified ResourceType
     */
    DepotsAbility,
    /**
     * DiscountAbility: discount of 1 from the specified ResourceType cost when buying some new development card from the DashBoard
     */
    DiscountAbility,
    /**
     * PowerProductionPlusAbility: add an extra production that take 1 Resource of the specified ResourceType and get 1 faith points and 1 of choice resource
     */
    ProductionPowerPlusAbility,
    /**
     * WhiteAbility: add the conversion of white Marbles in the specified ResourceType
     */
    WhiteAbility
}
