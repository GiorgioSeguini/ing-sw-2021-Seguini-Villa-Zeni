package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.LeaderStatus;
import it.polimi.ingsw.model.exception.PopesInspectionException;
/*Last Edit: Fabio*/

public class LeaderCard extends Card {

    private LeaderStatus status;

    private Requirements requirements;

    private Ability ability;

    private int VictoryPoints;

    /*Default constructor*/
    public LeaderCard() {
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
    public void setDiscard(Player owner) {
        if(getStatus().ordinal()==0){
            setStatus(LeaderStatus.Dead);
            try {
                owner.getFaithTrack().addPoint();
            } catch (PopesInspectionException e) {
                //TODO
            }
        }
    }

}