package it.polimi.ingsw.client.modelClient;

import it.polimi.ingsw.constant.enumeration.LeaderStatus;

public class LeaderCard extends Card {

    private LeaderStatus status;
    private final Requirements requirements;
    private final Ability ability;

    /*Default constructor*/
    public LeaderCard(Requirements requirements, Ability ability, int victoryPoints) {
        super(victoryPoints);
        this.status= LeaderStatus.onHand;
        this.requirements=requirements;
        this.ability=ability;
    }


    /*getter*/
    public LeaderStatus getStatus() {
        return status;
    }

    public Requirements getRequirements() {
        return requirements;
    }

    public Ability getAbility() {
        return ability;
    }


    @Override
    public boolean equals(Object o){
        if(o==this)
            return true;

        if(!(o instanceof LeaderCard))
            return false;

        LeaderCard other = (LeaderCard) o;

        if(this.getVictoryPoints() != other.getVictoryPoints())
            return false;

        if(!requirements.equals(other.requirements))
            return false;

        return ability.equals(other.ability);
    }


}
