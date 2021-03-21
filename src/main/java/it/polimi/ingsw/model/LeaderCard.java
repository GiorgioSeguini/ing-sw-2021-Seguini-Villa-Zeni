package it.polimi.ingsw.model;

import java.util.*;

/**
 * 
 */
public class LeaderCard extends Card {

    /**
     * Default constructor
     */
    public LeaderCard() {
    }

    /**
     * 
     */
    private LeaderStatus status;

    private Requirements requirements;

    private Ability ability;

    /**
     * @return
     */
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
        // TODO implement here
    }

    /**
     * @param owner 
     * @return
     */
    public void setDiscard(Player owner) {
        // TODO implement here
    }

}