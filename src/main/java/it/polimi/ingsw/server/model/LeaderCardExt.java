package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.enumeration.LeaderStatus;
import it.polimi.ingsw.constant.model.LeaderCard;

/**
 * LeaderCardExt class.
 * Extends LeaderCard.
 */
public class LeaderCardExt extends LeaderCard {

    private final Ability ability;

    /**
     * Default constructor.
     *
     * @param requirements of type RequirementsExt: leader card's requirements.
     * @param ability of type Ability:  leader card's ability.
     * @param victoryPoints of type int: leader card's victory points.
     */
    public LeaderCardExt(RequirementsExt requirements,  Ability ability, int victoryPoints) {
        super(requirements, victoryPoints);
        this.ability = ability;
    }

    /**
     *
     * @return the leader card's ability.
     */
    public Ability getAbility() {
        return ability;
    }

    /**
     *
     * @return of type RequirementsExt: the leader card's requirements.
     */
    @Override
    public RequirementsExt getRequirements() {
        return (RequirementsExt) super.getRequirements();
    }

    /*Additional Methods*/
    /** This set the LeaderCard status on "played"
     * @return true if the card is really activated
     */
    public boolean setPlayed(PlayerExt owner) {
        if(this.getStatus()==LeaderStatus.onHand && this.getRequirements().match(owner)){
                ability.RunAbility(owner);
                super.setStatus(LeaderStatus.Played);
                owner.getPersonalBoard().change();
                return true;
        }
        return false;
    }

    /** This set the LeaderCard status on "Dead" and add one FaithPoint
     * @return true if the card is really discarded, false otherwise
     * */
    public boolean setDiscard(PlayerExt owner){
        if(getStatus().equals(LeaderStatus.onHand)){
            setStatus(LeaderStatus.Dead);
            owner.getFaithTrack().addPoint();
            owner.getPersonalBoard().setDiscard(this);
            return true;
        }
        return false;
    }

    /**
     *
     * @param o of type Object.
     * @return True if param o.ability is equals to this.ability. False if param o isn't an instance of LeaderCardExt or o isn't equals to this.
     */
    @Override
    public boolean equals(Object o) {
        if(!super.equals(o))
            return false;

        if(!(o instanceof LeaderCardExt))
            return false;

        return this.ability.equals(((LeaderCardExt) o).ability);
    }
}
