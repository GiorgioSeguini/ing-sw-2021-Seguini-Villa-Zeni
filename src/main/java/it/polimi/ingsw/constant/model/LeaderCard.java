package it.polimi.ingsw.constant.model;

import it.polimi.ingsw.constant.enumeration.LeaderStatus;

public abstract class LeaderCard extends Card {

    private LeaderStatus status;
    private final Requirements requirements;


    /*Default constructor*/
    public LeaderCard(Requirements requirements, int victoryPoints) {
        super(victoryPoints);
        this.status= LeaderStatus.onHand;
        this.requirements=requirements;
    }


    /*getter*/
    public LeaderStatus getStatus() {
        return status;
    }

    public Requirements getRequirements() {
        return requirements;
    }

    public void setStatus(LeaderStatus status) {
        this.status = status;
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
        return true;
    }



}
