package it.polimi.ingsw.client.modelClient;


import it.polimi.ingsw.constant.model.LeaderCard;
import it.polimi.ingsw.constant.model.Requirements;

/**
 * The type Leader card client.
 */
public class LeaderCardClient extends LeaderCard {

    private final Ability ability;

    /**
     * Instantiates a new Leader card client.
     *
     * @param requirements  the requirements needed to active the card
     * @param victoryPoints the victory points guaranteed by the card
     * @param ability       the ability of the card
     */
    public LeaderCardClient(Requirements requirements, int victoryPoints, Ability ability) {
        super(requirements, victoryPoints);
        this.ability = ability;
    }

    /**
     * Gets ability class containing all the information about cards ability
     *
     * @return the ability - of type Ability
     */
    public Ability getAbility() {
        return ability;
    }

    @Override
    public String toString(){
        String LC ="";
        LC += "Requirements: \n";
        LC += "\t" +getRequirements();
        LC += "Victory Points: "+getVictoryPoints()+"\n";
        LC += "Ability: \n";
        LC += "\t "+getAbility().getAbilityType();
        LC+="\tResources: "+getAbility().getResource()+"\n";
        return LC;
    }
}
