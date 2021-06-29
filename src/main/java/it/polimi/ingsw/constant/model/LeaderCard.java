package it.polimi.ingsw.constant.model;

import it.polimi.ingsw.constant.enumeration.LeaderStatus;

/**
 * LeaderCard abstract class.
 * Extends Card.
 * Superclass of LeaderCardExt (server) and LeaderCardClient (client).
 */
public abstract class LeaderCard extends Card {

    private LeaderStatus status;
    private final Requirements requirements;


    /**
     * Instantiates a new Leader card.
     *
     * @param requirements of type Requirements: the requirements.
     * @param victoryPoints of type int: the victory points.
     */
    /*Default constructor*/
    public LeaderCard(Requirements requirements, int victoryPoints) {
        super(victoryPoints);
        this.status= LeaderStatus.onHand;
        this.requirements=requirements;
    }


    /**
     * Gets card's status.
     *
     * @return of type LeaderStatus: the status.
     */
    /*getter*/
    public LeaderStatus getStatus() {
        return status;
    }

    /**
     * Gets card's requirements.
     *
     * @return of type Requirements: the requirements.
     */
    public Requirements getRequirements() {
        return requirements;
    }

    /**
     * Sets card's status.
     *
     * @param status of type LeaderStatus: the status.
     */
    public void setStatus(LeaderStatus status) {
        this.status = status;
    }

    /**
     *
     * @param o of type Object.
     * @return True if param o is equals to this or if param o.getId is equals to this.getId.
     *         False if param o isn't an instance of LeaderCard or o.getRequirements or o.getVictoryPoints isn't equals to this.getRequirements or this.getVictoryPoints.
     */
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
        return ((LeaderCard) o).getId()==this.getId();
    }

}
