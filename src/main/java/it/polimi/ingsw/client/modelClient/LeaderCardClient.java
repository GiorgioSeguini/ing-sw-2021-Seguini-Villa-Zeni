package it.polimi.ingsw.client.modelClient;


import it.polimi.ingsw.constant.model.LeaderCard;
import it.polimi.ingsw.constant.model.Requirements;

public class LeaderCardClient extends LeaderCard {

    private final Ability ability;

    public LeaderCardClient(Requirements requirements, int victoryPoints, Ability ability) {
        super(requirements, victoryPoints);
        this.ability = ability;
    }

    public Ability getAbility() {
        return ability;
    }

    @Override
    public String toString(){
        String LC ="";
        LC += "Status: "+getStatus()+"\n";
        LC += "Requirements: \n";
        LC += "\t"/* +getRequirements()*/;
        LC += "Victory Points: "+getVictoryPoints()+"\n";
        LC += "Ability: \n";
        LC += "\t "+getAbility().getAbilityType()+"\tResources: "+getAbility().getResource()+"\n";
        return LC;
    }
}
