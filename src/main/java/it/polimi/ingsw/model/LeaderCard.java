package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.LeaderStatus;
/*Last Edit: Fabio*/

public class LeaderCard extends Card {

    private LeaderStatus status;
    private final Requirements requirements;
    private final Ability ability;
    private int victoryPoints;

    /*Default constructor*/
    public LeaderCard(Requirements requirements, Ability ability, int victoryPoints) {
        this.status=LeaderStatus.onHand;
        this.requirements=requirements;
        this.ability=ability;
        this.victoryPoints = victoryPoints;
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

    /*setter*/
    public void setStatus(LeaderStatus status) {
        this.status = status;
    }

    /*Additional Methods*/
    /** This set the LeaderCard status on "played"**/
    public void setPlayed(Player owner) {
        if(getStatus().ordinal()==0 && getRequirements().match(owner)) {
                getAbility().RunAbility(owner);
                setStatus(LeaderStatus.Played);
        }
    }

    /** This set the LeaderCard status on "Dead" and add one FaithPoint**/
    public void setDiscard(Player owner){
        if(getStatus().ordinal()==0){
            setStatus(LeaderStatus.Dead);
            owner.getFaithTrack().addPoint();
        }
    }

}
