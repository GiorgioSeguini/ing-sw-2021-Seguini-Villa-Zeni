package it.polimi.ingsw.model;

import java.util.*;
/*Last Edit: Fabio*/

public class LeaderCard extends Card {

    private LeaderStatus status;

    private Requirements requirements;

    private Ability ability;

    private int VictoryPoints;

    //Default constructor
    public LeaderCard() {
    }


    //getter
    public LeaderStatus getStatus() {
        return status;
    }

    public Requirements getRequirements() {
        return requirements;
    }

    public Ability getAbility() {
        return ability;
    }


    public void setStatus(LeaderStatus status) {
        this.status = status;
    }

    /**
     * @param owner 
     * @return
     */
    public void setPlayed(Player owner) {
        if(getStatus().ordinal()==0 && getRequirements().match(owner)) {
                getAbility().RunAbility(owner);
                setStatus(LeaderStatus.Played);
        }
    }

    /**
     * @param owner 
     * @return
     */
    public void setDiscard(Player owner) {
        if(getStatus().ordinal()==0){
            setStatus(LeaderStatus.Dead);
            owner.getFaithTrack().addPoint();
        }
    }

}